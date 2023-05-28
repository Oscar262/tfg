package org.iesfm.app.service;

import org.iesfm.app.configuration.Config;
import org.iesfm.app.dao.UserDao;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.mapper.SubjectMapper;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.ClassListException;
import org.iesfm.app.exceptions.EmptytListException;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<UserEntity> findAllStudents(Integer classId, Integer subjectId, Integer role) throws EmptytListException {

        if (userDao.findByRole_IdAndSubjectList_IdAndClassEntities_Id(role, subjectId, classId).isEmpty()) {
            throw new EmptytListException();
        }else return userDao.findByRole_IdAndSubjectList_IdAndClassEntities_Id(role, subjectId, classId);


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

    public boolean userIsAdmin(UserEntity user) {
        return user.getRole().getName().equalsIgnoreCase("Admin");

    }

    private boolean entityExist(UserEntity entity) {
        if (userDao.findByEmail(entity.getEmail()) == null) {
            return false;
        }
        return true;
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
        // if (!entity.getClassEntities().isEmpty()) {
        if (idRole == 1) {
            if (entity.getClassEntities().size() > 1) {
                throw new ClassListException();
            }
        } else if (idRole == 3) {
            if (!entity.getClassEntities().isEmpty()) {
                throw new ClassListException();
            }
        }
        // }
        UserEntity admin = userDao.findById(idUser).orElseThrow();
        if (userIsAdmin(admin)) {
            entity.setUsuCre(admin.getId());

            String emailUser = "";
            if (entity.getSecondSurname() != null) {
                emailUser = entity.getName() + entity.getFirstSurname() + entity.getSecondSurname();

            } else {
                emailUser = entity.getName() + entity.getFirstSurname();
            }

            entity.setEmail(checkEmail(emailUser, email));
            return userDao.save(entity);
        } else throw new IncorrectUserException();
    }

    private String checkEmail(String emailUser, String email) {

        if (userDao.findByEmail(emailUser + email) == null) {
            return (emailUser + email);

        } else {
            emailUser = emailUser + "1";

            if (userDao.findByEmail(emailUser + email) != null) {
                return checkEmail(emailUser, email);
            } else {
                return emailUser + email;
            }
        }
    }

    public UserEntity updateUser(UserEntity entity) {
      return userDao.save(entity);
    }
}

