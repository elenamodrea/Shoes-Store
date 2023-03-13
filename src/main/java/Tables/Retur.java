package Tables;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "retur")
public class Retur {
    @Id
    @Column(name = "idRetur", nullable = false, unique = true)
    private String idRetur;
    @Column(name = "dataRetur", nullable = false)
    private String dataRetur;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idComandaFK")
    private Comanda comanda;
    @ManyToOne
    @JoinColumn(name="idClientF")
    private Client client;

    public Retur() {
    }

    public Retur(String idRetur, String dataRetur,Comanda comanda) {
        this.idRetur = idRetur;
        this.dataRetur = dataRetur;
        this.comanda=comanda;
    }

    public Retur(String idRetur, String dataRetur,Comanda comanda,Client client) {
        this.idRetur = idRetur;
        this.dataRetur = dataRetur;
        this.comanda=comanda;
        this.client=client;
    }

    public String getIdRetur() {
        return idRetur;
    }

    public void setIdRetur(String idRetur) {
        this.idRetur = idRetur;
    }

    public String getDataRetur() {
        return dataRetur;
    }

    public void setDataRetur(String dataRetur) {
        this.dataRetur = dataRetur;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}