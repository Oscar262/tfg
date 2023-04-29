package org.iesfm.app.dao;

import org.iesfm.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmailAndPass(String email, String pass);

}
