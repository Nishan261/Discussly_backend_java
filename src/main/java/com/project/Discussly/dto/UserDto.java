package com.project.Discussly.dto;

import com.project.Discussly.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private UserRole role;
    private int reputation;

}
