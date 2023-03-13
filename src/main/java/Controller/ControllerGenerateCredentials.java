package Controller;
import Business.GenerateCredentialsBLL;
import Tables.Angajat;
import Tables.User;
import java.util.List;

public class ControllerGenerateCredentials {
    private GenerateCredentialsBLL generateCredentialsBLL;
    private List<Angajat> angajats;

    public ControllerGenerateCredentials() {

        this.generateCredentialsBLL = new GenerateCredentialsBLL();
    }

    public List<Angajat> generateDataTable(){
        angajats = generateCredentialsBLL.createTableObjects();
    return angajats;
    }

    public String handleReq(List<Angajat> angajats){
        StringBuilder string = new StringBuilder();
        for (Angajat a : angajats) {
            string.append(a.getIdAngajat()).append(",");
        }
        System.out.println(string);
        return string.toString();
    }

    public User convertUser(String[] req){
        return generateCredentialsBLL.convertUser(req);
    }

    public String generateCredentials(User user, int salariu, String durata, String ultimaPlatire, String dataDeAngajare){
        String[] strings = generateCredentialsBLL.generateCredentials(user, salariu, durata, ultimaPlatire, dataDeAngajare);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(strings[0]).append(",").append(strings[1]);
        return stringBuilder.toString();
    }

    public String convertMail(String id){
        return generateCredentialsBLL.convertMail(id);
    }

    public void sendMail(String mail, String[] credentials){
        generateCredentialsBLL.sendMail(mail, credentials);
    }
}
