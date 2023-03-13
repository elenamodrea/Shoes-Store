package GUI;



import Ajutor.ConfirmEmail;
import Tables.Produs;
import Validatoare.Validatoare;

import javax.swing.*;
import java.util.Timer;
//import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class Client extends JFrame implements ActionListener {

    private final Socket socket;
    private ClientServer.Client client;
    private String username;
    private JPanel Client;
    private JTextField textField1;
    private JButton cautareButton;
    private JButton cosCumparaturiButton;
    private JComboBox categorii;
    private JList<String> list1;
    private JButton profilButton;
    private JButton backButton;
    private JButton exitButton;
    private JButton returButton;
    private JComboBox sort;
    private JButton sortareButton;
    private JButton newsletterButton;
    private JButton selecteazaProdusButton;
    private List<String> produseComanda;
    private Validatoare validatoare;

    public Client(Socket socket, String username, List<String> produseComanda) {
        this.socket = socket;
        this.username = username;
        this.produseComanda = produseComanda;
        this.validatoare = new Validatoare();
        setTitle("Client");
        setContentPane(Client);
        setSize(new Dimension(500, 480));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.backButton.addActionListener(this);
        this.exitButton.addActionListener(this);
        this.cautareButton.addActionListener(this);
        this.cosCumparaturiButton.addActionListener(this);
        this.profilButton.addActionListener(this);
        this.returButton.addActionListener(this);
        this.sortareButton.addActionListener(this);
        this.newsletterButton.addActionListener(this);
        this.selecteazaProdusButton.addActionListener(this);
        try {
            client = new ClientServer.Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        categorii.addItem("Nothing");
        categorii.addItem("Femei");
        categorii.addItem("Barbati");
        categorii.addItem("Baieti");
        categorii.addItem("Fete");

        sort.addItem("Crescator");
        sort.addItem("Descrescator");
        sort.addItem("A-Z");
        sort.addItem("Z-A");

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            new Login(socket);
            this.dispose();
        }
        if (e.getSource() == exitButton) {
            this.dispose();
        }
        if (e.getSource() == cautareButton) {
            StringBuilder req = new StringBuilder("client,cautare,");
            if (categorii.getSelectedIndex() == 0) {
                if (validatoare.isSqlInjectionSafe(textField1.getText())) {
                    if (textField1.getText().isEmpty())
                        req.append("all items");
                    else req.append(textField1.getText());
                } else JOptionPane.showMessageDialog(null, "SQL Injection");
            } else if (categorii.getSelectedIndex() == 1)
                req.append("Femei");
            else if (categorii.getSelectedIndex() == 2)
                req.append("Barbati");
            else if (categorii.getSelectedIndex() == 3)
                req.append("Baieti");
            else if (categorii.getSelectedIndex() == 4)
                req.append("Fete");
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
        if (e.getSource() == profilButton) {
            StringBuilder req = new StringBuilder("client,profil,");
            req.append(username);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String[] res = client.getResponse();
            new Profil(socket, username, res, produseComanda);
            this.dispose();
        }
        if (e.getSource() == sortareButton) {
            ListModel<String> strings = list1.getModel();
            List<Produs> produses = new ArrayList<>();
            for (int i = 0; i < strings.getSize(); i++) {
                String[] s = strings.getElementAt(i).split(" ");
                Produs produs = new Produs(s[0], s[2], s[4], s[3], 0, s[1], "", "0");
                produses.add(produs);
            }
            Comparator<Produs> compareByPret = (Produs o1, Produs o2) ->
                    (int) (Double.parseDouble(o1.getPret()) - (Double.parseDouble(o2.getPret())));
            Comparator<Produs> compareByName = (Produs o1, Produs o2) ->
                    o1.getTipProdus().compareTo(o2.getTipProdus());
            List<String> strings1 = new ArrayList<>();
            if (sort.getSelectedIndex() == 2) {
                Collections.sort(produses, compareByName);
                for (Produs p : produses) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings1.add(str.toString());
                }
                DefaultListModel<String> list = new DefaultListModel<>();
                for (String s : strings1) {
                    list.addElement(s);
                }
                list1.setModel(list);
            } else if (sort.getSelectedIndex() == 3) {
                Collections.sort(produses, compareByName.reversed());
                for (Produs p : produses) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings1.add(str.toString());
                }
                DefaultListModel<String> list = new DefaultListModel<>();
                for (String s : strings1) {
                    list.addElement(s);
                }
                list1.setModel(list);
            } else if (sort.getSelectedIndex() == 0) {
                Collections.sort(produses, compareByPret);
                for (Produs p : produses) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings1.add(str.toString());
                }
                DefaultListModel<String> list = new DefaultListModel<>();
                for (String s : strings1) {
                    list.addElement(s);
                }
                list1.setModel(list);
            } else if (sort.getSelectedIndex() == 1) {
                Collections.sort(produses, compareByPret.reversed());
                for (Produs p : produses) {
                    StringBuilder str = new StringBuilder();
                    str.append(p.getIdProdus()).append(" ").append(p.getTipProdus()).append(" ").append(p.getCuloare()).append(" ")
                            .append(p.getPret()).append(" ").append(p.getMarime());
                    strings1.add(str.toString());
                }
                DefaultListModel<String> list = new DefaultListModel<>();
                for (String s : strings1) {
                    list.addElement(s);
                }
                list1.setModel(list);
            }
        }
        if (e.getSource() == cosCumparaturiButton) {
            new CosCumparaturi(socket, username, produseComanda);
            this.dispose();
        }
        if (e.getSource() == returButton) {
            new Retur(socket, username, produseComanda);
            this.dispose();
        }
        if (e.getSource() == newsletterButton) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 13);
            c.set(Calendar.MINUTE, 00);
            c.set(Calendar.SECOND, 00);

            Timer timer = new Timer();
            ConfirmEmail confirmEmail = new ConfirmEmail();
            StringBuilder req = new StringBuilder("client,newsletter,");
            req.append(username);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String[] res = client.getResponse();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //Call your method here
                    confirmEmail.sendMail(res[0], res[0], "Check our website for latest shoes!!!");
                }
            }, c.getTime(), 30000); //24hrs == 86400000ms
        }
        if (e.getSource() == selecteazaProdusButton) {
            if (!list1.isSelectionEmpty()) {
                String prdusselectat = list1.getSelectedValue();
                String[] idProdus = prdusselectat.split(" ");
                new GUI.Produs(socket, username, idProdus[0], produseComanda);
                this.dispose();
            }
        }
    }



    }

