package com.project.Discussly.service;

import com.project.Discussly.dto.AddQuestionDto;
import com.project.Discussly.dto.QuestionDto;
import com.project.Discussly.entity.Duplicate;
import com.project.Discussly.entity.Question;
import com.project.Discussly.entity.QuestionTag;
import com.project.Discussly.entity.User;
import com.project.Discussly.repository.QuestionRepository;
import com.project.Discussly.repository.QuestionTagRepository;
import com.project.Discussly.security.QueryUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final AnswerService answerService;
    private final QueryUtils queryUtils;
    private final QuestionTagRepository questionTagRepository;


    public void addQuestion(AddQuestionDto dto) {
        Question question = new Question();
        question.setQuestionText(dto.getQuestionText());
        question.setQuestionTitle(dto.getQuestionTitle());
        question.setUser(queryUtils.getCurrentLoggedInUser());
        question.setUpVotes(0);
        question.setQuestionTags(dto.getTags().stream().map(
                t->{
                    Optional<QuestionTag> op = questionTagRepository.findByTag(t);
                    return op.orElseGet(() -> new QuestionTag(t.toLowerCase()));
                }
        ).collect(Collectors.toSet()));
        questionRepository.save(question);


    }


    public QuestionDto getQuestion(int id) {
        Question q = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question Not Found."));
        QuestionDto dto = entityToDto(q);
        q.setViews(q.getViews()+1);
        questionRepository.save(q);
        return dto;
    }



    public void updateQuestion(int questionId,AddQuestionDto dto) {
        Question q = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question Not Found"));
        User u = queryUtils.getCurrentLoggedInUser();
        if (q.getUser().getId()==u.getId()) {
            q.setQuestionText(dto.getQuestionText());
            q.setQuestionTitle(dto.getQuestionTitle());
            q.setQuestionTags(dto.getTags().stream().map(
                    t->{
                        Optional<QuestionTag> op = questionTagRepository.findByTag(t);
                        return op.orElseGet(() -> new QuestionTag(t.toLowerCase()));
                    }
            ).collect(Collectors.toSet()));
            questionRepository.save(q);
        } else {
            throw new RuntimeException("Unauthorized");
        }

    }

    public List<QuestionDto> getAllQuestions(int pageNo) {
        return questionRepository.findAllByOrderByTimestampDesc(PageRequest.of(pageNo, 10)).stream().map(this::entityToDtoLazy).collect(Collectors.toList());
    }

    public QuestionDto entityToDto(Question q) {
        QuestionDto dto = entityToDtoLazy(q);
        dto.setAnswers(q.getAnswers().stream().map(answerService::entityToDto).collect(Collectors.toList()));
        dto.setUpVoted(q.getUpVotedUsers().contains(queryUtils.getCurrentLoggedInUser()));
        if(q.getDuplicate()!=null){
            dto.setOriginalQuestionId(q.getDuplicate().getQuestionId());
            dto.setDupMarkingUserId(q.getDuplicate().getUser().getId());
        }
        return dto;
    }

    public QuestionDto entityToDtoLazy(Question q) {
        QuestionDto dto = new QuestionDto();
        dto.setQuestionText(q.getQuestionText());
        dto.setQuestionTitle(q.getQuestionTitle());
        dto.setId(q.getId());
        dto.setTimestamp(q.getTimestamp());
        dto.setVoteCount(q.getUpVotes());
        dto.setAnswerCount(q.getAnswers().size());
        dto.setUserId(q.getUser().getId());
        dto.setTags(q.getQuestionTags().stream().map(QuestionTag::getTag).collect(Collectors.toList()));
        dto.setViews(q.getViews());

        return dto;
    }

    public Set<QuestionDto> searchQuestion(String question, int page) {
        String words = question.replace(" ","&");
        List<QuestionDto> searchResults = questionRepository.search(words,PageRequest.of(page,10)).stream().map(this::entityToDto).collect(Collectors.toList());
        searchResults.addAll(searchByTags(question.replace(" ","|"),page));
        return new HashSet<>(searchResults);
    }
    public void deleteQuestion(int id){
        Question question = questionRepository.findById(id).orElseThrow(()->new RuntimeException("Question Not Found"));
        User requestingUser = queryUtils.getCurrentLoggedInUser();
        if(question.getUser().getId()==requestingUser.getId()){
            questionRepository.delete(question);
        }else{
            throw new RuntimeException("Unauthorized");
        }
    }
    public List<QuestionDto> searchByTags(String tags,int page){

        Set<QuestionTag> tagset= questionTagRepository.findAllByTags(tags);
        for(QuestionTag t:tagset){
            log.info(t.getTag());
        }

      return questionRepository.findByQuestionTagsIn(new ArrayList<>(tagset),PageRequest.of(page,10)).stream().map(this::entityToDto).collect(Collectors.toList());
//        return questionRepository.findByQuestionTagsIn(new ArrayList<>(tagset),PageRequest.of(page,10));

    }


    public void markDuplicate(int id,String link) {
        String[] a = link.split("/");
        int orgId = Integer.parseInt(a[a.length-1]);
        boolean exist = questionRepository.existsById(id);
        if(exist){
            Question q = questionRepository.findById(id).orElseThrow(()->new RuntimeException("Question do not exist"));
            User u = queryUtils.getCurrentLoggedInUser();
            q.setDuplicate(new Duplicate(orgId,u));
            questionRepository.save(q);

        }
    }

    public List<QuestionDto> getUserQuestions() {
        User u = queryUtils.getCurrentLoggedInUser();
        return questionRepository.findAllByUserOrderByTimestampDesc(u).stream().map(this::entityToDtoLazy).collect(Collectors.toList());
    }

    public List<String> getTags() {
        return questionTagRepository.getsomeTags();
    }
}
