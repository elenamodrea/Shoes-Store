package GUI;

import Ajutor.ConfirmEmail;
import Ajutor.TableHelper;
import Ajutor.WriteReadFile;
import ClientServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class AngajatRetur extends JFrame implements ActionListener {
    private JPanel notificareAngajat;
    private JButton sendButton;
    private JButton backButton;
    private JTable table1;
    private JButton verificareDataButton;
    private JButton loadButton;
    private JButton adaugaInDBButton;
    private Socket socket;
    private WriteReadFile writeReadFile;
    private Client client;
    private int ok=0;
    private ConfirmEmail confirmEmail;
    private String respone="";
    private TableHelper tableHelper;

    public AngajatRetur(Socket socket) {
        this.socket=socket;
        setTitle("Retur");
        setContentPane(notificareAngajat);
        this.writeReadFile=new WriteReadFile();
        confirmEmail=new ConfirmEmail();
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tableHelper=new TableHelper();
        this.backButton.addActionListener(this);
        this.sendButton.addActionListener(this);
        this.verificareDataButton.addActionListener(this);
        this.loadButton.addActionListener(this);
        this.adaugaInDBButton.addActionListener(this);
        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.adaugaInDBButton.setEnabled(false);
        this.verificareDataButton.setEnabled(false);
        this.sendButton.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder req=new StringBuilder("email,");

        if(e.getSource()==loadButton){
            this.verificareDataButton.setEnabled(true);
            String column="User Name.ID Comanda.Data";
            tableHelper.updateTableFromFile(table1,"notificariRetur.txt",column);
        }
        if(e.getSource()==verificareDataButton){
            this.adaugaInDBButton.setEnabled(true);
            StringBuilder req1=new StringBuilder("dataComanda,");
            String idCom=(tableHelper.getAttr(table1))[1];
            String dataRetur=tableHelper.getAttr(table1)[2];
            req1.append(idCom).append(",").append(dataRetur);
            try {
                client.ClientReq(req1.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
           String response=client.getResponse()[0];
            if(response.equals("da")){
                JOptionPane.showMessageDialog(notificareAngajat,"Se poate face Retur!");
                respone="Se poate face returul.,";
            }else if(response.equals("nu")){
                JOptionPane.showMessageDialog(notificareAngajat,"Nu se poate face Retur!");
                respone="Nu se poate face returul.,";
                ok=0;
            }
        }
        if(e.getSource()==sendButton){
            req.append(respone);
            req.append(tableHelper.getAttr(table1)[0]);
                System.out.println(req);
                try {
                    client.ClientReq(req.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(notificareAngajat,"Email Trimis");

        }
        if(e.getSource()==backButton){
            this.dispose();
            new Angajat(socket);
        }
        if(e.getSource()==adaugaInDBButton){
            this.sendButton.setEnabled(true);
            StringBuilder req2=new StringBuilder("angajat,");
            req2.append("adaugaRetur,");
            String username=tableHelper.getAttr(table1)[0];
            String idComanda=tableHelper.getAttr(table1)[1];
            String data= tableHelper.getAttr(table1)[2];
            req2.append(idComanda).append(",").append(data).append(",").append(username);
            try {
                client.ClientReq(req2.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
