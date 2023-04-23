package org.iesfm.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "admin")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @OneToMany(mappedBy = "admin")
    private List<TeacherEntity> teachers;
}
