package Controller;

import Business.ProductBLL;
import Tables.Angajat;
import Tables.Produs;

import java.io.IOException;
import java.util.List;

public class ControllerProduct {
    private ProductBLL productBLL;
    private Produs produs;
    private List<Produs> produsList;
    public ControllerProduct(){
        this.productBLL = new ProductBLL();
    }

    public Produs createProduct(String[] string){
        produs = productBLL.createProduct(string);
        return produs;
    }
    public void handleReq(String[] string){
        productBLL.handleReq(string);
    }

    public List<Produs> generateProdus(){
        produsList = productBLL.createTableObjects();
        for(Produs p: produsList){
            System.out.println(p.getIdProdus());
        }
        return produsList;
    }

    public List<Produs> generateProdusNotification(){
        produsList = productBLL.createTableNotifications();
        for(Produs p: produsList){
            System.out.println(p.getIdProdus());
        }
        return produsList;
    }
    public String handleReq(List<Produs> produsList){
        StringBuilder string = new StringBuilder();
        for (Produs p: produsList) {
            string.append(p.getIdProdus()).append(",");
            System.out.println("req " + p.getIdProdus());
        }
        return string.toString();
    }
    public Produs modifyProduct(String[] string){
        return productBLL.modifyProduct(string);
    }

    public void deleteProduct(String id){
        productBLL.deleteProduct(id);
    }

    public void restocProduct(String[] strings){
        productBLL.restocProduct(strings);
    }

    public void restocWrite(String[] strings) throws IOException {
        productBLL.restocWrite(strings);
    }

    public void handleNotifications(String[] strings) throws IOException {
        productBLL.handleNotifications(strings);
    }

    public static void main(String[] args) {
        ControllerProduct controllerProduct = new ControllerProduct();
        controllerProduct.generateProdus();
    }
}
