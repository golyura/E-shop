package com.gol.shop.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface PersonalInfo2 {
    String getFirstname();
    String getEmail();
    LocalDate getBirthDate();
    @Value("#{target.firstname + ' ' + target.email}")
    String getFullName();
}
