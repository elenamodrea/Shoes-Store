package GUI;
import ClientServer.Client;
import DAO.ProdusDAO;
import Tables.Produs;

import javax.mail.search.AndTerm;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChangeProducts extends JFrame implements ActionListener {
    private JPanel ViewProducts;
    private JTable table;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton MODIFYButton;
    private JButton DELETEButton;
    private JTextField textField2;
    private JButton SHOWPRODUCTSButton;
    private JButton BACKButton;
    private JButton RESTOCAREButton;
    private Socket socket;
    private Client client;
    private ProdusDAO produsDAO;
    private int selectedIndex;
    private Produs selectedProduct;
    private List<Produs> produs;
    private int i;
    private StringBuilder req;
    private String which;

    public ChangeProducts(Socket socket, String which) {
        this.which=which;
        this.socket=socket;
        this.produsDAO = new ProdusDAO();
        this.setTitle("Change Products");
        this.setContentPane(ViewProducts);
        this.setSize(new Dimension(900, 700));
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.SHOWPRODUCTSButton.addActionListener(this);
        this.DELETEButton.addActionListener(this);
        this.MODIFYButton.addActionListener(this);
        this.BACKButton.addActionListener(this);
        this.RESTOCAREButton.addActionListener(this);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (! table.getSelectionModel().isSelectionEmpty()){
                selectedIndex = table.convertRowIndexToModel(table.getSelectedRow());
                selectedProduct = produs.get(selectedIndex);
                if(selectedProduct != null){
                    textField1.setText(selectedProduct.getIdProdus());
                    textField2.setText(selectedProduct.getCuloare());
                    textField3.setText(selectedProduct.getMarime());
                    textField4.setText(selectedProduct.getPret());
                    textField5.setText(String.valueOf(selectedProduct.getStoc()));
                    textField6.setText(selectedProduct.getTipProdus());
                    textField7.setText(selectedProduct.getCategorie());
                }
            }
        });

        i = 0;

        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(which.equals("angajat")){

            textField5.setVisible(false);
            this.RESTOCAREButton.setVisible(false);
        }
    }

    private void addButtonActionListener(JButton backButton,String which){
        backButton.addActionListener(e -> {
            if(which.equals("angajat")){
                new Angajat(socket);
            }else if(which.equals("admin")){
                new Products(socket);
            }
        });
    }

    public void updateTable(){
        produs = new ArrayList<>();
        i = 0;
        req = new StringBuilder();
        req.append("ShowProducts");
        try {
            client.ClientReq(req.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            client.ClientReq(req.toString());
            while(i < client.getResponse().length){
                Produs p = produsDAO.getProdus(client.getResponse()[i]);
                produs.add(p);
                System.out.println(i + " " + p.getIdProdus());
                i++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*Timer timer;
        int delay = 110000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Products(socket);
                dispose();
            }});

        timer.setRepeats(false);
        timer.start();
*/
        ProdusTableModel produsTableModel = new ProdusTableModel(produs);
        table.setModel(produsTableModel);
        table.setAutoCreateRowSorter(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == SHOWPRODUCTSButton) {
            updateTable();
        }
        if(e.getSource() == BACKButton){
            //addButtonActionListener(BACKButton,which);
            if(which.equals("angajat")){
                this.dispose();
                new Angajat(socket);
            }else if(which.equals("admin")){
                this.dispose();
                new Products(socket);
            }
        }

        if(e.getSource() == MODIFYButton){
            req = new StringBuilder();
            req.append("ModifyProducts");
            req.append(",").append(textField1.getText()).append(",").append(textField2.getText()).append(",")
                    .append(textField3.getText()).append(",").append(textField4.getText()).append(",").append(textField5.getText())
                    .append(",").append(textField6.getText()).append(",").append(textField7.getText());
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Produsul a fost modificat.");

        }

        if(e.getSource() == DELETEButton){
            req = new StringBuilder();
            req.append("DeleteProducts");
            req.append(",").append(textField1.getText());
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Produsul a fost sters.");

        }

        if(e.getSource() == RESTOCAREButton){
            String stoc = textField5.getText();
            req = new StringBuilder();
                req.append("RestocProducts");
            req.append(",").append(textField1.getText()).append(",").append(stoc);
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Produsul a fost restocat.");

        }
    }

    public static class ProdusTableModel extends AbstractTableModel {

        private final String[] columns = {"ID", "Culoare", "Marime", "Pret", "Stoc", "Tip Produs", "Categorie"};
        private List<Produs> produsList;

        public ProdusTableModel(List<Produs> produsList) {
            this.produsList = produsList;
        }

        @Override
        public int getRowCount() {
            return produsList.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> produsList.get(rowIndex).getIdProdus();
                case 1 -> produsList.get(rowIndex).getCuloare();
                case 2 -> produsList.get(rowIndex).getMarime();
                case 3 -> produsList.get(rowIndex).getPret();
                case 4 -> produsList.get(rowIndex).getStoc();
                case 5 -> produsList.get(rowIndex).getTipProdus();
                case 6 -> produsList.get(rowIndex).getCategorie();
                default -> "-";
            };
        }
        @Override
        public String getColumnName(int column){
            return columns[column];
        }
    }
}
