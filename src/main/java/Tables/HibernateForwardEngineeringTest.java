package Tables;

import DAO.*;
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import Tables.*;

import java.util.ArrayList;
import java.util.List;

public class HibernateForwardEngineeringTest {
    public static void main(String[] args) {
       Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        RandomID randomID = new RandomID();
        ClientDAO clientDAO=new ClientDAO();
        CardDAO cardDAO = new CardDAO();
        UserDAO userDAO=new UserDAO();
        AdminDAO adminDAO=new AdminDAO();
        AngajatDAO angajatDAO=new AngajatDAO();
        ComandaDAO comandaDAO=new ComandaDAO();
        ProdusDAO produsDAO=new ProdusDAO();
        ReturDAO returDAO=new ReturDAO();

           User user1= new User(randomID.getRandomID(), "Popescu2", "Ghita","123546","Cluj-Napoca","a3bcd@yahoo.com","admin","ghita1223","1234");
            User user2=new User(randomID.getRandomID(), "Popescu123", "Gheorghita","123546","Cluj-Napoca","abcde@yahoo.com","client","ghita1234","1234");
            User user3=new User(randomID.getRandomID(), "Popescu124", "Lica","123546","Cluj-Napoca","abcdef@yahoo.com","angajat","ghita12345","1234");
            Admin admin=new Admin(randomID.getRandomID(), user1);
        Angajat angajat=new Angajat(randomID.getRandomID(),250,"2 ani","01/02/2023","01/02/2021",user3);
            Client client=new Client(randomID.getRandomID(), user2);
        userDAO.createUser(user1);
        userDAO.createUser(user2);
        userDAO.createUser(user3);
        adminDAO.createAdmin(admin);
        clientDAO.createClient(client);
        angajatDAO.createAngajat(angajat);
            Card card1=new Card(randomID.getRandomID(), "414045647",789,"Lica","11/25");
            Card card2=new Card(randomID.getRandomID(), "45678926",456,"LicaL","12/26");
            card1.setClient(client);
            card2.setClient(client);
            List<Card> cardList=new ArrayList<>();
            cardList.add(card1);
            cardList.add(card2);
            client.setCards(cardList);
            clientDAO.updateClientCard(client.getIdClient(), cardList);
            //pana aici merge
        Comanda comanda = new Comanda(randomID.getRandomID(), "12/11/2022","card","150","1236456");
        List<Comanda> comandaList=new ArrayList<>();
        comandaList.add(comanda);
        comanda.setClient(client);
        client.setComands(comandaList);
        clientDAO.updateClientComanda(client.getIdClient(), client.getComands());
            Produs produs1=new Produs(randomID.getRandomID(), "roz","38","345",256,"adidasi","femei","0");
            Produs produs2=new Produs(randomID.getRandomID(), "gri","43","789.5",256,"adidasi","barbati","0");
            List<Produs> produsList=new ArrayList<>();
            produsList.add(produs1);
            produsList.add(produs2);
            comanda.setProduse(produsList);
            List<Comanda> comandas=new ArrayList<>();
            comandas.add(comanda);
            produs2.setComenzi(comandas);
            produs1.setComenzi(comandas);
            comandaDAO.updateComandaProduse(comanda.getIdComanda(), produsList);
        Retur retur=new Retur(randomID.getRandomID(), "22/11/2022",comanda);
        List<Retur> returList=new ArrayList<>();
        returList.add(retur);
        client.setReturs(returList);
        retur.setClient(client);
        clientDAO.updateClientRetur(client.getIdClient(),client.getReturs());
            Review review1 = new Review(randomID.getRandomID(), "me gusta","5");
            Review review2=new Review(randomID.getRandomID(), "fain","4.9");
            List<Review> reviewListC=new ArrayList<>();
            reviewListC.add(review1);
            reviewListC.add(review2);
            client.setReviews(reviewListC);
            review1.setClient(client);
            review2.setClient(client);
            clientDAO.updateClientReview(client.getIdClient(), reviewListC);
        List<Review> reviewListP1=new ArrayList<>();
        reviewListP1.add(review1);
        produs1.setReviews(reviewListP1);
        review1.setProdus(produs1);
        produsDAO.updateProdusReview(produs1.getIdProdus(),reviewListP1);
        List<Review> reviewListP2=new ArrayList<>();
        reviewListP2.add(review2);
        produs2.setReviews(reviewListP2);
        review2.setProdus(produs2);
        produsDAO.updateProdusReview(produs2.getIdProdus(),reviewListP2);
      /*  Produs produs1=new Produs(randomID.getRandomID(), "negru","37","345",102,"cizme","femei");
        Produs produs2=new Produs(randomID.getRandomID(), "gri","42","789.5",20,"ghete","barbati");
        Produs produs3=new Produs(randomID.getRandomID(),"negru" ,"36.5","243",20,"sneakers","fete");
        Produs produs4=new Produs(randomID.getRandomID(),"alb","41","658",23,"Jordan","baieti");
        Produs produs5= new Produs(randomID.getRandomID(),"albastru","42.5","120",102,"papuci","baieti");
        Produs produs6 = new Produs(randomID.getRandomID(), "mov","32.5","120",200,"papuci","fete");
        Produs produs7=new Produs(randomID.getRandomID(), "visiniu","37","342",235,"pantofi","femei");
        Produs produs8= new Produs(randomID.getRandomID(),"negru","43","350",100,"pantofi","barbati");
        Produs produs9=new Produs(randomID.getRandomID(),"alb","30","150",25,"cizme","fete");
        Produs produs10=new Produs(randomID.getRandomID(), "gri","39","125",25,"pantofi","baieti");
        produsDAO.createProdus(produs1);
        produsDAO.createProdus(produs2);
        produsDAO.createProdus(produs3);
        produsDAO.createProdus(produs4);
        produsDAO.createProdus(produs5);
        produsDAO.createProdus(produs6);
        produsDAO.createProdus(produs7);
        produsDAO.createProdus(produs8);
        produsDAO.createProdus(produs9);
        produsDAO.createProdus(produs10);*/
        //cardDAO.deleteCard("bl2w8jb0opvA9y9s");

      //  produsDAO.updateProdusReview(produs2.getIdProdus(),reviewListP2);

        //cardDAO.deleteCard("bl2w8jb0opvA9y9s");
        //userDAO.deleteUser("GbGxlrPTh1D9P0cl");

        /*User user1= new User(randomID.getRandomID(), "Radu", "Ciprian","123546","Cluj-Napoca","avjv@yahoo.com","angajat","radu1123","1234");
        User user2=new User(randomID.getRandomID(), "Pop", "Maria","123546","Hunedoara","hjv@yahoo.com","angajat","maria123","1234");
        User user3=new User(randomID.getRandomID(), "Ghimbir", "Gloria","123546","Sibiu","kjvbfdc@yahoo.com","angajat","gloria123","1234");
        User user4=new User(randomID.getRandomID(), "Buzatu", "Silviu","123546","Targu-Mures","ejbf@yahoo.com","angajat","silviu","1234");

        Angajat angajat1=new Angajat(randomID.getRandomID(),2700,"2 ani","17/12/2022","17/12/2021",user1);
        Angajat angajat2=new Angajat(randomID.getRandomID(),3800,"7 ani","04/12/2022","12/09/2005",user2);
        Angajat angajat3=new Angajat(randomID.getRandomID(),4700,"4 ani","29/12/2022","09/01/2022",user3);
        Angajat angajat4=new Angajat(randomID.getRandomID(),1400,"3 ani","12/12/2022","05/12/2016",user4);

        angajatDAO.createAngajat(angajat1);
        angajatDAO.createAngajat(angajat2);
        angajatDAO.createAngajat(angajat3);
        angajatDAO.createAngajat(angajat4);*/
    }
}