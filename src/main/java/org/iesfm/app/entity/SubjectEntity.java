package org.iesfm.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
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

    @NotNull
    private String name;

    @NotNull
    @Column(name = "total_hours")
    private Integer totalHours;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_cre")
    private UserEntity userCre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mod")
    private UserEntity userMod;

    @NotNull
    @Column(name = "date_cre")
    private LocalDate dateCre;

    @Column(name = "date_mod")
    private LocalDate dateMod;


    @OneToMany(mappedBy = "subject")
    private Set<AbsenceEntity> absence;

    @OneToMany(mappedBy = "subject")
    private Set<UserClassSubject> relation;

}
