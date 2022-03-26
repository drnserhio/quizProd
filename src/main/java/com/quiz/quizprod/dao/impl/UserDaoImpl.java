package com.quiz.quizprod.dao.impl;

import com.quiz.quizprod.dao.UserDao;
import com.quiz.quizprod.exception.*;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.model.impl.User;
import com.quiz.quizprod.model.role.Role;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.hibernate.jpa.QueryHints;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManagerFactory entityManagerFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDaoImpl(EntityManagerFactory entityManagerFactory, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.entityManagerFactory = entityManagerFactory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User registerAccount(User user)
            throws PasswordInvalidException, UsernameExistsException {
        validate(user.getUsername(), user.getPassword());
        user.setRole(Role.USER.name());
        user.setQuizzes(new ArrayList<>());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return save(user);
    }

    private void validate(String username, String password)
            throws PasswordInvalidException, UsernameExistsException {
        if (getUserByUsername(username) != null) {
            throw new UsernameExistsException("User already exists");
        }
        if (password.length() < 3) {
            throw new PasswordInvalidException("Password is low. Put on mote then 3 characters");
        }
    }

    @Override
    public User getUserByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = null;
        try {
           user = (User) entityManager.createQuery("select usr from User usr where usr.username =:username")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .setParameter("username", username)
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public User update(User update) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(update);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return update;
    }

    @Override
    public User save(User save) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(save);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return save;
    }

    @Override
    public User findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = null;
        try {
            user = (User) entityManager.createQuery("select usr from User usr where usr.id =:id")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .setParameter("id", id)
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users = new ArrayList<>();
        try {
            users = entityManager.createQuery("select usr from User usr")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public ResponseTable<User> findAll(RequestTable requestTable) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) throws AnswerUserFoundException {
        boolean isDelete = false;
        if (existsById(id)) {
            throw new AnswerUserFoundException("User not found");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            AnswerUser quiz = new AnswerUser();
            quiz.setId(id);
            entityManager.remove(quiz);
            transaction.commit();
            isDelete = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return isDelete;
    }

    @Override
    public boolean existsById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select count(id) from User where id =: id")
                .setParameter("id", id)
                .setHint(QueryHints.HINT_READONLY, true)
                .getFirstResult() == 1;
    }

    @Override
    public List<Quiz> getFreeQuizByUserId(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Quiz> quizzes = new ArrayList<>();
        try {
            quizzes =  entityManager
                    .createNativeQuery("select id, count_question, date_end, date_start, name, notice from quizes where id not in (select quizzes_id from users_quizzes where user_id =:userId)")
                    .setParameter("userId", userId)
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return quizzes;
    }

    @Override
    public Quiz onSelectQuiz(Long quizId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Quiz quiz = null;
        try {
            quiz = (Quiz) entityManager.createQuery("select quiz from Quiz quiz where quiz.id =:id")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList().get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return quiz;
    }

    @Override
    public boolean answerTheQuestion(Long quizId, String username, String answer, Question question)
            throws QuizExistsException {
        Quiz quiz = getQuizById(quizId);
        if (quiz == null) {
            throw new QuizExistsException("Quiz not found");
        }
        return createAnswer(question.getQuestion(), answer, question.getAnswer(), quizId, username);
    }

    private boolean createAnswer(String question, String answer, String successAnswer, Long quizId, String username) {
        boolean isSuccessAnswer = false;

        AnswerUser answerUser = new AnswerUser();
        answerUser.setQuestion(question);
        answerUser.setAnswerUser(answer);
        answerUser.setAnswerSuccessful(successAnswer);
        answerUser.setUsernameActive(username);
        answerUser.setQuizId(quizId);
        answerUser.setIdCode(username + quizId);
        if (successAnswer.equals(answer)) {
            answerUser.setSuccessfulAnswer(true);
            isSuccessAnswer = true;
        } else {
            answerUser.setSuccessfulAnswer(false);
        }
        saveAnswer(answerUser);
        return isSuccessAnswer;
    }

    private void saveAnswer(AnswerUser answerUser) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(answerUser);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Quiz getQuizById(Long quizId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Quiz quiz = null;
        try {
            quiz = (Quiz) entityManager.createQuery("select qiz from Quiz qiz where qiz.id =:quizId")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .setParameter("quizId", quizId)
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return quiz;
    }


}
