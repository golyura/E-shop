package com.gol.shop.dto;

import java.time.LocalDate;

public record CustomerFilter(String firstname,
                             LocalDate birthDate) {
}
