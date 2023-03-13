package Ajutor;

import DAO.AngajatDAO;
import DAO.UserDAO;
import Tables.Angajat;
import Tables.RandomID;
import Tables.User;
import org.apache.commons.lang3.RandomStringUtils;
import java.io.UnsupportedEncodingException;
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

public class GenerateCredentials {

    private String SMTP_HOST = "smtp.gmail.com";
    private String FROM_ADDRESS = "isproject160@gmail.com";
    private String PASSWORD = "hqtfkmnmpblpzehx";
    private String FROM_NAME = "Proiect";

    public boolean sendMail(String mail, String[] credentials) {
        String message= "Username: "+ credentials[0] + "\n Parola: " + credentials[1];
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
            bccAddress = new InternetAddress(mail);
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

    public String[] generateCredentials(User user, int salariu, String durata, String ultimaPltire, String dataDeAngajare){
        String[] credentials = new String[3];
        String username=user.getNume() + user.getPrenume() + RandomStringUtils.random(3,false, true);
        String parola= RandomStringUtils.random(12,true, true);
        user.setUsername(username);
        user.setParola(parola);
        RandomID randomID = new RandomID();
        Angajat angajat = new Angajat(randomID.getRandomID(), salariu, durata, ultimaPltire, dataDeAngajare, user);
        AngajatDAO angajatDAO = new AngajatDAO();
        angajatDAO.updateAngajat(angajat);
        credentials[0] = username;
        credentials[1] = parola;
        return credentials;
    }

    public static void main(String[] args) {
        GenerateCredentials generateCredentials = new GenerateCredentials();
        RandomID randomID = new RandomID();
        User user = new User(randomID.getRandomID(), "Dumitriu", "Gheorghe", "3785", "Targu-Mures", "domibeni10@gmail.com", "angajat", "aurel123", "kdfg");
        //generateCredentials.sendMail(user, 5800, "6 ani");
    }
}
