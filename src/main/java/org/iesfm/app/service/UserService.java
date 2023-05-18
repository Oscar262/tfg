package org.iesfm.app.service;

import org.iesfm.app.configuration.Config;
import org.iesfm.app.dao.UserDao;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.mapper.SubjectMapper;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.ClassListException;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserEntity checkRole(String email, String pass) throws UserNotFoundException {

        UserEntity entity = userDao.findByEmailAndPass(email, pass);
        if (entity == null) {
            throw new UserNotFoundException();
        }
        return entity;

    }

    public List<UserEntity> findAllStudents(String className, String subjectName, String role) {
        List<UserEntity> userEntities;

        if (subjectName != null && className != null) {
            userEntities = userDao.findByRole_NameAndSubjectList_NameAndClassEntities_Name(role, subjectName, className);
        } else if (subjectName != null) {
            userEntities = userDao.findByRole_NameAndSubjectList_Name(role, subjectName);
        } else if (className != null) {
            userEntities = userDao.findByRole_NameAndClassEntities_Name(role, className);
        } else {
            userEntities = userDao.findByRole_Name(role);
        }

        return userEntities;
    }


    public UserEntity getUser(int idUser) {
        return userDao.findById(idUser).orElseThrow();
    }

    public void getAbsencePercentage(UserEntity entity, List<SubjectDto> subjectDtos) {
        for (SubjectEntity subject : entity.getSubjectList()) {
            Double countAbsences = 0.00;
            Double percentage = 0.00;


            for (AbsenceEntity absence : entity.getAbsenceList()) {
                if (absence.getSubject().getId().equals(subject.getId()) && absence.getStudent().equals(entity)) {
                    countAbsences += absence.getNumHours();
                }


            }
            percentage = (countAbsences * 100) / subject.getTotalHours();
            SubjectDto subjectDto = SubjectMapper.toDtoInfoWithPercentage(subject, percentage);
            subjectDtos.add(subjectDto);
        }

    }

    private boolean entityExist(UserEntity entity) {
        if (userDao.findByEmail(entity.getEmail()) != null) {
            throw new EntityExistsException();
        }
        return false;
    }

    public boolean userIsAdmin(UserEntity user) {
        return user.getRole().getName().equalsIgnoreCase("Admin");

    }

    private int checkRole(UserEntity user) {
        int idRole = 0;
        switch (user.getRole().getId()) {
            case 1:
                idRole = 1;
                break;
            case 2:
                idRole = 2;
                break;
            case 3:
                idRole = 3;
                break;
        }
        return idRole;

    }

    public UserEntity addUser(UserEntity entity, Integer idUser) throws IncorrectUserException, ClassListException {
        entity.setPass(Config.createPass());
        String email = "@email.app";

        int idRole = checkRole(entity);

        if (idRole == 1){
            if (entity.getClassEntities().size() > 1){
                throw new ClassListException();
            }
        }else if (idRole == 3){
            if (!entity.getClassEntities().isEmpty()){
                throw new ClassListException();
            }
        }

        UserEntity user = userDao.findById(idUser).orElseThrow();
        if (userIsAdmin(user)) {
            entity.setUsuCre(user.getId());
            if (entity.getSecondSurname() != null) {
                entity.setEmail(entity.getName() + entity.getFirstSurname() + entity.getSecondSurname() + email);

            } else {
                entity.setEmail(entity.getName() + entity.getFirstSurname() + email);
            }
            if (!entityExist(entity)) {
                return userDao.save(entity);
            } else {
                entity.setEmail(entity.getEmail() + "1");
                return userDao.save(entity);
            }


        }
        throw new IncorrectUserException();
    }


}

