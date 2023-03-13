import Ajutor.ConfirmEmail;
import Ajutor.WriteReadFile;
import Business.AngajatBLL;
import Business.ClientBLL;
import DAO.*;
import Tables.*;
import Validatoare.Validatoare;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AngajatBLLTest {

    private UserDAO userDAOMock;
    private ReviewDAO reviewDAOMock;
    private ProdusDAO produsDAOMock;
    private ComandaDAO comandaDAOMock;
    private AngajatBLL angajatBLL;
    private AngajatDAO angajatDAOMock;
    @Before
    public void setup(){
        userDAOMock=mock(UserDAO.class);
        produsDAOMock=mock(ProdusDAO.class);
        comandaDAOMock=mock(ComandaDAO.class);
        reviewDAOMock=mock(ReviewDAO.class);
        angajatBLL=new AngajatBLL(userDAOMock,reviewDAOMock,comandaDAOMock,produsDAOMock);

    }

    @Test
    public void doublecheckProductsFromComand(){
        Comanda comanda=new Comanda("1234","12/11/2022","card","256","4145494041111269");
        when(comandaDAOMock.getComanda("1234")).thenReturn(comanda);
        List<Produs> produses=new ArrayList<>();
        produses.add(new Produs("5612","alb","43","256",10,"incaltaminte","femei","2.5"));
        produses.add(new Produs("12345","albastru","44","200",10,"incaltaminte","barbati","2.5"));
        produses.add(new Produs("12346","negru","45","358",10,"incaltaminte","fete","2.5"));
        produses.add(new Produs("12347","rosu","46","259",0,"incaltaminte","baieti","2.5"));
        when(comandaDAOMock.getProduses(comanda)).thenReturn(produses);
        String[] req=new String[10];
        req[0]="check";
        req[1]="1234";
        assertEquals(angajatBLL.checkProductsFromComand(req),"nu");

    }

    @Test
    public void doubleCheckDataComanda(){
        Comanda comanda=new Comanda("1234","2023-01-10","card","256","4145494041111269");
        when(comandaDAOMock.getDataComanda("1234")).thenReturn(comanda.getData());
        String[] req=new String[10];
        req[0]="dataComanda";
        req[1]="1234";
        req[2]="2023-01-10";
        assertEquals(angajatBLL.checkDataComanda(req),"da");
    }

    @Test
    public void checkgetAllProd(){
        List<Produs> produses=new ArrayList<>();
        produses.add(new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5"));
        produses.add(new Produs("12345","albastru","44","200",10,"incaltaminte","barbati","2.5"));
        produses.add(new Produs("12346","negru","45","358",10,"incaltaminte","fete","2.5"));
        produses.add(new Produs("12347","rosu","46","259",10,"incaltaminte","baieti","2.5"));
        when(produsDAOMock.findAllProducts()).thenReturn(produses);
        assertEquals(angajatBLL.getAllProd(),"1234 alb 43 256 10 incaltaminte femei,12345 albastru 44 200 10 incaltaminte barbati,12346 negru 45 358 " +
                "10 incaltaminte fete,12347 rosu 46 259 10 incaltaminte baieti");
    }

    @Test
    public void checkgetSpecifiedProd(){
        Produs produs=new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("1234")).thenReturn(produs);
        String[] req=new String[10];
        req[0]="produsReview";
        req[1]=produs.getIdProdus();
        assertEquals(angajatBLL.getSpecifiedProd(req),"1234 femei alb 43 256 2.5 incaltaminte,");
    }

    @Test
    public void doubleCheckStock(){
        String[] req=new String[10];
        req[0]="checkStoc";
        Produs produs=new Produs("1234","alb","43","256",10,"incaltaminte","femei","2.5");
        when(produsDAOMock.getProdus("1234")).thenReturn(produs);
        req[1]="1234";
        assertEquals(angajatBLL.checkStock(req),"da");
    }

    @Test
    public void checkgetReviewList(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        Client client=new Client("1234",user);
        Produs produs= new Produs("12345","alb","43","256",10,"incaltaminte","femei","2.5");
        Produs produs1= new Produs("54321","alb","43","256",10,"incaltaminte","femei","2.5");
        Produs produs3= new Produs("54321","alb","43","256",10,"incaltaminte","femei","2.5");
        List<Review> list=new ArrayList<>();
        list.add(new Review("12345","foarte bun","4",client,produs));
        list.add(new Review("54321","foarte bun","5",client,produs1));
        list.add(new Review("98765","foarte bun","5",client,produs3));


        when(reviewDAOMock.findAllReviews()).thenReturn(list);
        assertEquals(angajatBLL.getReviewList(),"12345 12345 4 1234 foarte bun,54321 54321 5 1234 foarte bun,98765 54321 5 1234 foarte bun");
    }

}
