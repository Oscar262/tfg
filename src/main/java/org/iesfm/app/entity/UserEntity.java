package org.iesfm.app.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * En esta clase describen los campos que tendra la entidad usuario, asi como los campos en la tabla "user_entity" y sus relaciones
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_entity")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "first_surname")
    private String firstSurname;

    @Column(name = "second_surname")
    private String secondSurname;

    @NotNull
    @Column(name = "user_cre")
    private Integer usuCre;

    @Column(name = "user_mod")
    private Integer usuMod;

    @NotNull
    @Column(name = "date_cre")
    private LocalDate dateCre;

    @Column(name = "date_mod")
    private LocalDate dateMod;


    @Email
    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String pass;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;


    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    private List<AbsenceEntity> absenceList;


    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "user_subject",
            joinColumns = @JoinColumn(name = "user_in_subject_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private Set<SubjectEntity> subjectList;


    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "user_class",
            joinColumns = @JoinColumn(name = "user_in_class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"))
    private Set<ClassEntity> classEntities;


    @OneToMany(mappedBy = "teacherCre")
    private List<AbsenceEntity> absenceCre;

    @OneToMany(mappedBy = "userMod")
    private List<AbsenceEntity> absenceMod;

    @OneToMany(mappedBy = "userCre")
    private List<ClassEntity> classCre;

    @OneToMany(mappedBy = "userMod")
    private List<ClassEntity> classMod;

    @OneToMany(mappedBy = "userCre")
    private List<SubjectEntity> subjectCre;

    @OneToMany(mappedBy = "userMod")
    private List<SubjectEntity> subjectMod;


}
