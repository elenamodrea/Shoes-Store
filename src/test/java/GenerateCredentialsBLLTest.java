import Business.GenerateCredentialsBLL;
import DAO.*;
import Tables.Angajat;
import Tables.User;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerateCredentialsBLLTest {
    private GenerateCredentialsBLL generateCredentialsBLL;
    private UserDAO userDAOMock;
    private AngajatDAO angajatDAOMock;

    @Before
    public void setup(){
        userDAOMock = mock(UserDAO.class);
        angajatDAOMock= mock(AngajatDAO.class);
        generateCredentialsBLL = new GenerateCredentialsBLL(userDAOMock, angajatDAOMock);
    }


    @Test
    public void convertMail(){
        User user = new User("Mocjqfz3eSxZLtNE","Ghimbir","Gloria","123546","Sibiu","kjvbfdc@yahoo.com","angajat","gloria123","1234");
        Angajat angajat = new Angajat("JfVnRtknsy7NGmbD",4700,"4 ani","29/12/2022","09/01/2022",user);
        when(angajatDAOMock.getAngajat("JfVnRtknsy7NGmbD")).thenReturn(angajat);
        assertEquals(generateCredentialsBLL.convertMail("JfVnRtknsy7NGmbD"), "kjvbfdc@yahoo.com");
    }

    @Test
    public void checkAngajatContractBefore() {
        User user = new User("Mocjqfz3eSxZLtNE", "Ghimbir", "Gloria", "123546", "Sibiu", "kjvbfdc@yahoo.com", "angajat", "gloria123", "1234");
        Angajat angajat = new Angajat("JfVnRtknsy7NGmbD", 4700, "4 ani", "29/12/2022", "09/01/2022", user);
        when(angajatDAOMock.getAngajat("JfVnRtknsy7NGmbD")).thenReturn(angajat);
        assertEquals(generateCredentialsBLL.checkAngajatContractBefore("JfVnRtknsy7NGmbD"), false);
    }

    @Test
    public void createTableObjects(){
        User user1 = new User("Mocjqfz3eSxZLtNE", "Ghimbir", "Gloria", "123546", "Sibiu", "kjvbfdc@yahoo.com", "angajat", "gloria123", "1234");
        Angajat angajat1 = new Angajat("JfVnRtknsy7NGmbD", 4700, "4 ani", "29/12/2022", "09/01/2022", user1);
        User user2 = new User("Vd4Zrmb5Unhxt1Th","Radu","Ciprian","123546","Cluj-Napoca","avjv@yahoo.com","angajat","radu1123","1234");
        Angajat angajat2 = new Angajat("S7LCGIvx9NALeqQF",2700,"2 ani","17/12/2022","17/12/2021",user2);
        List<Angajat> angajat = new ArrayList<>();
        angajat.add(angajat1);
        angajat.add(angajat2);
        when(angajatDAOMock.findAllAngajat()).thenReturn(angajat);
        //assertEquals(generateCredentialsBLL.createTableObjects(), angajat);
    }
}
