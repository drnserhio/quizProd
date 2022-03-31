package com.quiz.quizprod.resource.impl;

import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.resource.DefaultResource;
import com.quiz.quizprod.service.QuestionService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public record QuestionResource(QuestionService questionService) implements DefaultResource<Question> {

    @PutMapping("/update")
    @Override
    public Question update(
            @RequestBody Question update) {
        return questionService.update(update);
    }

    @PostMapping("/save")
    @Override
    public Question save(
            @RequestBody Question save) throws Exception {
        return questionService.save(save);
    }

    @GetMapping("/get/{id}")
    @Override
    public Question findById(
            @PathVariable("id") Long id) {
        return questionService.findById(id);
    }


    @GetMapping("/all")
    @Override
    public List<Question> findAll() {
        return questionService.findAll();
    }

    @PostMapping("/get/table")
    @Override
    public ResponseTable<Question> findAll(
            @RequestBody RequestTable requestTable) {
        return questionService.findAll(requestTable);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public boolean deleteById(
            @PathVariable("id") Long id) throws Exception {
        return questionService.deleteById(id);
    }

    @GetMapping("/get_questin_in_quiz/{quizId}")
    public List<Question> getAllQuestionWithoutInQuiz(
            @PathVariable("quizId") String quizId) {
        return questionService.getAllQuestionWithoutInQuiz(quizId);
    }
}
