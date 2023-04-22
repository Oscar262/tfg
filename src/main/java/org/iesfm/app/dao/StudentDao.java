package org.iesfm.app.dao;

import org.iesfm.app.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<StudentEntity, Integer> {

    StudentEntity findByEmailAndPass(String email, String pass);

}
