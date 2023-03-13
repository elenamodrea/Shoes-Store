package Tables;

import javax.persistence.*;

@Entity
@Table(name = "UserP")
public class User {
    @Id
    @Column(name = "idUser", nullable = false, unique = true)
    private String idUser;
    @Column(name = "nume", nullable = false)
    private String nume;
    @Column(name = "prenume", nullable = false)
    private String prenume;
    @Column(name = "telefon", nullable = false)
    private String telefon;
    @Column(name = "adresa", nullable = false)
    private String adresa;
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "tipUser", nullable = false)
    private String tipUser;
    @Column(name = "userName", nullable = false, unique = true)
    private String username;
    @Column(name = "parola", nullable = false)
    private String parola;
    /*  @OneToOne(mappedBy = "user")
      private Admin admin;
      @OneToOne(mappedBy = "user")
      private Angajat angajat;
      @OneToOne(mappedBy = "user")
      private Client client;
      */
    public User() {
    }

    public User(String idUser, String nume, String prenume, String telefon, String adresa, String mail, String tipUser, String username, String parola) {
        this.idUser = idUser;
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.adresa = adresa;
        this.mail = mail;
        this.tipUser = tipUser;
        this.username = username;
        this.parola = parola;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTipUser() {
        return tipUser;
    }

    public void setTipUser(String tipUser) {
        this.tipUser = tipUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

}