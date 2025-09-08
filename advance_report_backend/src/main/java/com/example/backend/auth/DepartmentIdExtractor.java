package com.example.backend.auth;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DepartmentIdExtractor {

    public Long extractDepartmentIdFromUsername(String username) {
        // Логика извлечения числа из username: user16, lombard-20/7 и т.д.
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(username);

        if (matcher.find()) {
            return Long.parseLong(matcher.group());
        }

        throw new IllegalArgumentException("Не удалось определить номер филиала из username: " + username);
    }
}