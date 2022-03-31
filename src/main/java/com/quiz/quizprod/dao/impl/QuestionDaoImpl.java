package com.quiz.quizprod.dao.impl;

import com.quiz.quizprod.dao.QuestionDao;
import com.quiz.quizprod.exception.QuestionNotFoundException;
import com.quiz.quizprod.model.impl.Question;
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
public class QuestionDaoImpl implements QuestionDao {

    private final EntityManagerFactory entityManagerFactory;

    public QuestionDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Question update(Question update) {
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
    public Question save(Question save) {
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
    public Question findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question question = null;
        try {
            question = (Question) entityManager.createQuery("select qst from Question qst where qst.id =:id")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .setParameter("id", id)
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public List<Question> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Question> questions = new ArrayList<>();
        try {
            questions = entityManager.createQuery("select qst from Question qst")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public ResponseTable<Question> findAll(RequestTable requestTable) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) throws QuestionNotFoundException {
        boolean isDelete = false;
        if (existsById(id)) {
            throw new QuestionNotFoundException("Question not found");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("delete from Question q where q.id =:id")
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
        return entityManager.createQuery("select count(id) from Question where id =: id")
                .setParameter("id", id)
                .setHint(QueryHints.HINT_READONLY, true)
                .getFirstResult() == 1;
    }

    @Override
    public List<Question> getAllQuestionWithoutInQuiz(String quizId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Question> questions = new ArrayList<>();
        try {
           questions = entityManager.createNativeQuery("select id, answer, bad_first_answer, bad_second_answer, question from questions quest where quest.id not in (select questions_id from quizes_questions where quiz_id =:quizId)", Question.class)
                    .setParameter("quizId", quizId)
                   .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return questions;
    }
}
