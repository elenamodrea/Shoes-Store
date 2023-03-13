package GUI;

import ClientServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import javax.swing.ImageIcon;

public class Produs extends JFrame implements ActionListener {
    private JPanel Produs;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton reviewButton;
    private JButton adaugaInCosButton;
    private JButton backButton;
    private JButton exitButton;
    private JLabel poza;
    private JTextField textField7;
    private JButton favoritButton;
    private final Socket socket;
    private String username;
    private Client client;
    private String idProdus;
    private List<String> produseComanda;

    public Produs(Socket socket,String username, String idProdus,List<String>produseComanda) {
        this.socket=socket;
        this.username=username;
        this.idProdus=idProdus;
        this.produseComanda=produseComanda;
        setTitle("Produs");
        setContentPane(Produs);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            client=new ClientServer.Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.backButton.addActionListener(this);
        this.exitButton.addActionListener(this);
        this.reviewButton.addActionListener(this);
        this.adaugaInCosButton.addActionListener(this);
        this.favoritButton.addActionListener(this);
        StringBuilder req=new StringBuilder("client,produs,");
        req.append(idProdus);
        try {
            client.ClientReq(req.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String[] res= client.getResponse();
        textField1.setText(res[0]);
        textField2.setText(res[1]);
        textField3.setText(res[2]);
        textField4.setText(res[3]);
        textField5.setText(res[4]);
        textField6.setText(res[5]);
        textField7.setText(res[6]);
        ImageIcon imageIcon=new ImageIcon(res[7]);
        poza.setIcon(imageIcon);
      /*  Timer timer;
        int delay = 40000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(socket);
                dispose();
            }});

        timer.setRepeats(false);
        timer.start();

       */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     if(e.getSource()==exitButton){
         this.dispose();
     }
     if(e.getSource()==backButton){
         new GUI.Client(socket,username,produseComanda);
         this.dispose();
     }
     if(e.getSource()==reviewButton){
         new Review(socket,username,idProdus,produseComanda);
         this.dispose();
     }
     if(e.getSource()==adaugaInCosButton){
         produseComanda.add(idProdus);
     }
     if(e.getSource()==favoritButton){
         StringBuilder req=new StringBuilder("client,favorit,");
         req.append(username).append(" ").append(idProdus);
         try {
             client.ClientReq(req.toString());
         } catch (IOException ex) {
             throw new RuntimeException(ex);
         }
         String[] res= client.getResponse();
         if(res[0].equals("deja in stoc")){
             JOptionPane.showMessageDialog(null,"Produsul se afla deja in stoc. Il puteti adauga in cos.");
         }
         else JOptionPane.showMessageDialog(null,"Veti primi un e-mail atunci cand produsul va fi restocat");
     }
    }
}
