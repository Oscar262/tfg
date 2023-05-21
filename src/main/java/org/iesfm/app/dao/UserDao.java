package org.iesfm.app.dao;

import org.iesfm.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmailAndPass(String email, String pass);

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

    List<UserEntity> findByRole_NameAndSubjectList_IdAndClassEntities_Id(String name, Integer id, Integer id1);


    

    List<UserEntity> findByRole_Name(String name);


}
