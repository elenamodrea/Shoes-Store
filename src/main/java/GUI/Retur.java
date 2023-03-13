package GUI;

import ClientServer.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Retur extends JFrame implements ActionListener {
    private JPanel retur;
    private JList list1;
    private JButton plaseazaReturulButton;
    private JButton backButton;
    private JButton exitButton;
    private JButton veziProduseButton;
    private JButton veziComenziButton;
    private Socket socket;
    private String username;
    private Client client;
    private List<String> produseComanda;

    public Retur(Socket socket, String username,List<String> produseComanda){
        this.socket=socket;
        this.username=username;
        this.produseComanda=produseComanda;
        setTitle("Client");
        setContentPane(retur);
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
        this.plaseazaReturulButton.addActionListener(this);
        this.veziProduseButton.addActionListener(this);
        this.veziComenziButton.addActionListener(this);

       /* Timer timer;
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
        this.veziProduseButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backButton){
            new GUI.Client(socket, username,produseComanda);
            this.dispose();
        }
        if(e.getSource()==exitButton){
            this.dispose();
        }
        if(e.getSource()==plaseazaReturulButton){
            StringBuilder req=new StringBuilder("client,retur,");
            req.append(username).append(" ");
            String check= (String) list1.getSelectedValue();
            String[] get=check.split(" ");
            req.append(get[0]).append(" ");
            String date=java.time.LocalDate.now().toString();
            req.append(date);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null,"Returul a fost plasat.");

        }
        if(e.getSource()==veziProduseButton){
            String check= (String) list1.getSelectedValue();
            String[] get=check.split(" ");
            StringBuilder req=new StringBuilder("client,produseComanda,");
            req.append(username).append(",").append(get[0]);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String[] res= client.getResponse();
            DefaultListModel<String> list=new DefaultListModel<>();
            for (String s: res) {
                list.addElement(s);
            }
            list1.setModel(list);
        }
        if (e.getSource() == veziComenziButton) {
            this.veziProduseButton.setEnabled(true);
            StringBuilder req = new StringBuilder("client,listacomenzi,");
            req.append(username);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String[] res = client.getResponse();
            DefaultListModel<String> list = new DefaultListModel<>();
            for (String s : res) {
                list.addElement(s);
            }
            list1.setModel(list);
        }
    }

}
