package org.iesfm.app.dao;

import org.iesfm.app.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends JpaRepository<TeacherEntity, Integer> {
    TeacherEntity findByEmailAndPass(String email, String pass);


}
