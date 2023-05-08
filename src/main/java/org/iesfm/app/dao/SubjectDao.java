package org.iesfm.app.dao;

import org.iesfm.app.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends JpaRepository<SubjectEntity, Integer> {
    SubjectEntity findByName(String name);

}
