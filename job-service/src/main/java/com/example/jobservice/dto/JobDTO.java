package com.example.jobservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Для исключения null значений из ответа
@Data
public class JobDTO {

    private Long id;

    private String jobTitle;
}
