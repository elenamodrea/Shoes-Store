package GUI;

import Ajutor.TableHelper;
import Ajutor.WriteReadFile;
import ClientServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class AngajatNotifStoc extends JFrame implements ActionListener {
    private JPanel notificari;
    private JButton backButton;
    private JButton sendButton;
    private JButton refreshButton;
    private JTable tableaNotificari;
    private String notif;
    private Socket socket;
    private WriteReadFile writeReadFile;
    private Client client;
    private TableHelper tableHelper;
    public AngajatNotifStoc(Socket socket){
        this.socket=socket;
        this.writeReadFile=new WriteReadFile();
        this.tableHelper=new TableHelper();
        setTitle("Notificare Restocare");
        setContentPane(notificari);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.backButton.addActionListener(this);
        this.refreshButton.addActionListener(this);
        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==refreshButton){
            String col="ID Produs."+"Nou Stoc.";
            tableHelper.updateTableFromFile(tableaNotificari,"notificareRestocare.txt",col);
        }
        if(e.getSource()==backButton){
            this.dispose();
            new Angajat(socket);
        }
    }

}
