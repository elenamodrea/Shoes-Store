package GUI;

import ClientServer.Client;
import Tables.Produs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Angajat extends JFrame implements ActionListener {
    private Socket socket;
    private JButton notyButton;
    private JButton reviewsButton;
    private JButton nofiticaStocGolButton;
    private JButton cereriReturButton;
    private JPanel angajat;
    private JButton logOutButton;
    private JButton modifyButton;
    private JButton confirmaComandaButton;
    private JButton anuntaFavoriteButton;
    private List<Produs> produses;
    private Client client;

    public Angajat(Socket socket) {
        this.socket = socket;
        setTitle("Angajat");
        setContentPane(angajat);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.nofiticaStocGolButton.addActionListener(this);
        this.notyButton.addActionListener(this);
        this.reviewsButton.addActionListener(this);
        this.cereriReturButton.addActionListener(this);
        this.logOutButton.addActionListener(this);
        this.anuntaFavoriteButton.addActionListener(this);
        this.modifyButton.addActionListener(this);
        this.confirmaComandaButton.addActionListener(this);
        produses=new ArrayList<>();
        try {
            this.client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.nofiticaStocGolButton){
            this.dispose();
            new AngajatStocGol(socket);
        }
        if(e.getSource()==this.modifyButton){
            this.dispose();
            String angajat="angajat";
            new ChangeProducts(socket,angajat);
        }

        if(e.getSource()==this.notyButton){
            this.dispose();
            new AngajatNotifStoc(socket);
        }

        if(e.getSource()==this.cereriReturButton){
            this.dispose();
            new AngajatRetur(socket);
        }
        if(e.getSource()==this.reviewsButton){
            this.dispose();
            new AngajatRating(socket);
        }
        if(e.getSource()==this.anuntaFavoriteButton){
            this.dispose();
            new AngajatFavorite(socket);
        }
        if(e.getSource()==this.confirmaComandaButton){
            this.dispose();
            new AngajatConfirma(socket);
        }
        if(e.getSource()==this.logOutButton){
            this.dispose();
            new Login(socket);
        }
    }
}
