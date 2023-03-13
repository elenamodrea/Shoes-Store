package GUI;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class CreateProduct extends JFrame implements ActionListener{
    private JRadioButton femeieRadioButton;
    private JRadioButton barbatRadioButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton CREATEPRODUCTButton;
    private JButton BROWSEFORPICTUREButton;
    private JLabel imageLabel;
    private JPanel CreateProduct;
    private JRadioButton fataRadioButton;
    private JRadioButton baiatRadioButton;
    private JButton BACKButton;
    private JFileChooser jFileChooser;
    private Socket socket;
    private ClientServer.Client client;
    private StringBuilder req;
    private File selectedImage;
    private String selectedImagePath;
    private ImageIcon imageIcon;


    public CreateProduct(Socket socket) {
            this.socket = socket;
            setTitle("Admin");
            setContentPane(CreateProduct);
            setSize(new Dimension(550, 700));
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            try {
                client= new ClientServer.Client(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        /*Timer timer;
        int delay = 120000; // Delay in milliseconds
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Products(socket);
                dispose();
            }});

        timer.setRepeats(false);
        timer.start();*/
            this.BROWSEFORPICTUREButton.addActionListener(this);
            this.CREATEPRODUCTButton.addActionListener(this);
            this.BACKButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BROWSEFORPICTUREButton){
            jFileChooser = new JFileChooser();
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
            jFileChooser.addChoosableFileFilter(fnef);
            int showOpenDialogue = jFileChooser.showOpenDialog(null);
            if(showOpenDialogue == JFileChooser.APPROVE_OPTION){
                selectedImage = jFileChooser.getSelectedFile();
                selectedImagePath = selectedImage.getAbsolutePath();
                JOptionPane.showMessageDialog(null, selectedImagePath);
                imageIcon = new ImageIcon(selectedImagePath);
                imageLabel.setIcon(imageIcon);
                System.out.println(selectedImage.getName());
            }
        }

        if(e.getSource() == CREATEPRODUCTButton){
            req = new StringBuilder();
            req.append("CreateProduct");
            req.append(",").append(selectedImage.getName()).append(",").append(comboBox1.getSelectedItem()).append(",")
                    .append(comboBox2.getSelectedItem()).append(",").append(textField1.getText()).append(",")
                    .append(textField2.getText()).append(",").append(textField3.getText()).append(",");
            if(baiatRadioButton.isSelected()){
                req.append("baieti");
            }
            else if (barbatRadioButton.isSelected()) {
                req.append("barbati");
            }
            else if(fataRadioButton.isSelected()){
                req.append("fete");
            }
            else if(femeieRadioButton.isSelected()){
                req.append("femei");
            }

            try {
                client.ClientReq(req.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Produsul a fost creat.");
        }

        if(e.getSource() == BACKButton){
            this.dispose();
            new Products(socket);
        }
    }
}
