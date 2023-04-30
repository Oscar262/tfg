package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserClassSubjectDto {


    @Positive
    @JsonProperty(namespace = "user_id")
    private UserDto user;

    @Positive
    @JsonProperty(namespace = "subject_id")
    private SubjectDto subject;


    @Positive
    @JsonProperty(namespace = "class_id")
    private ClassDto classEntity;

}
