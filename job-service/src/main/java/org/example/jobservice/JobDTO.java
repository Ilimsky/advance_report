package org.example.jobservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobDTO {

    private Long id;
    private String name;

}