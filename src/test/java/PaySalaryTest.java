import Business.PaySalaryBLL;
import DAO.AngajatDAO;
import Tables.Angajat;
import Tables.User;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaySalaryTest {

    private PaySalaryBLL paySalaryBLL;
    private AngajatDAO angajatDAOMock;

    @Before
    public void setup(){
        angajatDAOMock= mock(AngajatDAO.class);
        paySalaryBLL = new PaySalaryBLL(angajatDAOMock);
    }

    @Test
    public void generateAngajats() {
        User user1 = new User("Mocjqfz3eSxZLtNE", "Ghimbir", "Gloria", "123546", "Sibiu", "kjvbfdc@yahoo.com", "angajat", "gloria123", "1234");
        Angajat angajat1 = new Angajat("JfVnRtknsy7NGmbD", 4700, "4 ani", "29/12/2022", "09/01/2022", user1);
        User user2 = new User("Vd4Zrmb5Unhxt1Th", "Radu", "Ciprian", "123546", "Cluj-Napoca", "avjv@yahoo.com", "angajat", "radu1123", "1234");
        Angajat angajat2 = new Angajat("S7LCGIvx9NALeqQF", 2700, "2 ani", "17/12/2022", "17/12/2021", user2);
        List<Angajat> angajat = new ArrayList<>();
        angajat.add(angajat1);
        angajat.add(angajat2);
        when(angajatDAOMock.findAllAngajat()).thenReturn(angajat);
        assertEquals(paySalaryBLL.generateAngajats(), angajat);
    }

    @Test
    public void checkAngajatContractBefore() {
        User user = new User("Mocjqfz3eSxZLtNE", "Ghimbir", "Gloria", "123546", "Sibiu", "kjvbfdc@yahoo.com", "angajat", "gloria123", "1234");
        Angajat angajat = new Angajat("JfVnRtknsy7NGmbD", 4700, "4 ani", "29/12/2022", "09/01/2022", user);
        when(angajatDAOMock.getAngajat("JfVnRtknsy7NGmbD")).thenReturn(angajat);
        assertEquals(paySalaryBLL.checkAngajatContractBefore("JfVnRtknsy7NGmbD"), false);
    }
}
