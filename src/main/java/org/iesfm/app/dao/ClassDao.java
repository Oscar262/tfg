package org.iesfm.app.dao;

import org.iesfm.app.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassDao extends JpaRepository<ClassEntity, Integer> {
    ClassEntity findByName(String name);

    List<ClassEntity> findByUserEntities_Id(Integer id);


}
