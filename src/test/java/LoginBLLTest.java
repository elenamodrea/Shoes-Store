import Business.LoginBLL;
import DAO.UserDAO;
import Tables.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginBLLTest {
private LoginBLL loginBLL;
private UserDAO userDAOMock;

@Before
    public void setup(){
     userDAOMock=mock(UserDAO.class);
     loginBLL=new LoginBLL(userDAOMock);

}

@Test
    public void checkLoginBssClient(){
    User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
    when(userDAOMock.getUserName("alinion")).thenReturn(user);
    //if()
    assertEquals(loginBLL.LoginBss("alinion","1234"),"client");
}
@Test
    public void checkLoginBssParolaGresita(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        //if()
        assertEquals(loginBLL.LoginBss("alinion","12345"),"parola gresita");
    }
    @Test
    public void checkLoginBssAngajat(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","angajat","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        //if()
        assertEquals(loginBLL.LoginBss("alinion","1234"),"angajat");
    }
    @Test
    public void checkLoginBssAdmin(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","admin","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        //if()
        assertEquals(loginBLL.LoginBss("alinion","1234"),"admin");
    }

    @Test
    public void checkLoginBssUserNegasit(){
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","angajat","alinion","1234");
        when(userDAOMock.getUserName("alinion")).thenReturn(user);
        //if()
        assertEquals(loginBLL.LoginBss("alinion2","1234"),"Userul nu a fost gasit");
    }

}
