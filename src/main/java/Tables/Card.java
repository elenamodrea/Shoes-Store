package Tables;

import javax.persistence.*;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @Column(name = "idCard", nullable = false, unique = true)
    private String idCard;
    @Column(name = "nrCard", nullable = false)
    private String nrCard;
    @Column(name = "CVV", nullable = false)
    private int CVV;
    @Column(name = "nume", nullable = false)
    private String nume;
    @Column(name = "dataExpirare", nullable = false)
    private String dataExpirare;
    @ManyToOne
    @JoinColumn(name="idClientF")
    private Client client;
    public Card() {
    }

    public Card(String idCard, String nrCard, int CVV, String nume, String dataExpirare) {
        this.idCard = idCard;
        this.nrCard = nrCard;
        this.CVV = CVV;
        this.nume = nume;
        this.dataExpirare = dataExpirare;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNrCard() {
        return nrCard;
    }

    public void setNrCard(String nrCard) {
        this.nrCard = nrCard;
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(String dataExpirare) {
        this.dataExpirare = dataExpirare;
    }
}
