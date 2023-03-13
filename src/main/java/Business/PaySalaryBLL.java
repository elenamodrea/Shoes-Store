package Business;

import Ajutor.PaySalary;
import Ajutor.WriteReadFile;
import DAO.AngajatDAO;
import Tables.Angajat;
import Tables.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaySalaryBLL{
    private AngajatDAO angajatDAO;
    private DateTimeFormatter dateFormat;
    private Month month;
    private LocalDate date;
    private LocalDate now;
    private Month currentMonth;
    private PaySalary paySalary;
    private Angajat angajat;
    private List<Angajat> angajats;
    private Date date1;
    private SimpleDateFormat format;
    private DateTimeFormatter simpleDateFormat;
    private LocalDate localDate;
    public PaySalaryBLL() {
        this.angajatDAO = new AngajatDAO();
        this.paySalary = new PaySalary();
        this.angajat = new Angajat();
        this.angajats = new ArrayList<>();
    }

    public PaySalaryBLL(AngajatDAO angajatDAO) {
        this.angajatDAO = angajatDAO;
        this.paySalary = new PaySalary();
        this.angajat = new Angajat();
        this.angajats = new ArrayList<>();
    }

    public List<Angajat> generateAngajats(){
        now = LocalDate.now();
        currentMonth = now.getMonth();
        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        angajats = angajatDAO.findAllAngajat();
        List<Angajat> angajatList = new ArrayList<>();

        for(Angajat a: angajats){
            date = LocalDate.parse(a.getUltimaPlatire(), dateFormat);
            month = date.getMonth();
            if(month != currentMonth){
                if(!checkAngajatContractBefore(a.getIdAngajat())) {
                    angajatList.add(a);
                }
            }
        }
        for(Angajat a: angajatList){
            System.out.println(a.getIdAngajat() +  " " + a.getUltimaPlatire());
        }
        return angajatList;
    }

    public void sendMail(Angajat angajat){
        paySalary.sendMail(angajat);
    }

    public Angajat HandlerReq(String[] strings){
        User user = new User(strings[5], strings[6], strings[7], strings[10], strings[9], strings[8], strings[11], strings[12], strings[13]);
        angajat = new Angajat(strings[1], Integer.parseInt(strings[2]), strings[3], strings[4], strings[5], user);
        return angajat;
    }

    public Angajat getResponse(String[] strings){
        angajat = angajatDAO.getAngajat(strings[0]);
        format = new SimpleDateFormat("dd/MM/yyyy");
        date1 = new Date();
        angajat.setUltimaPlatire(format.format(date1));
        angajatDAO.updateAngajat(angajat);
        return angajat;
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
        if(newDate.isBefore(now)){
            angajatDAO.deleteAngajat(id);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        PaySalaryBLL paySalaryBLL = new PaySalaryBLL();
        paySalaryBLL.generateAngajats();
    }
}
