package org.iesfm.app.dao;

import org.iesfm.app.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassDao extends JpaRepository<ClassEntity, Integer> {
}
