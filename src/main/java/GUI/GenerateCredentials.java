package GUI;
import ClientServer.Client;
import Controller.ControllerGenerateCredentials;
import DAO.AngajatDAO;
import Tables.Angajat;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GenerateCredentials extends JFrame implements ActionListener {
    private JPanel GenerateCredentials;
    private AngajatDAO angajatDAO;
    private JTable table;
    private JButton SHOWEMPLOYEESButton;
    private JTextField textField1;
    private JTextField textField4;
    private JTextField textField5;
    private JButton SENDCREDENTIALSButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField6;
    private JTextField textField7;
    private JButton GENERATECREDENTIALSButton;
    private JButton BACKButton;
    private JTextField textField8;
    private JTextField textField9;
    private List<Angajat> angajat;
    private Angajat selectedAngajat;
    private int selectedIndex;
    private final Socket socket;
    private Client client;
    private ControllerGenerateCredentials controllerGenerateCredentials;
    private int i;


    public GenerateCredentials(Socket socket){
        this.socket=socket;
        this.angajatDAO = new AngajatDAO();
        this.angajat = new ArrayList<>();
        this.setTitle("Send Credentials");
        this.setContentPane(GenerateCredentials);
        this.setSize(new Dimension(1100, 700));
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        controllerGenerateCredentials = new ControllerGenerateCredentials();

        this.SHOWEMPLOYEESButton.addActionListener(this);
        this.SENDCREDENTIALSButton.addActionListener(this);
        this.GENERATECREDENTIALSButton.addActionListener(this);
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
                    textField8.setText(selectedAngajat.getUltimaPlatire());
                    textField9.setText(selectedAngajat.getDataDeAngajare());

                }
            }
        });

        i = 0;

        try {
            client=new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateTable(){
        StringBuilder req = new StringBuilder();;
        angajat = new ArrayList<>();
        i = 0;
        req.append("showAngajat");
        try {
            client.ClientReq(req.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            client.ClientReq(req.toString());
            while(i < client.getResponse().length){
                Angajat a = angajatDAO.getAngajat(client.getResponse()[i]);
                angajat.add(a);
                System.out.println(i + " " + a.getIdAngajat());
                i++;
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
        StringBuilder req;

        if (e.getSource() == SHOWEMPLOYEESButton){
            updateTable();
        }

        if(e.getSource() == GENERATECREDENTIALSButton){
            req = new StringBuilder();;
            req.append("GenerateCredentials");
            req.append(",").append(textField1.getText()).append(",").append(textField4.getText()).append(",")
                    .append(textField5.getText()).append(",").append(textField8.getText()).append(",").append(textField9.getText());
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            textField6.setText(client.getResponse()[0]);
            textField7.setText(client.getResponse()[1]);
        }

        if(e.getSource() == SENDCREDENTIALSButton){
            req = new StringBuilder();;
            req.append("SendCredentials");
            req.append(",").append(textField6.getText()).append(",").append(textField7.getText()).append(",")
                    .append(textField1.getText());
            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Credentialele au fost trimise cu succes.");

        }

        if(e.getSource() == BACKButton){
            this.dispose();
            new Admin(socket);
        }
    }

    public static class AngajatTableModel extends AbstractTableModel {

        private final String[] columns = {"ID", "Nume", "Prenume", "Salariu", "Data de angajare", "Durata contract", "Ultima platire"};;
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
