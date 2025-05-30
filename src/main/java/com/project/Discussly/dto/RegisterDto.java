package com.project.Discussly.dto;

import com.project.Discussly.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank(message = "First name cannot be blank.")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;
    private String middleName;
    @Email(message = "Provide valid email.")
    private String email;
    @Length(min = 8,message = "Password should contains at least 8 characters.")
    private String password;
    private String confirmPassword;
    private UserRole userRole;

}
