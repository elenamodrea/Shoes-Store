package Business;
import Ajutor.GenerateCredentials;
import Ajutor.WriteReadFile;
import DAO.AngajatDAO;
import DAO.UserDAO;
import Tables.Angajat;
import Tables.RandomID;
import Tables.User;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GenerateCredentialsBLL {
    private AngajatDAO angajatDAO;
    private GenerateCredentials generateCredentials = new GenerateCredentials();
    private UserDAO userDAO;
    private User user;
    private Angajat angajat;
    private DateTimeFormatter simpleDateFormat;
    private LocalDate localDate;
    private LocalDate now;
    private WriteReadFile writeReadFile;
    private RandomID randomID;

    public GenerateCredentialsBLL() {
        userDAO = new UserDAO();
        angajatDAO = new AngajatDAO();
        writeReadFile = new WriteReadFile();
        randomID = new RandomID();
    }

    public GenerateCredentialsBLL(UserDAO userDAO, AngajatDAO angajatDAO) {
        this.userDAO = userDAO;
        this.angajatDAO = angajatDAO;
    }

    public List<Angajat> createTableObjects() {
        List<Angajat> angajatList = new ArrayList<>();
        String[] s = new String[20];

        String[] strings = writeReadFile.read("viitoriAngajati.txt");
        for (int i = 0; i < strings.length; i++) {
            for (String s1 : strings) {
                if (strings[i] != null) {
                    s1 = strings[i].toString();
                    String[] strings1 = s1.split(",");
                    List<Angajat> angajats = angajatDAO.findAllAngajat();
                    System.out.println(s1);
                    if(userDAO.getUserName(strings1[7]) != null){
                        System.out.println(userDAO.getUserName(strings1[7]));
                        user = userDAO.getUserName(strings1[7]);
                        for(Angajat a: angajats){
                            if(a.getUser().getUsername().equals(strings1[7])){
                                angajat = a;
                            }
                        }
                    }
                    else {
                        user = new User(randomID.getRandomID(), strings1[2], strings1[4], strings1[5], strings1[0], strings1[1], strings1[6], strings1[7], strings1[3]);
                        angajat = new Angajat(randomID.getRandomID(), Integer.parseInt(strings1[10]), strings1[9], strings1[11], strings1[8], user);
                        angajatDAO.createAngajat(angajat);
                    }
                }
            }
            angajatList.add(angajat);
        }
        for (Angajat a: angajatList){
            System.out.println(a.getUser().getNume());
        }
        return angajatList;
    }

    public String[] generateCredentials(User user, int salariu, String durata, String ultimaPltire, String dataDeAngajare){
        String[] strings =  generateCredentials.generateCredentials(user, salariu, durata, ultimaPltire, dataDeAngajare);
        return strings;
    }

    public User convertUser(String[] req){
        angajat = angajatDAO.getAngajat(req[1]);
        User user = userDAO.getUser(angajat.getUser().getIdUser());
        return user;
    }

    public String convertMail(String id){
        String mail = angajatDAO.getAngajat(id).getUser().getMail();
        return mail;
    }

    public boolean checkAngajatContractBefore(String id){
        Angajat angajat = angajatDAO.getAngajat(id);
        String dataDeAngajare = angajat.getDataDeAngajare();
        String durata = angajat.getDurataContract();
        List<String> durataContract = List.of(durata.split(" "));
        int durataAngajare = Integer.parseInt(durataContract.get(0));
        String instanta = durataContract.get(1);
        simpleDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        localDate = LocalDate.parse(dataDeAngajare, simpleDateFormat);
        now = LocalDate.now();
        LocalDate newDate = null;

        if(instanta.equals("ani")){
            newDate = localDate.plus(Period.ofYears(durataAngajare));
        }
        else if(instanta.equals("luni")){
            newDate = localDate.plus(Period.ofMonths(durataAngajare));
        }
        else if(instanta.equals("zile")){
            newDate = localDate.plus(Period.ofDays(durataAngajare));
        }
        System.out.println(newDate + " " + now);

        if(newDate.isBefore(now)){
            System.out.println("true");
            angajatDAO.deleteAngajat(id);
            return true;
        }
        System.out.println("false");
        return false;
    }

    public void sendMail(String mail, String[] credentials){
        generateCredentials.sendMail(mail, credentials);
    }

    public static void main(String[] args) {
        GenerateCredentialsBLL generateCredentialsBLL = new GenerateCredentialsBLL();
        generateCredentialsBLL.createTableObjects();

    }

}
