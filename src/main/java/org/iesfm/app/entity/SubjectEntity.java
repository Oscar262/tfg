package org.iesfm.app.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    @ToString.Exclude
    private UserEntity userCre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mod")
    @ToString.Exclude
    private UserEntity userMod;

    @NotNull
    @Column(name = "date_cre")
    private LocalDate dateCre;

    @Column(name = "date_mod")
    private LocalDate dateMod;


    @OneToMany(mappedBy = "subject")
    @ToString.Exclude
    private Set<AbsenceEntity> absence;

    @ManyToMany(mappedBy = "subjectList")
    @ToString.Exclude
    private List<UserEntity> userEntities;

}
