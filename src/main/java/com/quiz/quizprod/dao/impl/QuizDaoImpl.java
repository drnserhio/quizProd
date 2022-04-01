package com.quiz.quizprod.dao.impl;

import com.quiz.quizprod.dao.QuizDao;
import com.quiz.quizprod.exception.domain.QuizExistsException;
import com.quiz.quizprod.exception.domain.AnswerUserNotFoundException;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuizDaoImpl implements QuizDao {

    private final EntityManagerFactory entityManagerFactory;

    public QuizDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Quiz update(Quiz update) {
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
    public Quiz save(Quiz save)
            throws QuizExistsException {
        validateName(save.getName());
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

    private void validateName(String name)
            throws QuizExistsException {
        if (existsByName(name)) {
            throw new QuizExistsException("quiz name already exists");
        }
    }

    private boolean existsByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select quiz from Quiz quiz where quiz.name =:name")
                .setParameter("name", name)
                .getFirstResult() == 1;
    }

    @Override
    public Quiz findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Quiz quiz = null;
        try {
            quiz = (Quiz) entityManager.createQuery("select qiz from Quiz qiz where qiz.id =:id")
                    .setParameter("id", id)
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return quiz;
    }

    @Override
    public List<Quiz> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Quiz> quizzes = new ArrayList<>();
        try {
            quizzes = entityManager.createQuery("select qiz from Quiz qiz")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return quizzes;
    }

    @Override
    public ResponseTable<Quiz> findAll(RequestTable requestTable) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) throws AnswerUserNotFoundException {
        boolean isDelete = false;
        if (existsById(id)) {
            throw new AnswerUserNotFoundException("Quiz not found");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Quiz quiz = new Quiz();
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
        return entityManager.createQuery("select count(quiz) from Quiz quiz where quiz.id =:id")
                .setParameter("id", id)
                .getFirstResult() == 1;
    }


    @Override
    public boolean creatQuizWithQuestion(Long quizId, Long... questionId)
            throws QuizExistsException {
        boolean isSuccessful = false;
        if (findById(quizId) == null) {
            throw new QuizExistsException("Quiz not found.");
        }
        try {
            for (Long qusId : questionId) {
                insertToQuiz(quizId, qusId);
            }
            isSuccessful = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    private void insertToQuiz(Long quizId, Long questionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("insert into quizes_questions values(:quizId, :questionId)")
                    .setParameter("quizId", quizId)
                    .setParameter("questionId", questionId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteFromQuiz(Long quizId, Long questionId)
            throws QuizExistsException {
        boolean isDeleteSuccessful = false;
        if (existsById(quizId)) {
            throw new QuizExistsException("Quiz not found");
        }
        try {
            deletefromQuiz(quizId, questionId);
            isDeleteSuccessful = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleteSuccessful;
    }

    private void deletefromQuiz(Long quizId, Long questId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("delete from quizes_questions where quiz_id =:quizId and questions_id =:questionId")
                    .setParameter("quizId", quizId)
                    .setParameter("questionId", questId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }


}
