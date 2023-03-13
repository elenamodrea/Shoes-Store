package Business;

import Ajutor.ConfirmEmail;
import Ajutor.TableHelper;
import Ajutor.WriteReadFile;
import DAO.*;
import Tables.*;
import Validatoare.Validatoare;

import javax.swing.*;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AngajatBLL {

    private UserDAO userDAO;
    private ConfirmEmail email;
    private ReviewDAO reviewDAO;
    private List<Review> reviewList;
    private ProdusDAO produsDAO;
    private WriteReadFile writeReadFile;
    private ComandaDAO comandaDAO;
    private Validatoare validatoare;
    private RandomID randomID;
    private ClientDAO clientDAO;
    private ReturDAO returDAO;

    public AngajatBLL() {
        this.userDAO = new UserDAO();
        this.email=new ConfirmEmail();
        this.reviewDAO=new ReviewDAO();
        this.reviewList=new ArrayList<>();
        this.produsDAO=new ProdusDAO();
        this.writeReadFile=new WriteReadFile();
        this.comandaDAO = new ComandaDAO();
        this.validatoare=new Validatoare();
        this.randomID=new RandomID();
        this.clientDAO=new ClientDAO();
        this.returDAO=new ReturDAO();

    }

    public AngajatBLL(UserDAO userDAO,ReviewDAO reviewDAO,ComandaDAO comandaDAO,ProdusDAO produsDAO) {
        this.userDAO = userDAO;
        this.reviewDAO=reviewDAO;
        this.comandaDAO=comandaDAO;
        this.produsDAO=produsDAO;
    }

    public void sendEmailRetur(String[] req){
       String customerE= userDAO.getEmail(req[2]);
//       User user=userDAO.getUserName(req[2]);
//        System.out.println(user.getMail());
       email.sendMail(customerE,customerE,req[1]);
    }


    public String getReviewList(){
        List<String> list=new ArrayList<>();
        List<Review> reviewLis= reviewDAO.findAllReviews();
        for(Review rew:reviewLis){
            Produs prod=rew.getProdus();
            Client client=rew.getClient();
            String rev = rew.getIdReview() + " " + prod.getIdProdus() + " " +
                    rew.getRating() + " " + client.getIdClient() + " " + rew.getMesaj();
            list.add(rev);
        }
        StringBuilder res=new StringBuilder();
        for (String s: list) {
            res.append(s).append(",");
        }
        if(!res.toString().equals(""))
            res.deleteCharAt(res.length()-1);
        return res.toString();
    }


    public String getSpecifiedProd(String[] req){
       Produs produs=produsDAO.getProdus(req[1]);
       StringBuilder prod=new StringBuilder();
       prod.append(produs.getIdProdus()).append(" ").append(produs.getCategorie()).append(" ").append(produs.getCuloare()).append(" ").append(produs.getMarime())
               .append(" ").append(produs.getPret()).append(" ").append(produs.getRating()).append(" ").append(produs.getTipProdus()).append(",");

       return prod.toString();
    }

    public void updateRating(String[] req){
        produsDAO.updateRating(req[1],req[2]);
    }

    public String checkStock(String[] req){
       // System.out.println(req[1]);
        Produs prod=produsDAO.getProdus(req[1]);
        //System.out.println(req[1]);
        String response="";
        if(prod.getStoc()==0){
            response="nu";
            return response;
        }else if(prod.getStoc() > 0){
            response="da";
            return response;
        }
        return response;
    }

    public String checkProductsFromComand(String[] req){
        StringBuilder stoc=new StringBuilder();
        int nr=0;
        Comanda comanda=comandaDAO.getComanda(req[1]);
        List<Produs> produsList=comandaDAO.getProduses(comanda);
        for(Produs produs:produsList){
            if(produs.getStoc()>0){
                nr++;
                produsDAO.updateStoc(produs.getIdProdus());
            }else{
                stoc.append(produs.getTipProdus()).append(" ");
            }
        }
        if(nr==produsList.size()){
            return "da";
        }else {
            return "nu";
        }
    }

    public String checkDataComanda(String[] req){
        String dataComanda=comandaDAO.getDataComanda(req[1]);
        String dataRetur=req[2];
        if(verifyData(dataComanda,dataRetur)){
            return "da";
        }else if(!verifyData(dataComanda,dataRetur)){
            return "nu";
        }
        return "nu merge";
    }

    public String getAllProd(){
        List<String> prodString=new ArrayList<>();
        List<Produs> produsLlist=produsDAO.findAllProducts();
        for(Produs prod:produsLlist){
            StringBuilder s=new StringBuilder();
            s.append(prod.getIdProdus()).append(" ").append(prod.getCuloare()).append(" ").append(prod.getMarime())
                    .append(" ").append(prod.getPret()).append(" ").append(prod.getStoc()).append(" ").append(prod.getTipProdus()).append(" ").append(prod.getCategorie());
            prodString.add(s.toString());
        }
        StringBuilder res=new StringBuilder();
        for (String s: prodString) {
            res.append(s).append(",");
        }
        if(!res.toString().equals(""))
            res.deleteCharAt(res.length()-1);
        return res.toString();
    }

    public void writeInSpecFile(String[] req) throws IOException {
        writeReadFile.writeFile(req[3],req[2]);
    }

    public boolean verifyData(String dataComanda,String dataRetur){
        String format="yyy-MM-dd";
        SimpleDateFormat formatter= new SimpleDateFormat(format);
        long days = 0;
        try{
            Date d1 = formatter.parse(dataRetur);
            Date d2 = formatter.parse(dataComanda.trim());
            //System.out.println(dataRetur+" "+dataComanda);

            long diff = 0;//time2 - time1;
            if (d1.before(d2)) {
                 diff = d2.getTime() - d1.getTime();

            } else if (d2.before(d1)) {
                 diff = d1.getTime() - d2.getTime();

            }

            days= diff / (1000 * 60 * 60 * 24);  // 1 day = 1000 milliseconds * 60 seconds * 60 minutes * 24 hours

        }catch (ParseException e){
            e.printStackTrace();
        }

        return Math.abs(days) <= 30;
    }

    public void addReturtoDb(String[] req){
        String idComanda=req[2];
        String dataRetur=req[3];
        String username=req[4];
        User user=userDAO.getUserName(username);
        Client client=clientDAO.getClientByUser(user);
        Comanda comanda=comandaDAO.getComanda(idComanda);
        Retur retur=new Retur(randomID.getRandomID(),dataRetur,comanda,client);
        retur.setComanda(comanda);
        retur.setClient(client);
        clientDAO.updateClientUnRetur(client.getIdClient(),retur);
        //comandaDAO.updateComanda(comanda);

    }





}
