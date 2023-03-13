import Ajutor.WriteReadFile;
import Business.ClientBLL;
import Business.LoginBLL;
import DAO.*;
import Tables.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientBLLTest {
private ClientBLL clientBLL;
    private UserDAO userDAOMock;
    private ClientDAO clientDAOMock;
    private ProdusDAO produsDAOMock;
    private ComandaDAO comandaDAOMock;
    private ReviewDAO reviewDAOMock;
    @Before
    public void setup(){
        userDAOMock=mock(UserDAO.class);
        clientDAOMock=mock(ClientDAO.class);
        produsDAOMock=mock(ProdusDAO.class);
        comandaDAOMock=mock(ComandaDAO.class);
        reviewDAOMock=mock(ReviewDAO.class);
        clientBLL=new ClientBLL(userDAOMock,clientDAOMock,produsDAOMock,comandaDAOMock,reviewDAOMock);

    }

    @Test
    public void checkGetProfile(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        Client client=new Client("12",user);
        when(clientDAOMock.getClientByUser(user)).thenReturn(client);
        List<Card> cards=new ArrayList<>();
        Card card=new Card("123","4140497071231234",256,"alin","11/22");
        cards.add(card);
        when(clientDAOMock.getCards(client)).thenReturn(cards);
        assertEquals(clientBLL.getProfile("alinion"),"Ion,Alin,Cluj-Napoca,ionalin@yahoo.com,alinion,1234,4140497071231234,256,11/22");
    }

    @Test
    public void checkProductsListAllItems(){
        List<Produs> produses=new ArrayList<>();
        produses.add(new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5"));
        produses.add(new Produs("12345","albastru","44","200",10,"incaltaminte","barbati","2.5"));
        produses.add(new Produs("12346","negru","45","358",10,"incaltaminte","fete","2.5"));
        produses.add(new Produs("12347","rosu","46","259",10,"incaltaminte","baieti","2.5"));
        when(produsDAOMock.findAllProducts()).thenReturn(produses);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="all items";
        assertEquals(clientBLL.productsList(req),"1234 incaltaminte alb 256 43,12345 incaltaminte albastru 200 44,12346 incaltaminte negru 358 45,12347 incaltaminte rosu 259 46");
    }
    @Test
    public void checkProductsListFemei(){
        List<Produs> produses=new ArrayList<>();
        produses.add(new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5"));
        produses.add(new Produs("12345","albastru","44","200",10,"incaltaminte","barbati","2.5"));
        produses.add(new Produs("12346","negru","45","358",10,"incaltaminte","fete","2.5"));
        produses.add(new Produs("12347","rosu","46","259",10,"incaltaminte","baieti","2.5"));
        when(produsDAOMock.findAllProducts()).thenReturn(produses);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="Femei";
        assertEquals(clientBLL.productsList(req),"1234 incaltaminte alb 256 43");
    }
    @Test
    public void checkProductsListBarbati(){
        List<Produs> produses=new ArrayList<>();
        produses.add(new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5"));
        produses.add(new Produs("12345","albastru","44","200",10,"incaltaminte","barbati","2.5"));
        produses.add(new Produs("12346","negru","45","358",10,"incaltaminte","fete","2.5"));
        produses.add(new Produs("12347","rosu","46","259",10,"incaltaminte","baieti","2.5"));
        when(produsDAOMock.findAllProducts()).thenReturn(produses);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="Barbati";
        assertEquals(clientBLL.productsList(req),"12345 incaltaminte albastru 200 44");
    }
    @Test
    public void checkProductsBaieti(){
        List<Produs> produses=new ArrayList<>();
        produses.add(new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5"));
        produses.add(new Produs("12345","albastru","44","200",10,"incaltaminte","barbati","2.5"));
        produses.add(new Produs("12346","negru","45","358",10,"incaltaminte","fete","2.5"));
        produses.add(new Produs("12347","rosu","46","259",10,"incaltaminte","baieti","2.5"));
        when(produsDAOMock.findAllProducts()).thenReturn(produses);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="Baieti";
        assertEquals(clientBLL.productsList(req),"12347 incaltaminte rosu 259 46");
    }
    @Test
    public void checkProductsFete(){
        List<Produs> produses=new ArrayList<>();
        produses.add(new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5"));
        produses.add(new Produs("12345","albastru","44","200",10,"incaltaminte","barbati","2.5"));
        produses.add(new Produs("12346","negru","45","358",10,"incaltaminte","fete","2.5"));
        produses.add(new Produs("12347","rosu","46","259",10,"incaltaminte","baieti","2.5"));
        when(produsDAOMock.findAllProducts()).thenReturn(produses);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="Fete";
        assertEquals(clientBLL.productsList(req),"12346 incaltaminte negru 358 45");
    }
    @Test
    public void checkgetEmail(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="alinion";
        assertEquals(clientBLL.getEmail(req),"ionalin@yahoo.com");
    }
    @Test
    public void checkgetComenzi(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        Client client=new Client("12",user);
        when(clientDAOMock.getClientByUser(user)).thenReturn(client);
        List<Comanda> comandas=new ArrayList<>();
        Comanda comanda=new Comanda("1234","12/11/2022","card","256","4145494041111269");
        comandas.add(comanda);
        when(clientDAOMock.getComands(client)).thenReturn(comandas);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="alinion";
        assertEquals(clientBLL.getComenzi(req),"1234 12/11/2022 256");
    }
    @Test
    public void checkgetProduseComanda(){
        Comanda comanda= new Comanda("1234","10/01/2023","card","256","4140126925689875");
        when(comandaDAOMock.getComanda("1234")).thenReturn(comanda);
        List<Produs> produses=new ArrayList<>();
        Produs produs=new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5");
        produses.add(produs);
        when(comandaDAOMock.getProduses(comanda)).thenReturn(produses);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="alinion";
        req[3]="1234";
        assertEquals(clientBLL.getProduseComanda(req),"1234 incaltaminte alb 256 43");
    }
    @Test
    public void checkgetInformatiiProdus(){
        Produs produs=new Produs("MwJj3NrfwKqI5jaC","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("MwJj3NrfwKqI5jaC")).thenReturn(produs);
        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="MwJj3NrfwKqI5jaC";
        assertEquals(clientBLL.getInformatiiProdus(req),"incaltaminte,256,femei,43,10,alb,2.5,redPuma.jpg");
    }
    @Test
    public void checkadaugaReviewExistent(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        Client client=new Client("12",user);
        when(clientDAOMock.getClientByUser(user)).thenReturn(client);
        Produs produs=new Produs("sWF20gJ8Y9xDOiyA","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("sWF20gJ8Y9xDOiyA")).thenReturn(produs);
        Review review = new Review("123","niiice","5");
        List<Review> reviews=new ArrayList<>();
        reviews.add(review);
        when(produsDAOMock.getReviews(produs)).thenReturn(reviews);
        when(reviewDAOMock.getClient(review)).thenReturn(client);

        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="alinion";
        req[3]="sWF20gJ8Y9xDOiyA";
        assertEquals(clientBLL.adaugaReview(req),"review deja existent");
    }
    @Test
    public void checkadaugaReviewNeexistent(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        Client client=new Client("12",user);
        when(clientDAOMock.getClientByUser(user)).thenReturn(client);
        Client client1=new Client("13",user);
        Produs produs=new Produs("sWF20gJ8Y9xDOiyA","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("sWF20gJ8Y9xDOiyA")).thenReturn(produs);
        Review review = new Review("123","niiice","5");
        List<Review> reviews=new ArrayList<>();
        reviews.add(review);
        when(produsDAOMock.getReviews(produs)).thenReturn(reviews);
        when(reviewDAOMock.getClient(review)).thenReturn(client1);

        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="alinion";
        req[3]="sWF20gJ8Y9xDOiyA";
        assertEquals(clientBLL.adaugaReview(req),"review adaugat");
    }
    @Test
    public void checkgetReviews(){
        Produs produs=new Produs("sWF20gJ8Y9xDOiyA","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("sWF20gJ8Y9xDOiyA")).thenReturn(produs);
        Review review = new Review("123","niiice","5");
        List<Review> reviews=new ArrayList<>();
        reviews.add(review);
        when(produsDAOMock.getReviews(produs)).thenReturn(reviews);

        String[] req=new String[10];
        req[0]="client";
        req[1]="produs";
        req[2]="sWF20gJ8Y9xDOiyA";
        req[3]="sWF20gJ8Y9xDOiyA";
        assertEquals(clientBLL.getReviews(req),"niiice 5");
    }
    @Test
    public void checkgetProdusePentruComanda(){
        Produs produs=new Produs("sWF20gJ8Y9xDOiyA","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("sWF20gJ8Y9xDOiyA")).thenReturn(produs);

        String[] req=new String[3];
        req[0]="client";
        req[1]="produs";
        req[2]="sWF20gJ8Y9xDOiyA";
        assertEquals(clientBLL.getProdusePentruComanda(req),"sWF20gJ8Y9xDOiyA incaltaminte 256 femei 43 10 alb ");
    }
    @Test
    public void checkTrimiteFavorit() throws IOException {
        Produs produs=new Produs("sWF20gJ8Y9xDOiyA","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("sWF20gJ8Y9xDOiyA")).thenReturn(produs);

        String[] req=new String[3];
        req[0]="client";
        req[1]="produs";
        req[2]="123 sWF20gJ8Y9xDOiyA";
        assertEquals(clientBLL.trimiteFavorit(req),"deja in stoc");
    }
    @Test
    public void checkTrimiteFavoritTrimis() throws IOException {
        Produs produs=new Produs("sWF20gJ8Y9xDOiyA","alb","43","256",0,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("sWF20gJ8Y9xDOiyA")).thenReturn(produs);

        String[] req=new String[3];
        req[0]="client";
        req[1]="produs";
        req[2]="123 sWF20gJ8Y9xDOiyA";
        assertEquals(clientBLL.trimiteFavorit(req),"favorit trimis");
    }


}
