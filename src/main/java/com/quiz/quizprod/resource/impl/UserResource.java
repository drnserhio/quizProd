package com.quiz.quizprod.resource.impl;

import com.quiz.quizprod.exception.PasswordInvalidException;
import com.quiz.quizprod.exception.QuizExistsException;
import com.quiz.quizprod.exception.UserExistsException;
import com.quiz.quizprod.exception.UsernameExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;
import com.quiz.quizprod.model.principal.CustomUserPrincipal;
import com.quiz.quizprod.resource.DefaultResource;
import com.quiz.quizprod.service.UserService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import com.quiz.quizprod.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserResource implements DefaultResource<User> {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PutMapping("/update")
    @Override
    public User update(User update) {
        return userService.update(update);
    }

    @PostMapping("/save")
    @Override
    public User save(User save) throws Exception {
        return userService.save(save);
    }

    @PostMapping("/get/{id}")
    @Override
    public User findById(
            @PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/get/by/{username}")
    public User findByUsername(
            @PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/all")
    @Override
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/get/table")
    @Override
    public ResponseTable<User> findAll(
            @RequestBody RequestTable requestTable) {
        return userService.findAll(requestTable);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public boolean deleteById(
            @PathVariable("id") Long id) throws Exception {
        return userService.deleteById(id);
    }

    @GetMapping("/get/free_quiz/by/{userId}")
    public List<Quiz> getFreeQuizByUserId(
            @PathVariable("userId") Long userId) {
        return userService.getFreeQuizByUserId(userId);
    }

    @GetMapping("/select/quiz/by/{quizId}")
    public Quiz onSelectQuiz(
            @PathVariable("quizId") Long quizId) {
        return userService.onSelectQuiz(quizId);
    }

    @PostMapping("/answer_question")
    public boolean answerTheQuestion(
            @RequestParam("quizId") Long quizId,
            @RequestParam("username") String username,
            @RequestParam("answer") String answer,
            @RequestParam("question") Question question)
            throws QuizExistsException {
        return userService.answerTheQuestion(quizId, username, answer, question);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody User user)
            throws UserExistsException, PasswordInvalidException, UsernameExistsException {
        User newUser = userService.registerAccount(user);
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestBody User user) {
        authentificated(user.getUsername(), user.getPassword());
        User loginUser = userService.getUserByUsername(user.getUsername());
        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(loginUser);
        HttpHeaders jwtHeaders = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeaders, OK);
    }

    private HttpHeaders getJwtHeader(CustomUserPrincipal customUserPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Jwt-Token", jwtTokenProvider.generateToken(customUserPrincipal));
        return headers;
    }

    private void authentificated(String username, String password) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

}
