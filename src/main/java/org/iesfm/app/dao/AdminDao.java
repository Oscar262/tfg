package org.iesfm.app.dao;

import org.iesfm.app.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminDao extends JpaRepository<AdminEntity, Integer> {
    AdminEntity findByEmailAndPass(String email, String pass);

}
