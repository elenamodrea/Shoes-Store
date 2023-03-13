package GUI;

import Ajutor.TableHelper;
import ClientServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class AngajatConfirma extends JFrame implements ActionListener {
    private JPanel confirmaAngajat;
    private JTable comandTable;
    private JButton sendButton;
    private JButton backButton;
    private JButton checkButton;
    private JButton refreshButton;
    private Socket socket;
    private TableHelper tableHelper;
    private Client client;
    private StringBuilder req;

    public AngajatConfirma(Socket socket){
        this.socket=socket;
        setTitle("Confirma Comanda");
        setContentPane(confirmaAngajat);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tableHelper=new TableHelper();
        this.backButton.addActionListener(this);
        this.checkButton.addActionListener(this);
        this.refreshButton.addActionListener(this);
        this.sendButton.addActionListener(this);
        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.checkButton.setEnabled(false);
        this.sendButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] comanda=tableHelper.getAttr(comandTable);
        if(e.getSource()==backButton){
            this.dispose();
            new Angajat(socket);
        }
        if(e.getSource()==refreshButton){
            this.checkButton.setEnabled(true);
            String columnName="User Name."+"ID Comanda.";
            tableHelper.updateTableFromFile(comandTable,"comandaTrimisa.txt",columnName);
        }
        if(e.getSource()==checkButton){
            this.sendButton.setEnabled(true);
            StringBuilder req1=new StringBuilder("check,");
            req1.append(comanda[1]);
            try {
                client.ClientReq(req1.toString());
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            req=new StringBuilder("email,");
            String response=client.getResponse()[0];
            if(response.equals("da")){
                String res="Comanda Dumneavoastra este gata de livrare.";
                JOptionPane.showMessageDialog(confirmaAngajat,res);
                req.append(res).append(",");

            }else if(response.equals("nu")) {
                String res="Upss!! Am intampinat cateva probleme comanda va intarzia. Multumim pentru intelegere!";
                JOptionPane.showMessageDialog(confirmaAngajat,res);
                req.append(res).append(",");
            }

        }
        if(e.getSource()==sendButton){
            req.append(comanda[0]);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(confirmaAngajat,"Email trimis!");
        }

    }
}
