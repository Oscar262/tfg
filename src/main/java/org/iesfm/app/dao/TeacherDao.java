package org.iesfm.app.dao;

import org.iesfm.app.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface TeacherDao extends JpaRepository<TeacherEntity, Integer> {
    TeacherEntity findByEmailAndPass(String email, String pass);


}
