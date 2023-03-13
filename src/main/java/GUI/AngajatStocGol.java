package GUI;

import Ajutor.TableHelper;
import Ajutor.WriteReadFile;
import Business.AngajatBLL;
import ClientServer.Client;
import DAO.ProdusDAO;
import Tables.Produs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class AngajatStocGol extends JFrame implements ActionListener {
    private JPanel Stoc;
    private JTable table1;
    private JButton sendButton;
    private JButton backButton;
    private JButton displayButton;
    private Socket socket;
    private ProdusDAO produsDAO;
    private List<Produs> produs;
    private Client client;
    private int i;
    private StringBuilder req;
    private WriteReadFile writeReadFile;
    private AngajatBLL angajatBLL;
    private TableHelper tableHelper;

    public AngajatStocGol(Socket socket){
        this.socket=socket;
        this.produsDAO = new ProdusDAO();
        this.writeReadFile=new WriteReadFile();
        this.setTitle("Anunta Stoc Gol");
        this.setContentPane(Stoc);
        this.setSize(new Dimension(900, 700));
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.displayButton.addActionListener(this);
        this.sendButton.addActionListener(this);
        this.backButton.addActionListener(this);
        this.tableHelper=new TableHelper();
        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder req=new StringBuilder("angajat,");
        if(e.getSource()==displayButton){
            req.append("allProd");
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String[] prod=client.getResponse();
            String col="ID."+ "Culoare."+ "Marime."+ "Pret."+"Stoc."+"Nume."+ "Categorie";
            tableHelper.updateTableFromString(table1,prod,col);
        }
        if(e.getSource()==sendButton){
            req.append("writeInFile,");
            String stoc=tableHelper.getAttr(table1)[4];
            if(stoc.equals("0")){
                try {
                    req.append("notificareStocGol.txt,");
                    req.append(tableHelper.getSelectedRows(table1));
                    client.ClientReq(req.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(Stoc,"Mai sunt produse pe stoc");
            }

        }
        if(e.getSource()==backButton){
            this.dispose();
            new Angajat(socket);
        }

    }
}
