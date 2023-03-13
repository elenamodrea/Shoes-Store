package Ajutor;
import DAO.AngajatDAO;
import DAO.UserDAO;
import Tables.Angajat;
import Tables.RandomID;
import Tables.User;
import org.apache.commons.lang3.RandomStringUtils;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PaySalary {

    private String SMTP_HOST = "smtp.gmail.com";
    private String FROM_ADDRESS = "isproject160@gmail.com";
    private String PASSWORD = "hqtfkmnmpblpzehx";
    private String FROM_NAME = "Proiect";
    private SimpleDateFormat format;
    private Date date;

    public boolean sendMail(Angajat angajat) {
        format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date  = new Date();

        String message= "Numele angajatului: " + angajat.getUser().getNume() + " " + angajat.getUser().getPrenume()  + ".\n" + "Ati fost platit pentru luna curenta: " + format.format(date) + ". \nSuma transferata: " + angajat.getSalariu() + ".";
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");

            Session session = Session.getInstance(props, new SocialAuth());
            Message msg = new MimeMessage(session);

            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
            msg.setFrom(from);

            InternetAddress toAddress = new InternetAddress();
            //toAddress = new InternetAddress(angajat.getUser().getMail());
            toAddress = new InternetAddress("fazakasedina2001@gmail.com");
            msg.setRecipient(Message.RecipientType.TO, toAddress);


            InternetAddress bccAddress = new InternetAddress();
            bccAddress = new InternetAddress(angajat.getUser().getMail());
            msg.setRecipient(Message.RecipientType.BCC, bccAddress);

            msg.setSubject("Confirmare email");
            msg.setContent(message, "text/plain");
            Transport.send(msg);
            return true;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConfirmEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } catch (MessagingException ex) {
            Logger.getLogger(ConfirmEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    class SocialAuth extends Authenticator {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

        }
    }

    public static void main(String[] args) {

        PaySalary paySalary = new PaySalary();
        Angajat a = new Angajat();
        AngajatDAO angajatDAO = new AngajatDAO();
        a = angajatDAO.getAngajat("ysnzjClAhuanpQJM");
        paySalary.sendMail(a);
    }
}
