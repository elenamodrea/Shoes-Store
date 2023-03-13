package GUI;

import ClientServer.Client;
import Validatoare.Validatoare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Review extends JFrame implements ActionListener {
    private JPanel review;
    private JList list1;
    private JButton backButton;
    private JButton exitButton;
    private JButton lasaReviewButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton veziReviewButton;
    private final Socket socket;
    private String username;
    private String idProdus;
    private Client client;
    private List<String> produseComanda;
    private Validatoare validatoare;
    public Review(Socket socket,String username,String idProdus, List<String> produseComanda){
        this.socket=socket;
        this.username=username;
        this.idProdus=idProdus;
        this.produseComanda=produseComanda;
        this.validatoare=new Validatoare();
        setTitle("Review");
        setContentPane(review);
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
        this.lasaReviewButton.addActionListener(this);
        this.veziReviewButton.addActionListener(this);

      /*  Timer timer;
        int delay = 40000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(socket);

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
            new Produs(socket,username,idProdus,produseComanda);
            this.dispose();
        }
        if(e.getSource()==lasaReviewButton){
            StringBuilder req=new StringBuilder("client,reviewNou,");
            req.append(username).append(",").append(idProdus).append(",");
            if(validatoare.isSqlInjectionSafe(textField1.getText())&&validatoare.isSqlInjectionSafe(textField2.getText())) {
                if (validatoare.validateRating(textField2.getText())) {
                    req.append(textField1.getText()).append(",").append(textField2.getText());
                    try {
                        client.ClientReq(req.toString());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String[] res = client.getResponse();
                    if (res[0].equals("review deja existent")) {
                        JOptionPane.showMessageDialog(null, "A fost deja adaugat un review de acest client");
                    }

                } else {
                JOptionPane.showMessageDialog(null,"Va rugam sa alegeti ca rating un numar natural intre 1 si 5");
            }}
                else {JOptionPane.showMessageDialog(null,"SQL Injection");}
        }
        if(e.getSource()==veziReviewButton){
           StringBuilder req=new StringBuilder("client,listaReview,");
            req.append(username).append(",").append(idProdus).append(",");
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String[] res=client.getResponse();
            DefaultListModel<String> list=new DefaultListModel<>();
            for (String s: res) {
                list.addElement(s);
            }
            list1.setModel(list);
        }
    }
}
