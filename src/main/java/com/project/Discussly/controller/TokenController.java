package com.project.Discussly.controller;

import com.project.Discussly.dto.ApiResponse;
import com.project.Discussly.service.TokenService;
import com.project.Discussly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final UserService userService;


    @GetMapping("/verify")
    public ResponseEntity<ApiResponse> verifyUser(@RequestParam("token") String token){
        if(token.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("Invalid Token",false), HttpStatus.BAD_REQUEST);

        }
        tokenService.confirmUser(token);
        return new ResponseEntity<>(new ApiResponse("Email Verified",true),HttpStatus.OK);

    }
    @PostMapping("/resend")
    public ResponseEntity<ApiResponse> resendToken(@RequestParam String email){
        userService.resendToken(email);
        return new ResponseEntity<>(new ApiResponse("Verification Token Sent",true),HttpStatus.OK);
    }
}
