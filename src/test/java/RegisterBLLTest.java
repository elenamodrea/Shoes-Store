import Business.LoginBLL;
import Business.RegisterBLL;
import DAO.UserDAO;
import Tables.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterBLLTest {
    private RegisterBLL registerBLL;
    private UserDAO userDAOMock;
    @Before
    public void setup(){
        userDAOMock=mock(UserDAO.class);
        registerBLL=new RegisterBLL(userDAOMock);
    }

    @Test
    public void checkHandlerRegisterMail(){
        List<User> users=new ArrayList<>();
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        users.add(user);
        when(userDAOMock.findAllUser()).thenReturn(users);
        String[] strings=new String[8];
        strings[0]="ala";
        strings[1]="Ion";
        strings[2]="alin";
        strings[3]="0123456789";
        strings[4]="Alba-Iulia";
        strings[5]="ionalin@yahoo.com";
        strings[6]="alinion";
        strings[7]="1234";

        assertEquals(registerBLL.HandlerRegister(strings),"Mailul a fost deja folosit.");
    }
    @Test
    public void checkHandlerRegisterUsername(){
        List<User> users=new ArrayList<>();
        User user=new User("1234","Ion","Alin","0765241048","Cluj-Napoca","ionalin@yahoo.com","client","alinion","1234");
        users.add(user);
        when(userDAOMock.findAllUser()).thenReturn(users);
        String[] strings=new String[8];
        strings[0]="ala";
        strings[1]="Ion";
        strings[2]="alin";
        strings[3]="0123456789";
        strings[4]="Alba-Iulia";
        strings[5]="ionalin1@yahoo.com";
        strings[6]="alinion";
        strings[7]="1234";

        assertEquals(registerBLL.HandlerRegister(strings),"Username-ul a fost deja folosit.");
    }

}
