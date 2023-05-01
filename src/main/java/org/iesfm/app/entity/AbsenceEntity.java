package org.iesfm.app.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

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

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_cre")
    @ToString.Exclude
    private UserEntity teacherCre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mod")
    @ToString.Exclude
    private UserEntity userMod;

    @NotNull
    private LocalDate date;

    @Column(name = "date_mod")
    private LocalDate dateMod;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject")
    @ToString.Exclude
    private SubjectEntity subject;

}
