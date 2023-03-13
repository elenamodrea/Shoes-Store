package Tables;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comanda")
public class Comanda {
    @Id
    @Column(name = "idComanda", nullable = false, unique = true)
    private String idComanda;
    @Column(name = "dataC", nullable = false)
    private String data;
    @Column(name = "modalitatePlata", nullable = false)
    private String modalitatePlata;
    @Column(name = "pretComanda", nullable = false)
    private String pretComanda;
    @Column(name = "nrCard", nullable = false)
    private String nrCard;
    @ManyToOne
    @JoinColumn(name="idClientF")
    private Client client;
    @ManyToMany(cascade ={CascadeType.ALL})
    @JoinTable(
            name="Comanda_Produs",
            joinColumns = {@JoinColumn(name = "comanda_id")},
            inverseJoinColumns = {@JoinColumn(name="produs_id")}
    )
    private List<Produs> produse;
    /* @OneToOne(mappedBy = "comanda")
     private Retur retur;*/
    public Comanda() {
    }

    public Comanda(String idComanda, String data, String modalitatePlata, String pretComanda, String nrCard) {
        this.idComanda = idComanda;
        this.data = data;
        this.modalitatePlata = modalitatePlata;
        this.pretComanda = pretComanda;
        this.nrCard = nrCard;
    }

    public String getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getModalitatePlata() {
        return modalitatePlata;
    }

    public void setModalitatePlata(String modalitatePlata) {
        this.modalitatePlata = modalitatePlata;
    }

    public String getPretComanda() {
        return pretComanda;
    }

    public void setPretComanda(String pretComanda) {
        this.pretComanda = pretComanda;
    }

    public String getNrCard() {
        return nrCard;
    }

    public void setNrCard(String nrCard) {
        this.nrCard = nrCard;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public void setProduse(List<Produs> produse) {
        this.produse = produse;
    }
}
