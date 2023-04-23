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
@Table(name = "teacher")
public class TeacherEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "first_surname")
    private String firstSurname;

    @Column(name = "second_surname")
    private String secondSurname;

    @Column(name = "user_cre")
    private String userCre;

    @Column(name = "user_mod")
    private String userMod;

    @Column(name = "date_cre")
    private LocalDate dateCre;

    @Column(name = "date_mod")
    private LocalDate dateMod;

    private String email;

    private String pass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @OneToMany(mappedBy = "teacher")
    private Set<TeacherClassSubject> relation;

}
