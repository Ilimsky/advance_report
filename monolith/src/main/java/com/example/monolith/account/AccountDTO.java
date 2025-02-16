package com.example.monolith.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private String name;
    private Long id;

    public AccountDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
