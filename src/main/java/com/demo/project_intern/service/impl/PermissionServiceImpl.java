package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.request.permission.PermissionCreateRequest;
import com.demo.project_intern.dto.request.permission.PermissionUpdateRequest;
import com.demo.project_intern.entity.PermissionEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.PermissionRepository;
import com.demo.project_intern.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final ModelMapper mapper;

    @Override
    public PermissionDto createPermission(PermissionCreateRequest request) {
        PermissionEntity PermissionEntity = mapper.map(request, PermissionEntity.class);
        if(permissionRepository.existsByCode(request.getCode())) {
            throw new BaseLibraryException(ErrorCode.PERMISSION_EXISTED);
        }
        permissionRepository.save(PermissionEntity);
        return mapper.map(PermissionEntity, PermissionDto.class);
    }

    @Override
    public List<PermissionDto> getPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(PermissionEntity -> mapper.map(PermissionEntity, PermissionDto.class))
                .toList();
    }

    @Override
    public PermissionDto getPermission(Long PermissionId) {
        PermissionEntity PermissionEntity = permissionRepository.findById(PermissionId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.map(PermissionEntity, PermissionDto.class);
    }

    @Override
    public PermissionDto updatePermission(Long PermissionId, PermissionUpdateRequest request) {
        PermissionEntity Permission = permissionRepository.findById(PermissionId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        Permission.setName(request.getName());
        Permission.setUpdatedAt(LocalDate.now());
        permissionRepository.save(Permission);
        return mapper.map(Permission, PermissionDto.class);
    }

    @Override
    public void deletePermission(Long PermissionId) {
        PermissionEntity Permission = permissionRepository.findById(PermissionId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        permissionRepository.delete(Permission);
    }

    @Override
    public Page<PermissionDto> searchPermissions(String keyword, String code, int page, int size, String sortBy, String direction) {
        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PermissionEntity> pagePermissions = permissionRepository.searchPermissions(keyword, code, pageable);
        return pagePermissions.map(pagePermission -> mapper.map(pagePermission, PermissionDto.class));
    }
}
