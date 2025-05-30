package com.project.Discussly.service;

import com.project.Discussly.dto.AnswerDto;
import com.project.Discussly.entity.Answer;
import com.project.Discussly.entity.User;
import com.project.Discussly.repository.AnswerRepository;
import com.project.Discussly.repository.QuestionRepository;
import com.project.Discussly.security.QueryUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    public final QueryUtils queryUtils;

    public void addAnswer(int questionId, String answer) {

        Answer ans = new Answer();
        ans.setAnswer(answer);
        ans.setQuestion(questionRepository.findById(questionId).orElseThrow());
        ans.setUser(queryUtils.getCurrentLoggedInUser());
        ans.setUpVotes(0);
        answerRepository.save(ans);
    }

    public void updateAnswer(int id, String answer) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Answer ans = answerRepository.findById(id).orElseThrow(() -> new RuntimeException("Answer does not exist."));
        if (ans.getUser().getEmail().equals(email)) {
            ans.setAnswer(answer);
            answerRepository.save(ans);
        } else {
            throw new RuntimeException("Unauthorized");
        }

    }

    public AnswerDto entityToDto(Answer answer) {
        AnswerDto dto = new AnswerDto();
        dto.setId(answer.getId());
        dto.setAnswer(answer.getAnswer());
        dto.setUserId(answer.getUser().getId());
        dto.setTimestamp(answer.getTimestamp());
        dto.setVoteCount(answer.getUpVotes());
        dto.setUpVoted(answer.getUpVotedUsers().contains(queryUtils.getCurrentLoggedInUser()));
        return dto;
    }

    public void deleteAnswer(int id) {
        Answer answer = answerRepository.findById(id).orElseThrow();
        User loggedInUser = queryUtils.getCurrentLoggedInUser();
        if(answer.getUser().getId()==loggedInUser.getId()){
            answerRepository.delete(answer);
        }else{
            throw new RuntimeException("Unauthorized");
        }
    }

    public long getAnswerCount() {
        User u = queryUtils.getCurrentLoggedInUser();
        return answerRepository.countAllByUser(u);

    }
}
