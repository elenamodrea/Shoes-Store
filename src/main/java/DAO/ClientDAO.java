package DAO;

import Tables.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDAO {
    public void createClient(Client client){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the client object
            session.save(client);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void updateClient(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the client object
            session.update(client);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void updateClientReview(String id, List<Review> review) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Client client = session.load(Client.class, id);
        client.setReviews(review);
        session.update(client);
        // commit transaction
        transaction.commit();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void updateClientOneReview(String id, Review review) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Client client = session.load(Client.class, id);
        List<Review> reviews=client.getReviews();
        reviews.add(review);
        client.setReviews(reviews);
        session.update(client);
        // commit transaction
        transaction.commit();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void updateClientOneCard(String id, Card card) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Client client = session.load(Client.class, id);
        //client.addCard(card);
        List<Card> cards=client.getCards();
        cards.add(card);
        client.setCards(cards);
        session.update(client);
        // commit transaction
        transaction.commit();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void updateClientCard(String id, List<Card> card) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Client client = session.load(Client.class, id);
        client.setCards(card);
        session.update(client);
        // commit transaction
        transaction.commit();
      /*  } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }
    public void updateClientRetur(String id, List<Retur> retur) {
        Transaction transaction = null;
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the client object
            Client client = session.load(Client.class, id);
            client.setReturs(retur);
            session.merge(client);
            // commit transaction
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void updateClientComanda(String id, List<Comanda> comanda) {
        Transaction transaction = null;
        // try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Client client = session.load(Client.class, id);
        client.setComands(comanda);
        session.update(client);
        // commit transaction
        transaction.commit();
        session.close();
        session.close();
//    } catch (Exception e) {
//        if (transaction != null) {
//            transaction.rollback();
//        }
//        e.printStackTrace();
//    }

    }
    public void updateClientOComanda(String id, Comanda comanda) {
        Transaction transaction = null;
         //try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Client client = session.load(Client.class, id);
        List<Comanda> comandaList=client.getComands();
        comandaList.add(comanda);
        client.setComands(comandaList);
        session.merge(client);
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

    public void updateClientUnRetur(String id, Retur retur) {
        Transaction transaction = null;
        //try (
        Session session = HibernateUtil.getSessionFactory().openSession();
        //) {
        // start a transaction
        transaction = session.beginTransaction();
        // save the client object
        Client client = session.load(Client.class, id);
        List<Retur> returList=client.getReturs();
        returList.add(retur);
        client.setReturs(returList);
        session.merge(client);
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
    public void deleteClient(String id) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a client object
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.delete(client);
                System.out.println("client is deleted");
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

    public Client getClient (String id) {

        Transaction transaction = null;
        Client client= null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get a client object
            client = session.get(Client.class, id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return client;
    }
    public Client getClientByUser (User user) {

        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        // start a transaction
        transaction = session.beginTransaction();
        // Create a criteria to search for a user with the specified username
        Criteria criteria = session.createCriteria(Client.class);

        // Add the username as a search criterion
        criteria.add(Restrictions.eq("user", user));

        // Retrieve the user with the specified username
        Client client =(Client) criteria.uniqueResult();

        session.close();

        return client;
    }
    public List<Comanda> getComands(Client client){
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        // start a transaction
        transaction = session.beginTransaction();
        Client client2 = (Client)session.get(Client.class,client.getIdClient());
        List<Comanda> comandaList=client2.getComands();
        return comandaList;
    }
    public List<Card> getCards(Client client){
       // Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        // start a transaction
       // transaction = session.beginTransaction();
        Client client2 = (Client)session.get(Client.class,client.getIdClient());
        List<Card> cardList=client2.getCards();
        return cardList;
    }
    public void closeSession(){
        Session session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.close();
    }
}
