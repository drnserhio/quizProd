package com.quiz.quizprod.resource.impl;

import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;
import com.quiz.quizprod.service.AnwerUserService;
import com.quiz.quizprod.service.QuizService;
import com.quiz.quizprod.service.UserService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/admin")
public class AdminDashboard {

    @Autowired
    private UserService userService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private AnwerUserService anwerUserService;



    @GetMapping("/all_users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, OK);
    }

    @PostMapping("/all_user_table")
    public ResponseTable<User> getAllUsersTable(
            @RequestBody RequestTable requestTable) {
      return userService.findAll(requestTable);
    }

    @GetMapping("/get/all_quiz/by/{userId}")
    public List<Quiz> getFreeQuizByUserId(
            @PathVariable Long userId) {
       return userService.getFreeQuizByUserId(userId);
    }

    @GetMapping("/select/quiz/{quizId}")
    public ResponseEntity<Quiz> onSelectQuiz(
            @PathVariable Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        return new ResponseEntity<>(quiz, OK);
    }

    @PostMapping("/all_test_passed/by/quiz_and_user")
    public ResponseEntity<List<AnswerUser>> getAllPassedByUserId(
            @RequestParam String quizId,
            @RequestParam String username) {
        List<AnswerUser> testPassed = anwerUserService.getAllPassedTestByQuizId(quizId, username);
        return new ResponseEntity<>(testPassed, OK);
    }
}
