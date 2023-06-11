package org.iesfm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * En esta clase describen los campos que tendra el dto(Data Transfer Object) de la entidad role
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

}
