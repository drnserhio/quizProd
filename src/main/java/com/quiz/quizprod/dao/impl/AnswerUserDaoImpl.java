package com.quiz.quizprod.dao.impl;

import com.quiz.quizprod.dao.AnswerUserDao;
import com.quiz.quizprod.exception.domain.AnswerUserNotFoundException;
import com.quiz.quizprod.model.impl.AnswerUser;
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
public class AnswerUserDaoImpl implements AnswerUserDao {

    private final EntityManagerFactory entityManagerFactory;

    public AnswerUserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public AnswerUser update(AnswerUser update) {
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
    public AnswerUser save(AnswerUser save) {
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
    public AnswerUser findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        AnswerUser rmQuiz = null;
        try {
            rmQuiz = (AnswerUser) entityManager.createQuery("select rmQuiz from AnswerUser rmQuiz where rmQuiz.id =:id")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .setParameter("id", id)
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return rmQuiz;
    }

    @Override
    public List<AnswerUser> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<AnswerUser> answerUsers = new ArrayList<>();
        try {
            answerUsers = entityManager.createQuery("select rmQuiz from AnswerUser rmQuiz")
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return answerUsers;
    }

    @Override
    public ResponseTable<AnswerUser> findAll(RequestTable requestTable) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) throws AnswerUserNotFoundException {
        boolean isDelete = false;
        if (existsById(id)) {
            throw new AnswerUserNotFoundException("Answer user not found");
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
        return entityManager.createQuery("select count(id) from AnswerUser where id =: id")
                .setParameter("id", id)
                .setHint(QueryHints.HINT_READONLY, true)
                .getFirstResult() == 1;
    }

    @Override
    public List<AnswerUser> getAllPassedTestByQuizId(String quizId, String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<AnswerUser> answers = new ArrayList<>();
        try {
           answers = entityManager.createNativeQuery("select id, answer_successful, answer_user, date_end, date_start, id_code, is_successful_answer, question, quiz_id, username_active from answers_users where quiz_id =:quizId and username_active =:activeUsername", AnswerUser.class)
                    .setParameter("quizId", quizId)
                    .setParameter("activeUsername", username)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public boolean deleteAllAnswersIfCloseTest(String quizId, String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        boolean isDelete = false;
        try {
            transaction.begin();
            entityManager.createNativeQuery("delete from answers_users where quiz_id =:quizId and username_active =:username")
                    .setParameter("quizId", quizId)
                    .setParameter("username", username)
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
}
