package org.iesfm.app.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "class_entity")
public class ClassEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_cre")
    @ToString.Exclude
    private UserEntity userCre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mod")
    @ToString.Exclude
    private UserEntity userMod;

    @NotNull
    @Column(name = "date_cre")
    private LocalDate dateCre;

    @Column(name = "date_mod")
    private LocalDate dateMod;

//    @ToString.Exclude
//    @ManyToMany
//    @JoinTable(name = "user_class",
//            joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_in_class_id", referencedColumnName = "id"))

    @ManyToMany(mappedBy = "classEntities")
    @ToString.Exclude
    private List<UserEntity> userEntities;

}
