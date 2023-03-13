package GUI;

import ClientServer.Client;
import Validatoare.Validatoare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Profil extends JFrame implements ActionListener {
    private final Socket socket;
    private Client client;
    private String username;
    private JPanel profil;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JPasswordField passwordField1;
    private JButton updateButton1;
    private JButton backButton;
    private JButton exitButton;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private String[] str;
    private List<String> produseComanda;
    private Validatoare validatoare;
    public Profil(Socket socket, String username,String[] str,List<String>produseComanda) {
        this.socket=socket;
        this.username=username;
        this.str=str;
        this.produseComanda=produseComanda;
        this.validatoare=new Validatoare();
        setTitle("Profil");
        setContentPane(profil);
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
        this.updateButton1.addActionListener(this);
        textField1.setText(str[0]);
        textField2.setText(str[1]);
        textField3.setText(str[2]);
        textField4.setText(str[3]);
        textField5.setText(str[4]);
        passwordField1.setText(str[5]);
        if(str.length==9){
            textField6.setText(str[6]);
            textField7.setText(str[7]);
            textField8.setText(str[8]);
        }


        /*Timer timer;
        int delay = 40000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               *//* new Login(socket);
                dispose();*//*
                System.exit(0);
            }});

        timer.setRepeats(false);
        timer.start();*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==backButton) {
               new GUI.Client(socket,username,produseComanda);
               this.dispose();
       }
       if(e.getSource()==exitButton){
           this.dispose();
       }
       if(e.getSource()==updateButton1){
           StringBuilder req=new StringBuilder("client,updateProfil,");
           req.append(username).append(",");
           if(validatoare.isSqlInjectionSafe(textField1.getText())&&validatoare.isSqlInjectionSafe(textField2.getText())&&validatoare.isSqlInjectionSafe(textField3.getText())&&
                   validatoare.isSqlInjectionSafe(textField4.getText())&&validatoare.isSqlInjectionSafe(textField5.getText())&&validatoare.isSqlInjectionSafe(textField6.getText())&&
                   validatoare.isSqlInjectionSafe(textField7.getText())&&validatoare.isSqlInjectionSafe(textField8.getText())&&validatoare.isSqlInjectionSafe(passwordField1.getText())) {
               if (validatoare.validateCardNumber(textField6.getText()) && validatoare.validateCVV(textField7.getText()) && validatoare.validateExpirationDate(textField8.getText())) {

                   if (validatoare.validateEmail(textField4.getText())) {
                       req.append(textField1.getText()).append(",").append(textField2.getText()).append(",").append(textField3.getText()).
                               append(",").append(textField6.getText()).append(",").append(textField7.getText()).append(",").
                               append(textField8.getText()).append(",").append(textField4.getText()).append(",").append(textField5.getText()).
                               append(",").append(passwordField1.getPassword());

                       try {
                           client.ClientReq(req.toString());
                       } catch (IOException ex) {
                           throw new RuntimeException(ex);
                       }
                   }
                   else JOptionPane.showMessageDialog(null,"email incorect");
               }
               else JOptionPane.showMessageDialog(null,"Datele cardului sunt invalide");
           }
           else JOptionPane.showMessageDialog(null,"SQL Injection");
       }
    }
}
