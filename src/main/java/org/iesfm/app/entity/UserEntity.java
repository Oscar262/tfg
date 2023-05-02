package org.iesfm.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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


    @ManyToMany(mappedBy = "userEntities")
    @ToString.Exclude
    private List<ClassEntity> classEntities;


    @OneToOne(mappedBy = "teacherCre")
    private AbsenceEntity absenceCre;

    @OneToOne(mappedBy = "userMod")
    private AbsenceEntity absenceMod;

    @OneToOne(mappedBy = "userCre")
    private ClassEntity classCre;

    @OneToOne(mappedBy = "userMod")
    private ClassEntity classMod;

    @OneToOne(mappedBy = "userCre")
    private SubjectEntity subjectCre;

    @OneToOne(mappedBy = "userMod")
    private SubjectEntity subjectMod;




}
