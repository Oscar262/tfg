package org.iesfm.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "absence")
public class AbsenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    @Column(name = "num_hours")
    private Integer numHours;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_cre")
    private UserEntity teacherCre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mod")
    private UserEntity userMod;

    @NotNull
    private LocalDate date;

    @Column(name = "date_mod")
    private LocalDate dateMod;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject")
    private SubjectEntity subject;
}
