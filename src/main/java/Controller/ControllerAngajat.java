package Controller;

import Ajutor.WriteReadFile;
import Business.AngajatBLL;
import Tables.Produs;
import Tables.Review;
import net.bytebuddy.utility.JavaType;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class ControllerAngajat {
    private AngajatBLL angajatBLL;
    private List<Review> reviewList;
    private WriteReadFile writeReadFile;

    public ControllerAngajat(){
        angajatBLL=new AngajatBLL();
        writeReadFile=new WriteReadFile();
    }

    public String handleAngajatReq(String[] req) throws IOException {
        if(req[1].equals("allProd")){
            return angajatBLL.getAllProd();
        }else if(req[1].equals("writeInFile")){
            angajatBLL.writeInSpecFile(req);
            return "Notificare Restocare";
        }else if(req[1].equals("adaugaRetur")){
            angajatBLL.addReturtoDb(req);
            return "retur adaugat";
        }
        return "nu merge";
    }

    public void sendRetur(String[] req){
        angajatBLL.sendEmailRetur(req);
    }

    public String getReviews(){
       return angajatBLL.getReviewList();
    }

    public String getSpecProd(String[] req){
        return angajatBLL.getSpecifiedProd(req);
    }

    public void updateRatingProd(String[] req){
        angajatBLL.updateRating(req);
    }

    public String checkProd(String[] req){
        return angajatBLL.checkStock(req);
    }

    public String checkComand(String[] req){
        return angajatBLL.checkProductsFromComand(req);
    }

    public String checkDataComanda(String[] req) {
        return angajatBLL.checkDataComanda(req);
    }

    public String getAllProd(){return angajatBLL.getAllProd();}

}
