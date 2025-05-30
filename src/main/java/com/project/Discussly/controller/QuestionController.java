package com.project.Discussly.controller;

import com.project.Discussly.dto.AddQuestionDto;
import com.project.Discussly.dto.ApiResponse;
import com.project.Discussly.dto.QuestionDto;
import com.project.Discussly.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> postQuestion(@Valid @RequestBody AddQuestionDto dto,Errors errors) {
        if (errors.getAllErrors().size() > 0) {
            return new ResponseEntity<>(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage(), false), HttpStatus.BAD_REQUEST);
        }
        log.info(dto.getTags().toString());
        questionService.addQuestion(dto);

        return new ResponseEntity<>(new ApiResponse("Question Posted Successfully.", true), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateQuestion(@PathVariable int id,@Valid @RequestBody AddQuestionDto dto, Errors errors) {
        if (errors.getAllErrors().size() > 0) {
            return new ResponseEntity<>(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage(), false), HttpStatus.BAD_REQUEST);
        }
        questionService.updateQuestion(id,dto);
        return new ResponseEntity<>(new ApiResponse("Updated Successfully",true),HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable("id") int id) {

        QuestionDto dto = questionService.getQuestion(id);
        log.info(dto.toString());
       return new ResponseEntity<>(dto,HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(@RequestParam(defaultValue = "0") int pageNo) {
        List<QuestionDto> questions = questionService.getAllQuestions(pageNo);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<Set<QuestionDto>> searchQuestion(@RequestParam(value = "query") String question, @RequestParam(defaultValue = "0") int page){
        log.info(String.valueOf(page));
        Set<QuestionDto> questions;
        questions = questionService.searchQuestion(question,page);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable int id){
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(new ApiResponse("Question Deleted Successfully.",true),HttpStatus.OK);
    }
//    @GetMapping("/searchByTag")
//    public ResponseEntity<?> searchByTags(@RequestParam("tags")String [] tags,@RequestParam(value = "page",defaultValue = "0") int page){
//        log.info(Arrays.toString(tags));
//       return new ResponseEntity<>(questionService.searchByTags(tags,page),HttpStatus.OK);
//
//    }
    @PostMapping("/duplicate/{id}")
    public ResponseEntity<ApiResponse> markQuestionDuplicate(@PathVariable("id") int id,@RequestParam("link") String link){
        if(link.trim().isEmpty()){
            return new ResponseEntity<>(new ApiResponse("No link provided",false),HttpStatus.BAD_REQUEST);
        }
        questionService.markDuplicate(id,link);
        return new ResponseEntity<>(new ApiResponse("Question Marked as Duplicate",true),HttpStatus.OK);
    }

    @GetMapping("/user/me")
    public ResponseEntity<List<QuestionDto>> getUserQuestions(){
        List<QuestionDto> questions = questionService.getUserQuestions();

        return new ResponseEntity<>(questions,HttpStatus.OK);

    }
    @GetMapping("/tags")
    public ResponseEntity<List<String>> getTags(){
        return new ResponseEntity<>( questionService.getTags(),HttpStatus.OK);
    }


}
