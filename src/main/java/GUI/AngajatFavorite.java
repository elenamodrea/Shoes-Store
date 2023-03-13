package GUI;

import Ajutor.TableHelper;
import Ajutor.WriteReadFile;
import Business.AngajatBLL;
import ClientServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class AngajatFavorite extends JFrame implements ActionListener {
    private JPanel favoriteAngajat;
    private JTable favoriteTable;
    private JButton sendEmailButton;
    private JButton backButton;
    private JButton checkButton;
    private JButton refreshButton;
    private Socket socket;
    private AngajatBLL angajatBLL;
    private WriteReadFile writeReadFile;
    private Client client;
    private boolean checkPressed=false;
    private String respone="";
    private TableHelper tableHelper;
    public AngajatFavorite(Socket socket){
        this.socket=socket;
        setTitle("Anunta Produs Favorit");
        setContentPane(favoriteAngajat);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.tableHelper=new TableHelper();
        this.backButton.addActionListener(this);
        this.checkButton.addActionListener(this);
        this.refreshButton.addActionListener(this);
        this.sendEmailButton.addActionListener(this);
        angajatBLL=new AngajatBLL();
        writeReadFile=new WriteReadFile();
        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.checkButton.setEnabled(false);
        this.sendEmailButton.setEnabled(false);

    }

    public Boolean checkIfInStock(){
        StringBuilder req=new StringBuilder("checkStoc,");
        String prodID=tableHelper.getAttr(favoriteTable)[1];
        req.append(prodID);
        try {
            client.ClientReq(req.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response=client.getResponse()[0];
        if(response.equals("nu")){
            return false;

        }else{
            return true;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder req=new StringBuilder("email,");
        if(e.getSource()==backButton){
            this.dispose();
            new Angajat(socket);
        }
        if(e.getSource()==refreshButton){
            this.checkButton.setEnabled(true);
            String colum="User name."+"ID Produs";
            tableHelper.updateTableFromFile(favoriteTable,"produseFavorite.txt",colum);
        }
        if(e.getSource()==checkButton){
            this.sendEmailButton.setEnabled(true);
            if(!checkIfInStock()){
                JOptionPane.showMessageDialog(favoriteAngajat,"Nu este in stoc");
            }else{
                JOptionPane.showMessageDialog(favoriteAngajat,"Este in stoc");
                respone="ok";
            }
        }
        if(e.getSource()==sendEmailButton){
            String username=tableHelper.getAttr(favoriteTable)[0];
            if(Objects.equals(respone, "ok")){
                req.append("Produsul de care sunteti interesat/a este disponibil.,");
            }
            req.append(username);
            System.out.println(req);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
