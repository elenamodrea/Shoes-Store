package DAO;

import Tables.Admin;
import Tables.Client;
import Tables.Produs;
import Tables.Review;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ReviewDAO {
    public void createReview(Review review){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the review object
            session.save(review);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void updateReview(Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the review object
            session.update(review);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteReview(String id) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a review object
            Review review = session.get(Review.class, id);
            if (review != null) {
                session.delete(review);
                System.out.println("review is deleted");
            }

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Review getReview (String id) {

        Transaction transaction = null;
        Review review= null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get a review object
            review = session.get(Review.class, id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return review;
    }
    public List<Review> findAllReviews() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> rootEntry = cq.from(Review.class);
        CriteriaQuery<Review> all = cq.select(rootEntry);

        TypedQuery<Review> allQuery = session.createQuery(all);
        return allQuery.getResultList();}
    public Client getClient(Review review) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        // start a transaction
        transaction = session.beginTransaction();
        Review review1 = (Review) session.get(Review.class, review.getIdReview());
         Client client = review1.getClient();
        return client;
    };
}
