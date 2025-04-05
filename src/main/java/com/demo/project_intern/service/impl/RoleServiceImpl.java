package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.RoleDto;
import com.demo.project_intern.dto.request.role.RoleCreateRequest;
import com.demo.project_intern.dto.request.role.RoleSearchRequest;
import com.demo.project_intern.dto.request.role.RoleUpdateRequest;
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
    public RoleDto getRole(Long RoleId) {
        RoleEntity roleEntity = roleRepository.findById(RoleId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public RoleDto updateRole(Long RoleId, RoleUpdateRequest request) {
        RoleEntity role = roleRepository.findById(RoleId)
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
    public void deleteRole(Long RoleId) {
        RoleEntity role = roleRepository.findById(RoleId)
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
}
