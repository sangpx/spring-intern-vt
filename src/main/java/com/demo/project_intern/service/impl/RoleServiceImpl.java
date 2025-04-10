package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.RoleDto;
import com.demo.project_intern.dto.request.book.AssignRemoveCategoryRequest;
import com.demo.project_intern.dto.request.role.AssignRemovePermissionsRequest;
import com.demo.project_intern.dto.request.role.RoleCreateRequest;
import com.demo.project_intern.dto.request.role.RoleSearchRequest;
import com.demo.project_intern.dto.request.role.RoleUpdateRequest;
import com.demo.project_intern.dto.response.*;
import com.demo.project_intern.entity.BookEntity;
import com.demo.project_intern.entity.CategoryEntity;
import com.demo.project_intern.entity.PermissionEntity;
import com.demo.project_intern.entity.RoleEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.PermissionRepository;
import com.demo.project_intern.repository.RoleRepository;
import com.demo.project_intern.service.RoleService;
import com.demo.project_intern.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;



@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final ModelMapper mapper;


    @Override
    public RoleDto createRole(RoleCreateRequest request) {
        RoleEntity roleEntity = mapper.map(request, RoleEntity.class);
        if(roleRepository.existsByCode(request.getCode())) {
            throw new BaseLibraryException(ErrorCode.ROLE_EXISTED);
        }
        roleEntity.setPermissions(getPermissionEntity(request.getPermissions()));
        roleRepository.save(roleEntity);
        return mapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public List<RoleDto> getRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleEntity -> mapper.map(RoleEntity, RoleDto.class))
                .toList();
    }

    @Override
    public RoleDto getRole(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public RoleDto updateRole(Long roleId, RoleUpdateRequest request) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        if(request.getName() != null) {
            role.setName(request.getName());
        }
        role.setPermissions(getPermissionEntity(request.getPermissions()));
        role.setUpdatedAt(LocalDate.now());
        roleRepository.save(role);
        return mapper.map(role, RoleDto.class);
    }

    @Override
    public void deleteRole(Long roleId) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        roleRepository.delete(role);
    }

    private Set<PermissionEntity> getPermissionEntity (Set<PermissionDto> permissionDtos) {
        // check request.getPermissions() null
        Set<String> permissionCodes = Optional.ofNullable(permissionDtos)
                .orElse(Collections.emptySet())
                .stream()
                .map(PermissionDto::getCode)
                .collect(Collectors.toSet());
        return new HashSet<>(permissionRepository.findByCodeIn(permissionCodes));
    }

    @Override
    public Page<RoleDto> search(RoleSearchRequest request) {
        Pageable pageable = PageableUtils.from(request);
        return roleRepository.search(request, pageable);
    }

    @Override
    public AssignPermissionResponse assignPermission(AssignRemovePermissionsRequest request) {
        return processAssignAndRemove(
                request,
                true,
                () -> AssignPermissionResponse.builder().build(),
                (response, result) -> {
                    response.setAdded(result.getProcessed());
                    response.setDuplicated(result.getSkipped());
                }
        );
    }

    @Override
    public RemovePermissionResponse removePermission(AssignRemovePermissionsRequest request) {
        return processAssignAndRemove(
                request,
                false,
                () -> RemovePermissionResponse.builder().build(),
                (response, result) -> {
                    response.setRemoved(result.getProcessed());
                    response.setNotAssigned(result.getSkipped());
                }
        );
    }

    private RoleEntity getRoleEntity(Long roleId) {
        if(roleId == null) {
            throw new BaseLibraryException(ErrorCode.INVALID_KEY);
        }
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    private List<PermissionEntity> validatePermissions(List<Long> permissionIds) {
        List<PermissionEntity> permissionEntities = permissionRepository.findAllById(permissionIds);
        if (permissionEntities.size() != permissionIds.size()) {
            throw new BaseLibraryException(ErrorCode.REQUEST_PERMISSIONS_INVALID);
        }
        return permissionEntities;
    }

    private ProcessResult processRolePermissions(RoleEntity role, List<PermissionEntity> permissions, boolean isAssign) {
        Set<Long> existingPermissionIds = role.getPermissions()
                .stream()
                .map(PermissionEntity::getId)
                .collect(Collectors.toSet());

        List<String> processedPermissions = new ArrayList<>();
        List<String> skippedPermissions = new ArrayList<>();

        for (PermissionEntity permission : permissions) {
            boolean alreadyHasPermission = existingPermissionIds.contains(permission.getId());
            if (isAssign) {
                if (alreadyHasPermission) {
                    skippedPermissions.add(permission.getCode());
                } else {
                    role.getPermissions().add(permission);
                    processedPermissions.add(permission.getCode());
                }
            } else { // remove
                if (alreadyHasPermission) {
                    role.getPermissions().remove(permission);
                    processedPermissions.add(permission.getCode());
                } else {
                    skippedPermissions.add(permission.getCode());
                }
            }
        }
        return ProcessResult
                .builder()
                .processed(processedPermissions)
                .skipped(skippedPermissions)
                .build();
    }

    private <T> T processAssignAndRemove(
            AssignRemovePermissionsRequest request,
            boolean isAssign,
            Supplier<T> responseSupplier,
            BiConsumer<T, ProcessResult> resultConsumer
    ) {
        RoleEntity role = getRoleEntity(request.getRoleId());
        List<PermissionEntity> permissionsToProcess = validatePermissions(request.getPermissionIds());
        ProcessResult result = processRolePermissions(role, permissionsToProcess, isAssign);
        roleRepository.save(role);
        T response = responseSupplier.get();
        if (response instanceof AssignPermissionResponse assignResponse) {
            assignResponse.setRoleId(role.getId());
        } else if (response instanceof RemovePermissionResponse removeResponse) {
            removeResponse.setRoleId(role.getId());
        }
        resultConsumer.accept(response, result);
        return response;
    }
}
