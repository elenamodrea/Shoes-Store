package GUI;

import Ajutor.WriteReadFile;
import ClientServer.Client;
import Tables.RandomID;
import Tables.User;
import Validatoare.Validatoare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Register extends JFrame implements ActionListener{
    private JPanel register;
    private JTextField nume;
    private JTextField prenume;
    private JTextField telefon;
    private JTextField adresa;
    private JTextField mail;
    private JTextField username;
    private JButton REGISTERButton;
    private JButton BACKButton;
    private JButton EXITButton;
    private JPasswordField parola;
    private RandomID randomID;
    private final Socket socket;
    private Client client;
    private Validatoare validatoare;

    public void setUsername(JTextField username) {
        this.username = username;
    }

    public Register(Socket socket){
        super();
        setTitle("Register");
        setContentPane(register);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        randomID = new RandomID();
        this.validatoare = new Validatoare();
        this.socket=socket;
        this.REGISTERButton.addActionListener(this);
        this.BACKButton.addActionListener(this);
        this.EXITButton.addActionListener(this);
        try {
            client=new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
       /* Timer timer;
        int delay = 60000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                *//*new Login(socket);
                dispose();*//*
                System.exit(0);
            }});
        timer.setRepeats(false);
        timer.start();*/

    }

    public void registerListener(ActionListener actionListener){
        this.REGISTERButton.addActionListener(actionListener);
    }

    public void backListener(ActionListener actionListener){
        this.BACKButton.addActionListener(actionListener);
    }

    public void exitListener(ActionListener actionListener){
        this.EXITButton.addActionListener(actionListener);
    }

    public JPanel getRegister() {
        return register;
    }

    public String getNume() {
        return nume.getText();
    }

    public String getPrenume() {
        return prenume.getText();
    }

    public String getTelefon() {
        return telefon.getText();
    }

    public String getAdresa() {
        return adresa.getText();
    }

    public String getMail() {
        return mail.getText();
    }

    public JButton getREGISTERButton() {
        return REGISTERButton;
    }

    public JButton getBACKButton() {
        return BACKButton;
    }

    public JButton getEXITButton() {
        return EXITButton;
    }

    public String getParola() {
        return parola.getText();
    }

    public String getUsername() {
        return username.getText();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==BACKButton){
            new Login(socket);
            this.dispose();
        }
        if(e.getSource()==EXITButton){
            this.dispose();
        }
        StringBuilder reqReg=new StringBuilder("register,");
        if(e.getSource()==REGISTERButton){
            reqReg.append(nume.getText()).append(",").append(prenume.getText()).append(",").append(telefon.getText()).append(",").append(adresa.getText()).append(",")
                    .append(mail.getText()).append(",").append(username.getText()).append(",").append(parola.getPassword());

            this.dispose();
            if(validatoare.validateEmail(mail.getText()) && validatoare.isSqlInjectionSafe(nume.getText()) && validatoare.isSqlInjectionSafe(prenume.getText())&& validatoare.isSqlInjectionSafe(username.getText())&& validatoare.isSqlInjectionSafe(adresa.getText())&& validatoare.isSqlInjectionSafe(parola.getText())) {
                try {
                    client.ClientReq(reqReg.toString());
                    System.out.println(reqReg);
                    if(client.getResponse()[0].equals("Mailul a fost deja folosit.")){
                        JOptionPane.showMessageDialog(null, client.getResponse()[0]);
                        new Register(socket);
                    }
                    else if(client.getResponse()[0].equals("Username-ul a fost deja folosit.")){
                        JOptionPane.showMessageDialog(null, client.getResponse()[0]);
                        new Register(socket);
                    }
                    else
                    {
                        User user = new User(client.getResponse()[0], nume.getText(), prenume.getText(), telefon.getText(), adresa.getText(), mail.getText(), "client", username.getText(), parola.getText());
                        System.out.println(user.getIdUser());
                        new ConfirmMail(user, client.getSocket());

                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Adresa de mail este invalida!");
        }
    }
}
