package GUI;
import ClientServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Admin extends JFrame implements ActionListener {
    private final Socket socket;
    private JButton SENDCREDENTIALSButton;
    private JButton PAYSALARIESButton;
    private JPanel Admin;
    private Client client;
    private JButton PRODUCTSButton;
    private JButton BACKButton;
    private JButton NOTIFICATIONSButton;

    public Admin(Socket socket) {
        this.socket = socket;
        setTitle("Admin");
        setContentPane(Admin);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
                new Login(socket);
                System.exit(0);
            }});

        timer.setRepeats(false);
        timer.start();*/
        this.SENDCREDENTIALSButton.addActionListener(this);
        this.PAYSALARIESButton.addActionListener(this);
        this.PRODUCTSButton.addActionListener(this);
        this.BACKButton.addActionListener(this);
        this.NOTIFICATIONSButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     if (e.getSource() == SENDCREDENTIALSButton){
         this.dispose();
         new GenerateCredentials(socket);
     }

     if(e.getSource() == PAYSALARIESButton){
         this.dispose();
         new PaySalary(socket);
     }

     if(e.getSource() == PRODUCTSButton){
         this.dispose();
         new Products(socket);
     }

     if(e.getSource() == NOTIFICATIONSButton){
         this.dispose();
         new NotificariAdmin(socket);
     }

     if(e.getSource() == BACKButton){
         this.dispose();
         new Login(socket);
     }
    }

}
