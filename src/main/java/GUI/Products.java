package GUI;
import ClientServer.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Products extends JFrame implements ActionListener {
    private JButton CREATEPRODUCTButton;
    private JButton VIEWPRODUCTSButton;
    private JPanel Products;
    private JButton BACKButton;
    private Socket socket;
    private Client client;

    public Products(Socket socket) {
        this.socket = socket;
        setTitle("Products");
        setContentPane(Products);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            client=new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Timer timer;
//        int delay = 40000; // Delay in milliseconds
//        timer = new Timer(delay, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Admin(socket);
//                dispose();
//            }});
//
//        timer.setRepeats(false);
//        timer.start();

        this.CREATEPRODUCTButton.addActionListener(this);
        this.VIEWPRODUCTSButton.addActionListener(this);
        this.BACKButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BACKButton){
            this.dispose();
            new Admin(socket);
        }

        if(e.getSource() == VIEWPRODUCTSButton){
            this.dispose();
            String admin="admin";
            new ChangeProducts(socket,admin);
        }

        if(e.getSource() == CREATEPRODUCTButton){
            this.dispose();
            new CreateProduct(socket);
        }
    }
}
