package com.quiz.quizprod.resource.impl;

import com.quiz.quizprod.exception.domain.AnswerUserNotFoundException;
import com.quiz.quizprod.exception.domain.QuizExistsException;
import com.quiz.quizprod.model.impl.BodyId;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.resource.DefaultResource;
import com.quiz.quizprod.service.QuizService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public record QuizResource(QuizService quizService) implements DefaultResource<Quiz> {


    @PutMapping("/update")
    @Override
    public Quiz update(
            @RequestBody Quiz update) {
        return quizService.update(update);
    }

    @PostMapping("/save")
    @Override
    public Quiz save(
            @RequestBody Quiz save) throws Exception {
        return quizService.save(save);
    }

    @GetMapping("/get/{id}")
    @Override
    public Quiz findById(
            @PathVariable Long id) {
        return quizService.findById(id);
    }

    @GetMapping("/all")
    @Override
    public List<Quiz> findAll() {
        return quizService.findAll();
    }

    @PostMapping("/get/table")
    @Override
    public ResponseTable<Quiz> findAll(
            @RequestBody RequestTable requestTable) {
        return quizService.findAll(requestTable);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public boolean deleteById(
            @PathVariable Long id) throws Exception {
        return quizService.deleteById(id);
    }

    @PostMapping("/save_question_to_quiz/{quizId}")
    public boolean creatQuizWithQuestion(
            @PathVariable Long quizId,
            @RequestBody BodyId ids)
            throws QuizExistsException, AnswerUserNotFoundException {
        return quizService.creatQuizWithQuestion(quizId, ids.getIds());
    }

    @GetMapping("/delete_question_in_quiz/{quizId}/{questionId}")
    public boolean deleteFromQuiz(
            @PathVariable Long quizId,
            @PathVariable Long questionId) throws QuizExistsException, AnswerUserNotFoundException {
        return quizService.deleteFromQuiz(quizId, questionId);
    }

}
