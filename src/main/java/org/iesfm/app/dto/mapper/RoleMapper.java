package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.RoleDto;
import org.iesfm.app.entity.RoleEntity;

public class RoleMapper {
    public static RoleEntity toEnity(RoleDto role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(role.getName());
        roleEntity.setId(role.getId());
        return roleEntity;
    }

    public static RoleDto toDto(RoleEntity role) {
        return new RoleDto(
                role.getId(),
                role.getName());
    }
}
