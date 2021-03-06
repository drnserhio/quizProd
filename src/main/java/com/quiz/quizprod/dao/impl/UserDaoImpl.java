package com.quiz.quizprod.dao.impl;

import com.quiz.quizprod.dao.QuizDao;
import com.quiz.quizprod.dao.UserDao;
import com.quiz.quizprod.exception.domain.PasswordInvalidException;
import com.quiz.quizprod.exception.domain.QuizExistsException;
import com.quiz.quizprod.exception.domain.UserNotFoundException;
import com.quiz.quizprod.exception.domain.UsernameExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.model.impl.User;
import com.quiz.quizprod.model.role.Role;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManagerFactory entityManagerFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final QuizDao quizDao;

    public UserDaoImpl(EntityManagerFactory entityManagerFactory,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       QuizDao quizDao) {
        this.entityManagerFactory = entityManagerFactory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.quizDao = quizDao;
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

    @Override
    public User registerAccountAdmin(User user)
            throws PasswordInvalidException, UsernameExistsException {
        validate(user.getUsername(), user.getPassword());
        user.setRole(Role.ADMIN.name());
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
    public boolean isExistByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNativeQuery("select count(id) from users where username =:username")
                .setParameter("username", username)
                .getFirstResult() == 1;
    }

    @Override
    public User getUserByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = null;
        try {
            user = (User) entityManager.createNativeQuery("select id, password, role, username from users where username =:username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            entityManager.close();
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
            user = (User) entityManager.createQuery("select usr from User usr where usr.id =:id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
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
    public boolean deleteById(Long id) throws UserNotFoundException {
        boolean isDelete = false;
        if (existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("delete from User usr where usr.id =:id")
                    .setParameter("id", id)
                    .executeUpdate();
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
        boolean isDelete = false;
        try {
            isDelete = entityManager.createQuery("select count(id) from User where id =: id")
                    .setParameter("id", id)
                    .getFirstResult() == 1;
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return isDelete;
    }

    @Override
    public List<Quiz> getFreeQuizByUserId(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Quiz> quizzes = new ArrayList<>();
        try {
            quizzes = entityManager
                    .createNativeQuery("select id, name, notice from quizes where id not in (select quizzes_id from users_quizzes where user_id =:userId)", Quiz.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return quizzes;
    }

    @Override
    public boolean answerTheQuestion(String quizId, String username, String answer, Question question)
            throws QuizExistsException {
        Quiz quiz = getQuizById(Long.valueOf(quizId));
        if (quiz == null) {
            throw new QuizExistsException("Quiz not found");
        }
        return createAnswer(question.getQuestion(), answer, question.getAnswer(), Long.valueOf(quizId), username);
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
        answerUser.setDateStart(new Date());
        if (successAnswer.equals(answer)) {
            answerUser.setIsSuccessfulAnswer(true);
            isSuccessAnswer = true;
        } else {
            answerUser.setIsSuccessfulAnswer(false);
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
                    .setParameter("quizId", quizId)
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return quiz;
    }

    @Override
    public boolean insertQuizToUserAfterTest(Long quizId, Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        boolean isSave = false;
        try {
            transaction.begin();
            entityManager.createNativeQuery("insert into users_quizzes values (:userId, :quizId)")
                    .setParameter("userId", userId)
                    .setParameter("quizId", quizId)
                    .executeUpdate();
//            Quiz quiz = quizDao.findById(quizId);
//            User user = findById(userId);
//            user.getQuizzes().add(quiz);
//            save(user);
            transaction.commit();
            isSave = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return isSave;
    }

    @Override
    public void deleteByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("delete from User where username =:username")
                    .setParameter("username", username)
                    .executeUpdate();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }


}
