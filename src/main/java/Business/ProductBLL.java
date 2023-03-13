package Business;
import Ajutor.WriteReadFile;
import DAO.ProdusDAO;
import Tables.Produs;
import Tables.RandomID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductBLL {
    private RandomID randomID;
    private Produs produs;
    private ProdusDAO produsDAO;
    private WriteReadFile writeReadFile;
    public ProductBLL() {
        this.randomID = new RandomID();
        this.produsDAO = new ProdusDAO();
        this.writeReadFile = new WriteReadFile();
    }

    public ProductBLL(ProdusDAO produsDAO) {
        this.randomID = new RandomID();
        this.produsDAO = produsDAO;
        this.writeReadFile = new WriteReadFile();
    }

    public Produs createProduct(String[] string){
        produs = new Produs(randomID.getRandomID(),string[2], string[3], string[4], Integer.parseInt(string[5]), string[6], string[7],"0");
        produsDAO.createProdus(produs);
        return produs;
    }

    public Produs modifyProduct(String[] string){
        produs = produsDAO.getProdus(string[1]);
        produs.setCuloare(string[2]);
        produs.setMarime(string[3]);
        produs.setPret(string[4]);
        produs.setStoc(Integer.parseInt(string[5]));
        produs.setTipProdus(string[6]);
        produs.setCategorie(string[7]);
        produsDAO.updateProdus(produs);
        return produs;
    }

    public void restocProduct(String[] string){
        produs = produsDAO.getProdus(string[1]);
        produs.setStoc(Integer.parseInt(string[2]));
        produsDAO.updateProdus(produs);
    }

    public void deleteProduct(String id){
        produsDAO.deleteProdus(id);
    }

    public void handleReq(String[] string){
        StringBuilder stringBuilder1 = new StringBuilder(string[0] + " " + string[1]);
        try {
            writeReadFile.writeFile(String.valueOf(stringBuilder1), "pantofi.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void restocWrite(String[] strings) throws IOException {
        String message = strings[1] + " " + strings[2];
        writeReadFile.writeFile(message, "notificareRestocare.txt");
    }

    public List<Produs> createTableObjects(){
        List<Produs> produsList = produsDAO.findAllProducts();
        return produsList;
    }

    public List<Produs> createTableNotifications(){
        produsDAO = new ProdusDAO();
        List<Produs> produsList = new ArrayList<>();
        List<String> strings = writeReadFile.readFileNotifications("notificareStocGol.txt");
        for (String s: strings){
            System.out.println(s);
            ProdusDAO produsDAO = new ProdusDAO();
            String id = s;
            Produs p = produsDAO.getProdus(id);
            System.out.println(p.getIdProdus());
            produsList.add(p);
        }
        return produsList;
    }

    public void handleNotifications(String[] req) throws IOException {
        writeReadFile.deleteReader(req[1], "notificareStocGol.txt");
    }

    public static void main(String[] args) {
        ProductBLL productBLL = new ProductBLL();
        productBLL.createTableNotifications();
    }
}
