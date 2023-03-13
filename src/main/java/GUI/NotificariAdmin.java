package GUI;

import ClientServer.Client;
import DAO.ProdusDAO;
import Tables.Produs;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NotificariAdmin extends JFrame implements ActionListener {
    private JPanel notificationsAdmin;
    private JTable table;
    private JButton VIEWNOTIFICATIONSButton;
    private JButton BACKButton;
    private JButton NOTIFICATIONHANDLEDButton;
    private Socket socket;
    private List<Produs> produsList;
    private int selectedIndex;
    private Produs selectedProdus;
    private int i=0;
    private Client client;
    private StringBuilder req;
    private ProdusDAO produsDAO;

    public NotificariAdmin(Socket socket){
        this.socket=socket;
        this.produsList = new ArrayList<Produs>();
        this.setTitle("Send Credentials");
        this.setContentPane(notificationsAdmin);
        this.setSize(new Dimension(900, 700));
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.produsDAO = new ProdusDAO();

        this.BACKButton.addActionListener(this);
        this.NOTIFICATIONHANDLEDButton.addActionListener(this);
        this.VIEWNOTIFICATIONSButton.addActionListener(this);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (! table.getSelectionModel().isSelectionEmpty()){
                selectedIndex = table.convertRowIndexToModel(table.getSelectedRow());
                selectedProdus = produsList.get(selectedIndex);
                /*if(selectedAngajat != null){
                    textField1.setText(selectedAngajat.getIdAngajat());
                    textField2.setText(selectedAngajat.getUser().getNume());
                    textField3.setText(selectedAngajat.getUser().getPrenume());
                    textField4.setText(String.valueOf(selectedAngajat.getSalariu()));
                    textField5.setText(selectedAngajat.getDurataContract());
                    textField8.setText(selectedAngajat.getUltimaPlatire());

                }*/
            }
        });
        i = 0;

        try {
            client=new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateTable(){
        produsList = new ArrayList<Tables.Produs>();
        i = 0;
        req = new StringBuilder();
        req.append("ShowNotificationsAdmin");
        try {
            client.ClientReq(req.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int gol = 0;
        try {
            client.ClientReq(req.toString());
            while(i < client.getResponse().length){
                Tables.Produs p = produsDAO.getProdus(client.getResponse()[i]);
                produsList.add(p);
                if(p != null) {
                    System.out.println(i + " " + p.getIdProdus());
                    gol = 1;
                }
                i++;
            }
            if(gol == 0){
                JOptionPane.showMessageDialog(null, "Nu sunt notificari noi.");
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ProdusTableModel produsTableModel = new ProdusTableModel(produsList);
        table.setModel(produsTableModel);
        table.setAutoCreateRowSorter(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BACKButton){
            this.dispose();
            new Admin(socket);
        }
        if(e.getSource() == VIEWNOTIFICATIONSButton){
            updateTable();
        }

        if(e.getSource() == NOTIFICATIONHANDLEDButton){
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you handled the notification?");
            switch (result) {
                case JOptionPane.YES_OPTION:
                    req = new StringBuilder();
                    req.append("NotificationHandled");
                    req.append(",").append(selectedProdus.getIdProdus());
                    try {
                        client.ClientReq(req.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,"Notification is handled.");

                    System.out.println("Yes");
                    break;

                case JOptionPane.CLOSED_OPTION:
                    System.out.println("Closed");
                    break;
            }
        }
    }

    public static class ProdusTableModel extends AbstractTableModel {

        private final String[] columns = {"ID", "Culoare", "Marime", "Pret", "Stoc", "Tip Produs", "Categorie"};
        private List<Tables.Produs> produses;

        public ProdusTableModel(List<Tables.Produs> produsList) {
            this.produses = produsList;
        }

        @Override
        public int getRowCount() {
            return produses.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> produses.get(rowIndex).getIdProdus();
                case 1 -> produses.get(rowIndex).getCuloare();
                case 2 -> produses.get(rowIndex).getMarime();
                case 3 -> produses.get(rowIndex).getPret();
                case 4 -> produses.get(rowIndex).getStoc();
                case 5 -> produses.get(rowIndex).getTipProdus();
                case 6 -> produses.get(rowIndex).getCategorie();
                default -> "-";
            };
        }
        @Override
        public String getColumnName(int column){
            return columns[column];
        }
    }
}
