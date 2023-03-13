package DAO;
import Tables.Angajat;
import Tables.Produs;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AngajatDAO {
    public void createAngajat(Angajat angajat){
        Transaction transaction = null;
        //try (
         Session session = HibernateUtil.getSessionFactory().openSession();//) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the angajat object
            session.save(angajat);
            // commit transaction
            transaction.commit();
        /*} catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void updateAngajat(Angajat angajat) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the angajat object
            session.update(angajat);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteAngajat(String id) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete an angajat object
            Angajat angajat = session.get(Angajat.class, id);
            if (angajat != null) {
                session.delete(angajat);
                System.out.println("angajat is deleted");
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

    public Angajat getAngajat (String id) {

        Transaction transaction = null;
        Angajat angajat= null;
        //try (
         Session session = HibernateUtil.getSessionFactory().openSession();//) {
            // start a transaction
            transaction = session.beginTransaction();
            // get a angajat object
            angajat = session.get(Angajat.class, id);
            // commit transaction
            transaction.commit();
        /* catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
        return angajat;
    }


    public List<Angajat> findAllAngajat() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Angajat> cq = cb.createQuery(Angajat.class);
        Root<Angajat> rootEntry = cq.from(Angajat.class);
        CriteriaQuery<Angajat> all = cq.select(rootEntry);

        TypedQuery<Angajat> allQuery = session.createQuery(all);
        return allQuery.getResultList();}
   /* public List<Angajat> getAllAngajat () {

        Transaction transaction = null;
        List<Angajat> angajat= null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get a angajat object
            Query<Angajat> query = session.createQuery("from Angajat", Angajat.class);
            angajat = query.getResultList();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        /*for (Angajat a : angajat) {
            System.out.println(a.getIdAngajat() + " " + a.getDurataContract() + " " + a.getSalariu());
        }
        return angajat;
    }*/
}
