package GUI;

import Ajutor.CodeGenerator;
import Ajutor.ConfirmEmail;
import Ajutor.WriteReadFile;
import ClientServer.Client;
import DAO.ClientDAO;
import DAO.UserDAO;
import Tables.RandomID;
import Tables.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ConfirmMail extends JFrame implements ActionListener{
    private JPanel ConfirmMail;
    private JTextField confirm;
    private String mail;
    private JButton VERIFYButton;
    private WriteReadFile writeReadFile;
    private User user;
    private RandomID randomID;
    private final Socket socket;
    private Client client;


    public ConfirmMail(User user, Socket socket) {
        this.socket=socket;
        setTitle("Confirm Mail");
        setContentPane(ConfirmMail);
        setSize(new Dimension(600, 600));
        setVisible(true);

        VERIFYButton.addActionListener(this);
        try {
            client=new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        /*Timer timer;
        int delay = 40000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               *//* new Register(socket);
                dispose();*//*
                System.exit(0);
            }});

        timer.setRepeats(false);
        timer.start();*/

        this.user = user;
        System.out.println(user.getMail());
        this.randomID = new RandomID();
    }

    public User getUser() {
        return user;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder req=new StringBuilder("confirm,");
        if(e.getSource()==VERIFYButton){
            req.append(confirm.getText()).append(",").append(user.getIdUser()).append(",").append(user.getNume()).append(",").append(user.getPrenume()).append(",")
                    .append(user.getTelefon()).append(",").append(user.getAdresa()).append(",").append(user.getMail()).append(",client,").append(user.getUsername()).append(",").append(user.getParola());
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new Login(socket);
            this.dispose();
        }
    }
}
