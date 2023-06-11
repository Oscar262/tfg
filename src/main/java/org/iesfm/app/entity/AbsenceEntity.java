package org.iesfm.app.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * En esta clase describen los campos que tendra la entidad ausencia, asi como los campos en la tabla "absence" y sus relaciones
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "absence")
public class AbsenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    @Column(name = "num_hours")
    private Integer numHours;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_cre")
    @ToString.Exclude
    private UserEntity teacherCre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mod")
    @ToString.Exclude
    private UserEntity userMod;

    @NotNull
    private LocalDate date;

    @Column(name = "date_mod")
    private LocalDate dateMod;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity student;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject")
    @ToString.Exclude
    private SubjectEntity subject;

}
