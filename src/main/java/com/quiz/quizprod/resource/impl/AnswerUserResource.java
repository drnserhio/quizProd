package com.quiz.quizprod.resource.impl;

import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.resource.DefaultResource;
import com.quiz.quizprod.service.AnwerUserService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answerUser")
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
}
