package org.iesfm.app.dao;

import org.iesfm.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * En esta clase se controla el acceso en base de datos para los usuarios
 */
@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {

    /**
     * En este metodo se busca un usuario en la base de datos
     * @param email es el correo que tiene que tener el usuario en la base de datos
     * @param pass es la contraseña que tiene que tener el usuario en la base de datos
     * @return devuelve al usuario si los dos campos anteriores coinciden
     */
    UserEntity findByEmailAndPass(String email, String pass);

    /**
     * En este metodo se busca un usuario en la base de datos
     * @param email es el email que tiene que tener el usuario en la base de datos
     * @return devuelve al usuario que tenga el email que coincida con el buscado
     */
    @Query("select u from UserEntity u where u.email = ?1")
    UserEntity findByEmail(String email);


    UserEntity findByIdAndRole_Name(Integer id, String name);


    List<UserEntity> findByRole_NameAndSubjectList_Name(String name, String name1);

    @Query("select u from UserEntity u inner join u.classEntities classEntities " +
            "where u.role.name = ?1 and classEntities.name = ?2")
    List<UserEntity> findByRole_NameAndClassEntities_Name(String name, String name1);

    @Query("select u from UserEntity u inner join u.subjectList subjectList inner join u.classEntities classEntities " +
            "where u.role.name = ?1 and subjectList.name = ?2 and classEntities.name = ?3")
    List<UserEntity> findByRole_NameAndSubjectList_NameAndClassEntities_Name(String name, String name1, String name2);

    /**
     * En este metodo se busca una lista de usuarios en la base de datos
     * @param idRole es el id del role que se buscara en la base de datos
     * @param id es el id que tiene que tener una de las asignaturas en las que estan matriculados los estudiantes o que
     *           imparten los profesores
     * @param id1 es el id de la clase en la que tienen que estar los estudiantes, o una de en las que dan clase los profesores
     * @return devolvera una lista de usuarios que contenga el role buscado, que en su lista de clases se encuentre la que se
     * esta buscando, y que en la lista de asignaturas tambien se encuentre la que se esta buscando
     */
    @Query("select u from UserEntity u inner join u.subjectList subjectList inner join u.classEntities classEntities " +
            "where u.role.id = ?1 and subjectList.id = ?2 and classEntities.id = ?3")
    List<UserEntity> findByRole_IdAndSubjectList_IdAndClassEntities_Id(Integer idRole, Integer id, Integer id1);


    List<UserEntity> findByRole_Name(String name);


}
