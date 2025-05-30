package com.project.Discussly.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDto {
    @Length(min = 8,message = "Password should contains at least 8 characters.")
    String password;
    String token;
}
