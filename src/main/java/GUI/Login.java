package GUI;

import ClientServer.Client;
import Controller.ControllerRegister;
import Validatoare.Validatoare;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;


public class Login extends JFrame implements ActionListener{
    private JPanel Login;
    private JTextField username;
    private JButton LOGINButton;
    private JButton REGISTERButton;
    private JPasswordField parola;
    private JButton EXITButton;
    private Client client;
    private final Socket socket;
    private Validatoare validatoare;
    private int seconds = 0;
    private int max = 120;
    private Timer timer;
    private java.util.List<String> produseComanda;

    public Login(Socket socket){
        this.socket=socket;
        this.produseComanda=new ArrayList<>();
        setTitle("Login");
        setContentPane(Login);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        validatoare = new Validatoare();

        try {
            client=new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Timer timer;
        int delay = 30000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new Login(socket);
                //dispose();
                System.exit(0);
            }});
        timer.setRepeats(false);
        timer.start();*/
        this.LOGINButton.addActionListener(this);
        this.REGISTERButton.addActionListener(this);
        this.EXITButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder req=new StringBuilder();

        if(e.getSource()==LOGINButton){
            req.append("login" + ",").append(username.getText()).append(",").append(parola.getPassword());
            this.dispose();
            if(validatoare.isSqlInjectionSafe(username.getText()) && validatoare.isSqlInjectionSafe(parola.getText())) {
                try {
                    client.ClientReq(req.toString());
                    System.out.println(req);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (client.getResponse()[1].equals("client")) {
                    new GUI.Client(client.getSocket(), username.getText(),produseComanda);
                } else if (client.getResponse()[1].equals("angajat")) {
                    new Angajat(client.getSocket());
                } else if (client.getResponse()[1].equals("admin")) {
                    new Admin(client.getSocket());
                } else {
                    JOptionPane.showMessageDialog(null, "Username sau parola gresita");
                }
            }
            else JOptionPane.showMessageDialog(null, "SQL injection");
        }
        if(e.getSource()==REGISTERButton){
            this.dispose();
            new Register(client.getSocket());

        }
        if(e.getSource()==EXITButton){
            this.dispose();
        }
    }
}