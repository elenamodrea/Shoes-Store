package GUI;
import ClientServer.Client;
import Controller.ControllerPaySalary;
import DAO.AngajatDAO;
import Tables.Angajat;
import Tables.User;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PaySalary extends JFrame implements ActionListener {
    private JTable table;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton PAYEMPLOYEEButton;
    private JButton SHOWEMPLOYEESButton;
    private JPanel PaySalary;
    private JTextField textField5;
    private JTextField textField6;
    private JButton BACKButton;
    private final Socket socket;
    private AngajatDAO angajatDAO;
    private List<Tables.Angajat> angajat;
    private int selectedIndex;
    private int i;
    private Angajat selectedAngajat;
    private Client client;
    private StringBuilder req;
    private ControllerPaySalary controllerPaySalary;
    private JScrollPane jScrollPane;

    public PaySalary(Socket socket){
        this.socket=socket;
        this.angajatDAO = new AngajatDAO();
        this.angajat = new ArrayList<>();
        this.controllerPaySalary = new ControllerPaySalary();
        this.setTitle("Pay Salary");
        this.setContentPane(PaySalary);
        this.setSize(new Dimension(1100, 700));
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.SHOWEMPLOYEESButton.addActionListener(this);
        this.PAYEMPLOYEEButton.addActionListener(this);
        this.BACKButton.addActionListener(this);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (! table.getSelectionModel().isSelectionEmpty()){
                selectedIndex = table.convertRowIndexToModel(table.getSelectedRow());
                selectedAngajat = angajat.get(selectedIndex);
                if(selectedAngajat != null){
                    textField1.setText(selectedAngajat.getIdAngajat());
                    textField2.setText(selectedAngajat.getUser().getNume());
                    textField3.setText(selectedAngajat.getUser().getPrenume());
                    textField4.setText(String.valueOf(selectedAngajat.getSalariu()));
                    textField5.setText(selectedAngajat.getDurataContract());
                    textField6.setText(selectedAngajat.getUltimaPlatire());
                }
            }
        });

        i = 0;

        try {
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTable(){
        angajat = new ArrayList<>();
        i = 0;
        req = new StringBuilder();
        req.append("showAngajatPay");
        try {
            client.ClientReq(req.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if(client.getResponse()!=null){
                client.ClientReq(req.toString());
                while(i < client.getResponse().length){
                    Angajat a = angajatDAO.getAngajat(client.getResponse()[i]);
                    angajat.add(a);
                    //System.out.println(i + " " + a.getIdAngajat());
                    i++;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AngajatTableModel angajatTableModel = new AngajatTableModel(angajat);
        table.setModel(angajatTableModel);
        table.setAutoCreateRowSorter(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == SHOWEMPLOYEESButton){
            updateTable();
        }

        if(e.getSource() == PAYEMPLOYEEButton){
            selectedAngajat = new Angajat();
            selectedAngajat = angajatDAO.getAngajat(textField1.getText());
            User user = selectedAngajat.getUser();
            req = new StringBuilder();
            req.append("PayEmployee");
            req.append(",").append(textField1.getText()).append(",").append(textField4.getText()).append(",")
                    .append(textField5.getText()).append(",").append(textField6.getText()).append(",")
                    .append(user.getIdUser()).append(",").append(user.getNume()).append(",").append(user.getPrenume())
                    .append(",").append(user.getMail()).append(",").append(user.getAdresa()).append(",").append(user.getTelefon())
                    .append(",").append("angajat,").append(user.getUsername()).append(",").append(user.getParola());
            try {
                client.ClientReq(req.toString());
                controllerPaySalary.getResponse(client.getResponse());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Angajatul a fost platit.");

        }
        if(e.getSource() == BACKButton){
            this.dispose();
            new Admin(socket);
        }
    }

    public static class AngajatTableModel extends AbstractTableModel {

        private final String[] columns = {"ID", "Nume", "Prenume", "Salariu", "Data de angajare", "Durata contract", "Ultima platire"};
        private List<Angajat> angajatList;

        public AngajatTableModel(List<Angajat> angajatList) {
            this.angajatList = angajatList;
        }

        @Override
        public int getRowCount() {
            return angajatList.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> angajatList.get(rowIndex).getIdAngajat();
                case 1 -> angajatList.get(rowIndex).getUser().getNume();
                case 2 -> angajatList.get(rowIndex).getUser().getPrenume();
                case 3 -> angajatList.get(rowIndex).getSalariu();
                case 4 -> angajatList.get(rowIndex).getDataDeAngajare();
                case 5 -> angajatList.get(rowIndex).getDurataContract();
                case 6 -> angajatList.get(rowIndex).getUltimaPlatire();
                default -> "-";
            };
        }
        @Override
        public String getColumnName(int column){
            return columns[column];
        }
    }
}
