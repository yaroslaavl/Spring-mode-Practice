package com.dmdev.spring.dto;

import java.time.LocalDate;

public record PersonalInfo(String firstName,
                           String lastName,
                           LocalDate birthDate) {
}
