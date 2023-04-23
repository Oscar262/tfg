package org.iesfm.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "subject")
public class SubjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "total_hours")
    private Integer totalHours;

    @Column(name = "user_cre")
    private String userCre;

    @Column(name = "user_mod")
    private String userMod;

    @Column(name = "date_cre")
    private LocalDate dateCre;

    @Column(name = "date_mod")
    private LocalDate dateMod;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_subject",
            joinColumns = {@JoinColumn(name = "subject")},
            inverseJoinColumns = {@JoinColumn(name = "teacher")})
    private Set<TeacherEntity> teacher;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_subject",
            joinColumns = {@JoinColumn(name = "subject")},
            inverseJoinColumns = {@JoinColumn(name = "student")})
    private Set<StudentEntity> student;

    @OneToMany(mappedBy = "subject")
    private List<AbsenceEntity> absence;
}
