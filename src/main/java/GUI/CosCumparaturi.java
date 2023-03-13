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
import javax.swing.Timer;

public class CosCumparaturi extends JFrame implements ActionListener {
    private JPanel cosCumparaturi;
    private JButton backButton;
    private JButton exitButton;
    private JButton stergeButton1;
    private JButton plaseazaComandaButton;
    private JList list1;
    private JButton veziProduseButton;
    private final Socket socket;
    private Client client;
    private String username;
    private List<String> produseComanda;

    public CosCumparaturi(Socket socket, String username,List<String> produseComanda){
        this.socket=socket;
        this.username=username;
        this.produseComanda=produseComanda;
        setTitle("Client");
        setContentPane(cosCumparaturi);
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
        this.plaseazaComandaButton.addActionListener(this);
        this.veziProduseButton.addActionListener(this);
        this.stergeButton1.addActionListener(this);

      /*  Timer timer;
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
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backButton){
            new GUI.Client(socket,username,produseComanda);
            this.dispose();
        }
        if(e.getSource()==exitButton){
            this.dispose();
        }
        if(e.getSource()==veziProduseButton){
            StringBuilder req=new StringBuilder("client,produseDinCos,");
            for(String s:produseComanda){
                req.append(s).append(",");
            }
            req.deleteCharAt(req.length()-1);
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
        if(e.getSource()==stergeButton1){
            if(!list1.isSelectionEmpty()) {
                String prdusselectat = (String)list1.getSelectedValue();
                String[] idProdus=prdusselectat.split(" ");
                int j=-1,i=0;
                for (String s: produseComanda) {
                    if(s.contains(idProdus[0])){
                        j=i;
                    }
                    i++;
                }
                produseComanda.remove(j);
                }
        }
        if(e.getSource()==plaseazaComandaButton){
            StringBuilder req=new StringBuilder("client,plasareComanda,");
            req.append(username).append(" ");
            for (String s: produseComanda) {
               req.append(s).append(" ");
            }
            req.deleteCharAt(req.length()-1);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            produseComanda=new ArrayList<>();
            //JOptionPane.showMessageDialog(null,"Comanda a fost plasata");
        }


    }
}
