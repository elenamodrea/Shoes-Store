package GUI;

import Ajutor.TableHelper;
import Business.AngajatBLL;
import ClientServer.Client;
import DAO.ProdusDAO;
import DAO.ReviewDAO;
import Tables.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AngajatRating extends JFrame implements ActionListener {
    private JPanel reviewAngajat;
    private JButton backButton;
    private JButton addRatingsButton;
    private JButton refreshButton;
    private JButton refreshProducts;
    private JTable tableReview;
    private JTable produsTable;
    private Socket socket;
    private Client client;
    private List<Review> reviewList;
    private int i;
    private ReviewDAO reviewDAO;
    private AngajatBLL angajatBLL;
    private HashMap<String, List<String>> ratings;
    private ProdusDAO produsDAO;
    private TableHelper tableHelper;
    public AngajatRating(Socket socket){
        this.socket=socket;
        setTitle("Rating");
        setContentPane(reviewAngajat);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.backButton.addActionListener(this);
        this.addRatingsButton.addActionListener(this);
        this.refreshButton.addActionListener(this);
        this.refreshProducts.addActionListener(this);
        reviewDAO=new ReviewDAO();
        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        angajatBLL=new AngajatBLL();
        produsDAO=new ProdusDAO();
        tableHelper=new TableHelper();

    }

    public void updateRating(String[] reviews){
        ratings=new HashMap<>();
        for(String review:reviews){
            String[] rev1=review.split(" ");
            String idProdus=rev1[1];
            String rating=rev1[2];
            if(!ratings.containsKey(idProdus)){
                List<String> r=new ArrayList<>();
                r.add(rating);
                ratings.put(idProdus,r);
            }else{
                List<String> aux=ratings.get(idProdus);
                List<String> newAux = new ArrayList<>(aux);
                newAux.add(rating);
                ratings.put(idProdus,newAux);
            }
        }

    }


    private String[] sendHandleReq() {
        StringBuilder req=new StringBuilder("review,");
        try {
            client.ClientReq(req.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return client.getResponse();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==backButton){
            this.dispose();
            new Angajat(socket);
        }
        if(e.getSource()==refreshButton){
            String[] rev = sendHandleReq();
            updateRating(rev);
            String col="ID Review."+"ID Produs."+"Rating."+"ID Client."+"Mesaj";
            tableHelper.updateTableFromString(tableReview,rev,col);
        }
        if(e.getSource()==refreshProducts){
            StringBuilder req=new StringBuilder("produsReview,");
            String produsID=tableHelper.getAttr(tableReview)[1].trim();
            req.append(produsID);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String[] prod=client.getResponse();
            String col="ID Produs."+"Categorie."+"Culoare."+"Marime."+"Pret."+"Rating."+"Nume.";
            tableHelper.updateTableFromString(produsTable,prod,col);
        }
        if(e.getSource()==addRatingsButton){
            StringBuilder req1=new StringBuilder("updateRating,");
            String prodID=tableHelper.getAttr(produsTable)[0];
            req1.append(prodID).append(",");
            String newRating=calculateRating(ratings,prodID);
            req1.append(newRating);
            try {
                client.ClientReq(req1.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(reviewAngajat,"Pentru a vedea noul rating Apasa Refresh Products");
        }
    }

    public String calculateRating(HashMap<String,List<String>> ratings, String product){
        int cinci=0,patru=0,trei=0,doi=0,unu=0;
        if(ratings.containsKey(product)){
            List<String> aux=ratings.get(product);
            for(String rating:aux){
                if(rating.equals("5")){
                    cinci++;
                }
                if(rating.equals("4")){
                    patru++;
                }
                if(rating.equals("3")){
                    trei++;
                }
                if(rating.equals("2")){
                    doi++;
                }
                if(rating.equals("1")){
                    unu++;
                }
            }

        }else{
            System.out.println("Nu merge");
        }

        int formula=(5*cinci + 4*patru + 3*trei + 2*doi + unu) / (cinci+patru+trei+doi+unu);

        return String.valueOf(formula);
    }



}
