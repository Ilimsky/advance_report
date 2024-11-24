package com.example.reportservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Для исключения null значений из ответа
public class ReportDTO {

    private Long id;  // Используем Long для идентификатора, как в сущности

    private String sequenceNumber;  // Название департамента
    private int branchCounter;
}
