package org.iesfm.app.dao;

import org.iesfm.app.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * En esta clase se controla el acceso en base de datos para las clases
 */
@Repository
public interface ClassDao extends JpaRepository<ClassEntity, Integer> {

    /**
     * En este metodo se accede a la base de datos y se busca una clase
     * @param name es el nombre de la clase que se busca en la base de datos
     * @return devolvera la clase en caso de encontrarla
     */
    ClassEntity findByName(String name);

    /**
     * En este metodo se busca un lista de clases que esten relacionadas con un usuario
     * @param id es el id del usuario para el que se buscaran las clases
     * @return devuelve la lista de las clases
     */
    List<ClassEntity> findByUserEntities_Id(Integer id);


}
