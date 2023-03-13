package DAO;

import Tables.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ComandaDAO {
    public void createComanda(Comanda comanda){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the comanda object
            session.save(comanda);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void updateComanda(Comanda comanda) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the comanda object
            session.merge(comanda);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void updateComandaProduse(String id, List<Produs> produs) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Comanda comanda = session.load(Comanda.class, id);
        comanda.setProduse(produs);
        session.update(comanda);
        // commit transaction
        transaction.commit();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void updateComandaUnProdus(String id, Produs produs) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Comanda comanda = session.load(Comanda.class, id);
        List<Produs> produses=comanda.getProduse();
        produses.add(produs);
        comanda.setProduse(produses);
        session.update(comanda);
        // commit transaction
        transaction.commit();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }


    public void deleteComanda(String id) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a comanda object
            Comanda comanda = session.get(Comanda.class, id);
            if (comanda != null) {
                session.delete(comanda);
                System.out.println("comanda is deleted");
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

    public Comanda getComanda (String id) {

        Transaction transaction = null;
        Comanda comanda= null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get a comanda object
            comanda = session.get(Comanda.class, id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return comanda;
    }

    public String getDataComanda(String id){
        Transaction transaction = null;
        Comanda comanda= null;
        String data="";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get a comanda object
            comanda = session.get(Comanda.class, id);

            if(comanda!=null){
                data=comanda.getData();
            }
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return data;
    }

    public List<Comanda> findAllComands() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Comanda> cq = cb.createQuery(Comanda.class);
        Root<Comanda> rootEntry = cq.from(Comanda.class);
        CriteriaQuery<Comanda> all = cq.select(rootEntry);

        TypedQuery<Comanda> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }
    public List<Produs> getProduses(Comanda comanda) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        // start a transaction
        transaction = session.beginTransaction();
        Comanda comanda1 = (Comanda) session.get(Comanda.class, comanda.getIdComanda());
        List<Produs> produses = comanda1.getProduse();
        return produses;
    };
}