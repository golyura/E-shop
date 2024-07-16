package com.gol.shop.dto;

import java.time.LocalDate;

public record PersonalInfo(String firstname,
                           String email,
                           LocalDate birthDate) {
}
