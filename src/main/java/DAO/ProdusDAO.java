package DAO;

import Tables.*;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProdusDAO {
    public void createProdus(Produs produs){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(produs);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void updateProdus(Produs produs) {
        Transaction transaction = null;
        //try (
        Session session = HibernateUtil.getSessionFactory().openSession();//) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the admin object
            session.merge(produs);
            // commit transaction
            transaction.commit();
        //} //catch (Exception e) {
            //if (transaction != null) {
            //    transaction.rollback();
            //}
            //e.printStackTrace();
        //}
    }
    public void updateProdusReview(String id, List<Review> review) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Produs produs = session.load(Produs.class, id);
        produs.setReviews(review);
        session.update(produs);
        // commit transaction
        transaction.commit();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void updateProdusOneReview(String id, Review review) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Produs produs = session.load(Produs.class, id);
        List<Review> reviews=produs.getReviews();
        reviews.add(review);
        produs.setReviews(reviews);
        session.update(produs);
        session.clear();
        // commit transaction
        transaction.commit();

      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void deleteProdus(String id) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a course object
            Produs produs = session.get(Produs.class, id);
            if (produs != null) {
                session.delete(produs);
                System.out.println("produs is deleted");
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

    public Produs getProdus (String id) {

        Transaction transaction = null;
        Produs produs = null;
        //try (
        Session session = HibernateUtil.getSessionFactory().openSession();//) {
        // start a transaction
        transaction = session.beginTransaction();
        // get a course object
        produs = session.get(Produs.class, id);
        // commit transaction
        transaction.commit();
        //} catch (Exception e) {
        //if (transaction != null) {
        //   transaction.rollback();
        //}
        //e.printStackTrace();
        //}
        return produs;
    }
    public List<Produs> findAllProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Produs> cq = cb.createQuery(Produs.class);
        Root<Produs> rootEntry = cq.from(Produs.class);
        CriteriaQuery<Produs> all = cq.select(rootEntry);

        TypedQuery<Produs> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }
    public List<Produs> getAllProductsWithStockZero(){
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        // start a transaction
        transaction = session.beginTransaction();

        Criteria criteria= session.createCriteria(Produs.class);
        criteria.add(Restrictions.eq("stoc",0));
        List<Produs> produses=criteria.list();

        session.close();

        return produses;
    }
    public List<Review> getReviews(Produs produs) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        // start a transaction
        transaction = session.beginTransaction();
        Produs produs1 = (Produs) session.get(Produs.class, produs.getIdProdus());
        List<Review> reviews = produs1.getReviews();
        return reviews;
    }

    public void updateRating(String produsId,String newRating){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Produs product = (Produs) session.get(Produs.class, produsId);
            product.setRating(newRating);
            session.save(product);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateStoc(String produsId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Produs product = (Produs) session.get(Produs.class, produsId);
            product.setStoc(product.getStoc()-1);
            session.save(product);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateComandaOComanda(String id, Comanda comanda) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Produs produs = session.load(Produs.class, id);
        List<Comanda> comandaList=produs.getComenzi();
        comandaList.add(comanda);
        produs.setComenzi(comandaList);
        session.update(produs);
        session.clear();
        // commit transaction
        transaction.commit();
        session.close();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public static void main(String[] args) {

    }
}