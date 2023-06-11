package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.RoleDto;
import org.iesfm.app.entity.RoleEntity;

/**
 * En esta clase se encuentran todos los metodos que convierten las entidades en dtos para los roles
 */
public class RoleMapper {

    /**
     * En este metodo se transforma un dto de role en una entidad de role
     * @param role es el dto a transformar
     * @return devuelve la entidad de role
     */
    public static RoleEntity toEnity(RoleDto role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(role.getName());
        roleEntity.setId(role.getId());
        return roleEntity;
    }

    /**
     * En este metodo se transforma una entidad de role a un dto de role
     * @param role es la entidad a transformar
     * @return devuelve el dto de role
     */
    public static RoleDto toDto(RoleEntity role) {
        return new RoleDto(
                role.getId(),
                role.getName());
    }
}
