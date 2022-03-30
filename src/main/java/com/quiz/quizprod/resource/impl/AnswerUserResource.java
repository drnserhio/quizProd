package com.quiz.quizprod.resource.impl;

import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.resource.DefaultResource;
import com.quiz.quizprod.service.AnwerUserService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public record AnswerUserResource(
        AnwerUserService anwerUserService) implements DefaultResource<AnswerUser> {

    @PutMapping("/update")
    @Override
    public AnswerUser update(AnswerUser update) {
        return anwerUserService.update(update);
    }

    @PostMapping("/save")
    @Override
    public AnswerUser save(AnswerUser save) throws Exception {
        return anwerUserService.save(save);
    }

    @GetMapping("/get/{id}")
    @Override
    public AnswerUser findById(Long id) {
        return anwerUserService.findById(id);
    }

    @GetMapping("/all")
    @Override
    public List<AnswerUser> findAll() {
        return anwerUserService.findAll();
    }

    @PostMapping("/get/table")
    @Override
    public ResponseTable<AnswerUser> findAll(RequestTable requestTable) {
        return anwerUserService.findAll(requestTable);
    }

    @GetMapping("/delete/{id}")
    @Override
    public boolean deleteById(Long id) throws Exception {
        return anwerUserService.deleteById(id);
    }

    @PostMapping("/get_passed_test")
    public List<AnswerUser> getAllPassedTestByQuizId(
            @RequestParam("quizId") String quizId,
            @RequestParam("username") String username) {
        return anwerUserService.getAllPassedTestByQuizId(quizId, username);
    }

    @PostMapping("/delete_by_close_test")
    public boolean deleteAllAnswersIfCloseTest(
            @RequestParam String quizId,
            @RequestParam String username) {
        return anwerUserService.deleteAllAnswersIfCloseTest(quizId, username);
    }
}
