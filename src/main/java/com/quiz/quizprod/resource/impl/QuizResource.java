package com.quiz.quizprod.resource.impl;

import com.quiz.quizprod.exception.QuizExistsException;
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

    @GetMapping("/delete/{id}")
    @Override
    public boolean deleteById(
            @PathVariable Long id) throws Exception {
        return quizService.deleteById(id);
    }
}
