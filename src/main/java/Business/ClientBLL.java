package Business;

import Ajutor.CodeGenerator;
import Ajutor.WriteReadFile;
import DAO.*;
import Tables.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class ClientBLL {
    private UserDAO userDAO;
    private ClientDAO clientDAO;
    private ProdusDAO produsDAO;
    private RandomID randomID;
    private ComandaDAO comandaDAO;
    private WriteReadFile writeReadFile;
    private ReviewDAO reviewDAO;

    public ClientBLL() {
        userDAO = new UserDAO();
        clientDAO = new ClientDAO();
        randomID = new RandomID();
        produsDAO = new ProdusDAO();
        comandaDAO=new ComandaDAO();
        writeReadFile=new WriteReadFile();
        reviewDAO=new ReviewDAO();

    }
    public ClientBLL(UserDAO userDAO,ClientDAO clientDAO,ProdusDAO produsDAO,ComandaDAO comandaDAO,ReviewDAO reviewDAO) {
        this.userDAO = userDAO;
        this.clientDAO = clientDAO;
        randomID = new RandomID();
        this.produsDAO =produsDAO;
        this.comandaDAO=comandaDAO;
        writeReadFile=new WriteReadFile();
        this.reviewDAO= reviewDAO;

    }

    public String getProfile(String username) {
        StringBuilder str = new StringBuilder();
        User user = userDAO.getUserName(username);
        Client client=clientDAO.getClientByUser(user);
        List<Card> cardList=clientDAO.getCards(client);
        if(cardList.isEmpty()){
            str.append(user.getNume()).append(",").append(user.getPrenume()).append(",").append(user.getAdresa()).append(",");
            str.append(user.getMail()).append(",").append(user.getUsername()).append(",").append(user.getParola()).append(",");
        }else {
            Card card=clientDAO.getCards(client).get(clientDAO.getCards(client).size()-1);
            str.append(user.getNume()).append(",").append(user.getPrenume()).append(",").append(user.getAdresa()).append(",");
            str.append(user.getMail()).append(",").append(user.getUsername()).append(",").append(user.getParola()).append(",");
            str.append(card.getNrCard()).append(",").append(card.getCVV()).append(",").append(card.getDataExpirare());
        }

        return str.toString();
    }

    public void updateProfileAndCard(String username, String[] req) {
        User user = userDAO.getUserName(username);
        Client client = clientDAO.getClientByUser(user);
        user.setNume(req[3]);
        user.setPrenume(req[4]);
        user.setAdresa(req[5]);
        user.setMail(req[9]);
        user.setUsername(req[10]);
        user.setParola(req[11]);
        userDAO.updateUser(user);
        if(req[6]==null || req[7]==null || req[8]==null){
            JOptionPane.showMessageDialog(null,"Va rugam adaugati un card");
        }else{
            Card card1=new Card("1234",req[6],Integer.parseInt(req[7]),"",req[8]);
            List<Card> cards=clientDAO.getCards(client);
            int ok=0;
            for (Card c: cards) {
                if((c.getNrCard().equals(card1.getNrCard()))&&(c.getCVV()== card1.getCVV())&&(c.getDataExpirare()).equals(card1.getDataExpirare()))
                    ok=1;
            }
            if (ok==0) {
                Card card = new Card(randomID.getRandomID(), req[6], Integer.parseInt(req[7]), req[3] + " " + req[4], req[8]);
                card.setClient(client);
                clientDAO.updateClientOneCard(client.getIdClient(), card);
            }
        }


    }

    public String productsList(String[] req) {
        List<String> strings = new ArrayList<>();
        List<Produs> produsList = produsDAO.findAllProducts();
        if (req[2].equals("all items")) {
            for (Produs p : produsList) {
                StringBuilder str = new StringBuilder();
                str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                        .append(p.getPret()).append(" ").append(p.getMarime());
                strings.add(str.toString());
            }
        } else if (req[2].equals("Femei")) {
            for (Produs p : produsList) {
                if (p.getCategorie().equals("femei")) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings.add(str.toString());
                }
            }
        } else if (req[2].equals("Barbati")) {
            for (Produs p : produsList) {
                if (p.getCategorie().equals("barbati")) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings.add(str.toString());
                }
            }
        } else if (req[2].equals("Baieti")) {
            for (Produs p : produsList) {
                if (p.getCategorie().equals("baieti")) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings.add(str.toString());
                }
            }
        } else if (req[2].equals("Fete")) {
            for (Produs p : produsList) {
                if (p.getCategorie().equals("fete")) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings.add(str.toString());
                }
            }
        }
        else {
            for (Produs p : produsList) {
                if (p.getTipProdus().contains(req[2])) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings.add(str.toString());
                }
            }
        }
        StringBuilder res=new StringBuilder();
        for (String s: strings) {
            res.append(s).append(",");
        }
        if(!res.toString().equals(""))
          res.deleteCharAt(res.length()-1);
        return res.toString();
    }
    public String getEmail(String[] req){
        User user = userDAO.getUserName(req[2]);
        return user.getMail();
    }
    public String getComenzi(String[] req){
        List<String> strings = new ArrayList<>();
        User user = userDAO.getUserName(req[2]);
        Client client = clientDAO.getClientByUser(user);
        List<Comanda> comandList =clientDAO.getComands(client);
        for (Comanda c:comandList) {
            StringBuilder str=new StringBuilder();
            str.append(c.getIdComanda()).append(" ").append(c.getData()).append(" ").append(c.getPretComanda());
            strings.add(str.toString());
        }
        StringBuilder res=new StringBuilder();
        for (String s: strings) {
            res.append(s).append(",");
        }
        if(!res.toString().equals(""))
            res.deleteCharAt(res.length()-1);
        return res.toString();
    }
    public String getProduseComanda(String[] req){
        List<String> strings = new ArrayList<>();
        Comanda comanda = comandaDAO.getComanda(req[3]);
        List<Produs> produsList=comandaDAO.getProduses(comanda);
        for(Produs p:produsList){
            StringBuilder str = new StringBuilder();
            str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                    .append(p.getPret()).append(" ").append(p.getMarime());
            strings.add(str.toString());
        }
        StringBuilder res=new StringBuilder();
        for (String s: strings) {
            res.append(s).append(",");
        }
        if(!res.toString().equals(""))
            res.deleteCharAt(res.length()-1);
        return res.toString();
    }
    public void trimiteRetur(String[] req) throws IOException {
        writeReadFile.writeFile(req[2],"notificariRetur.txt");
    }
    public String getInformatiiProdus(String[] req){
        String idProdus=req[2];
        Produs produs=produsDAO.getProdus(idProdus);
        StringBuilder res=new StringBuilder().append(produs.getTipProdus()).append(",").append(produs.getPret())
                .append(",").append(produs.getCategorie()).append(",").append(produs.getMarime()).append(",").append(produs.getStoc())
                .append(",").append(produs.getCuloare()).append(",").append(produs.getRating()).append(",");
        String pozaFisier=writeReadFile.readFile(idProdus,"pantofi.txt");
        String[] calePoza=pozaFisier.split(" ");;
        res.append(calePoza[0]);
        return res.toString();
        }
        public String adaugaReview(String[] req){
        User user=userDAO.getUserName(req[2]);
        Client client=clientDAO.getClientByUser(user);
        Produs produs=produsDAO.getProdus(req[3]);
        List<Review> reviews= produsDAO.getReviews(produs);
            for (Review r: reviews) {
                if(reviewDAO.getClient(r).getIdClient().equals(client.getIdClient()))
                {return "review deja existent";}
            }
        Review review=new Review(randomID.getRandomID(),req[4],req[5]);
        review.setClient(client);
        review.setProdus(produs);
        clientDAO.updateClientOneReview(client.getIdClient(), review);
        produsDAO.updateProdusOneReview(produs.getIdProdus(),review);
        return "review adaugat";
        }
        public String getReviews(String[] req){
        Produs produs=produsDAO.getProdus(req[3]);
        List<Review> reviews=produsDAO.getReviews(produs);
        List<String> strings=new ArrayList<>();
        for (Review r: reviews){
            StringBuilder str=new StringBuilder();
            str.append(r.getMesaj()).append(" ").append(r.getRating());
            strings.add(str.toString());
        }
            StringBuilder res=new StringBuilder();
            for (String s: strings) {
                res.append(s).append(",");
            }
            if(!res.toString().equals(""))
                res.deleteCharAt(res.length()-1);
            return res.toString();
        }
        public String getProdusePentruComanda(String[] req){
        List<String> strings=new ArrayList<>();
        for(int i=2;i< req.length;i++){
            Produs produs= produsDAO.getProdus(req[i]);
            StringBuilder res=new StringBuilder().append(produs.getIdProdus()).append(" ").
                    append(produs.getTipProdus()).append(" ").append(produs.getPret())
                    .append(" ").append(produs.getCategorie()).append(" ").append(produs.getMarime()).append(" ").append(produs.getStoc())
                    .append(" ").append(produs.getCuloare()).append(" ");
            strings.add(res.toString());
        }
            StringBuilder res=new StringBuilder();
            for (String s: strings) {
                res.append(s).append(",");
            }
            if(!res.toString().equals(""))
                res.deleteCharAt(res.length()-1);
            return res.toString();
        }
    public void trimiteComanda(String[] req) throws IOException {
        String[] ids=req[2].split(" ");
        List<Produs> produses=new ArrayList<>();
        Double pretTotal=0.0;
        for(int i=1;i<ids.length;i++){
            Produs produs=produsDAO.getProdus(ids[i]);
            pretTotal+=Double.parseDouble(produs.getPret());
            produses.add(produs);
        }
        String date=java.time.LocalDate.now().toString();
        User user=userDAO.getUserName(ids[0]);
        Client client=clientDAO.getClientByUser(user);
        List<Card> cards=clientDAO.getCards(client);
        if(cards.isEmpty()){
            JOptionPane.showMessageDialog(null,"Va rugam adaugati un card");
        }else{
            Card card=cards.get(cards.size()-1);
            //Card card=new Card();
            Comanda comanda=new Comanda(randomID.getRandomID(),date,"card",pretTotal.toString(), card.getNrCard());
            comanda.setClient(client);
            comanda.setProduse(produses);
            for(int i=1;i<ids.length;i++) {
                produsDAO.updateComandaOComanda(ids[i],comanda);
            }
            clientDAO.updateClientOComanda(client.getIdClient(),comanda);
            String[] forUsername=req[2].split(" ");
            StringBuilder str=new StringBuilder(forUsername[0]);
            str.append(" ").append(comanda.getIdComanda());
            writeReadFile.writeFile(str.toString(),"comandaTrimisa.txt");
        }

    }

    public String trimiteFavorit(String[] req) throws IOException {
        String[] r=req[2].split(" ");
        Produs produs=produsDAO.getProdus(r[1]);
        if(produs.getStoc()==0){
        writeReadFile.writeFile(req[2],"produseFavorite.txt");
        return "favorit trimis";}
        else return "deja in stoc";
    }

    }

