package org.iesfm.app.dao;

import org.iesfm.app.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * En esta clase se controla el acceso en base de datos para las asignaturas
 */
@Repository
public interface SubjectDao extends JpaRepository<SubjectEntity, Integer> {

    /**
     * En este metodo se busca una asignatura en la base de datos
     * @param name es el nombre de la asignatura a buscar
     * @return devuelve una asignatura si esta se encuentra en la base de datos
     */
    SubjectEntity findByName(String name);

}
