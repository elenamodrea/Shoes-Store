import Business.ProductBLL;
import DAO.ProdusDAO;
import Tables.Produs;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductBLLTest {
    private ProductBLL productBLL;
    private ProdusDAO produsDAOMock;

    @Before
    public void setup(){
        produsDAOMock = mock(ProdusDAO.class);
        productBLL = new ProductBLL(produsDAOMock);
    }

    @Test
    public void createTableObjects(){
        Produs produs1 = new Produs("0pSf6Zhq6c0x5QcO","gri","43","789.5",256,"barbati","adidasi","0");
        Produs produs2 = new Produs("R8kRF3lgpY9fK8k2","roz","38","345",5,"femei","adidasi","0");
        List<Produs> produsList = new ArrayList<>();
        produsList.add(produs1);
        produsList.add(produs2);
        when(produsDAOMock.findAllProducts()).thenReturn(produsList);
        assertEquals(productBLL.createTableObjects(), produsList);
    }
}
