package org.iesfm.app.dao;

import org.iesfm.app.entity.AbsenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceDao extends JpaRepository<AbsenceEntity, Integer> {
}
