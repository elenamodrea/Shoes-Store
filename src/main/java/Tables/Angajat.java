package Tables;

import javax.persistence.*;

@Entity
@Table(name = "angajat")
public class Angajat {
    @Id
    @Column(name = "idAngajat", nullable = false, unique = true)
    private String idAngajat;
    @Column(name = "salariu", nullable = false)
    private int salariu;
    @Column(name = "durataContract", nullable = false)
    private String durataContract;
    @Column(name = "ultimaPlatire", nullable = false)
    private String ultimaPlatire;
    @Column(name = "dataDeAngajare", nullable = false)
    private String dataDeAngajare;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUserFK")
    private User user;



    public Angajat() {
    }

    public Angajat(String idAngajat, int salariu, String durataContract, String ultimaPlatire, String dataDeAngajare, User user) {
        this.idAngajat = idAngajat;
        this.salariu = salariu;
        this.durataContract = durataContract;
        this.user = user;
        this.ultimaPlatire = ultimaPlatire;
        this.dataDeAngajare = dataDeAngajare;
    }

    public String getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(String idAngajat) {
        this.idAngajat = idAngajat;
    }


    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public String getDurataContract() {
        return durataContract;
    }

    public void setDurataContract(String durataContract) {
        this.durataContract = durataContract;
    }

    public User getUser() {
        return user;
    }

    public String getUltimaPlatire() {
        return ultimaPlatire;
    }

    public void setUltimaPlatire(String ultimaPlatire) {
        this.ultimaPlatire = ultimaPlatire;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDataDeAngajare() {
        return dataDeAngajare;
    }

    public void setDataDeAngajare(String dataDeAngajare) {
        this.dataDeAngajare = dataDeAngajare;
    }
}
