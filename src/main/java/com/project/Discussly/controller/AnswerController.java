package com.project.Discussly.controller;

import com.project.Discussly.dto.ApiResponse;
import com.project.Discussly.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    @PostMapping("/add/{id}")
    public ResponseEntity<ApiResponse> addAnswer(@PathVariable int id, @org.jetbrains.annotations.NotNull @RequestParam String answer){
        if(answer.trim().isEmpty()){
            throw new RuntimeException("Answer cannot be empty.");
        }
        answerService.addAnswer(id,answer);

        return new ResponseEntity<>(new ApiResponse("Answer Posted Successfully.",true), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAnswer(@PathVariable int id,@RequestParam String answer){
        if(answer.trim().isEmpty()){
            throw new RuntimeException("Answer cannot be empty.");
        }
        answerService.updateAnswer(id,answer);
        return new ResponseEntity<>(new ApiResponse("Answer Updated Successfully.",true), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAnswer(@PathVariable int id){
        answerService.deleteAnswer(id);
        return new ResponseEntity<>(new ApiResponse("Answer Deleted Successfully.",true),HttpStatus.OK);
    }
    @GetMapping("/count/me")
    public ResponseEntity<ApiResponse> getAnswerCount(){
        long count = answerService.getAnswerCount();
        return new ResponseEntity<>(new ApiResponse(String.valueOf(count),true),HttpStatus.OK);
    }
}
