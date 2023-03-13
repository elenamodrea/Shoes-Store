package Controller;
import Business.PaySalaryBLL;
import Tables.Angajat;
import Tables.User;

import java.util.List;

public class ControllerPaySalary {
    private PaySalaryBLL paySalaryBLL;


    public ControllerPaySalary() {
        this.paySalaryBLL = new PaySalaryBLL();
    }
    public List<Angajat> generateAngajats(){
        List<Angajat> angajats = paySalaryBLL.generateAngajats();
        for(Angajat a : angajats) {
            System.out.println("controller " + a.getIdAngajat());
        }
        return angajats;
    }
    public String handleReq(List<Angajat> angajats){
        StringBuilder string = new StringBuilder();
        for (Angajat a : angajats) {
            string.append(a.getIdAngajat()).append(",");
            System.out.println("req " + a.getIdAngajat());
        }
        return string.toString();
    }

    public void sendMail(Angajat angajat){
        paySalaryBLL.sendMail(angajat);
    }

    public Angajat handleReq(String[] strings){
        return paySalaryBLL.HandlerReq(strings);
    }

    public Angajat getResponse(String[] strings){
        return paySalaryBLL.getResponse(strings);
    }
}
