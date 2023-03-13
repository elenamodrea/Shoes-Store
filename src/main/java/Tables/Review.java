package Tables;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "idReview", nullable = false, unique = true)
    private String idReview;
    @Column(name = "mesaj", nullable = false)
    private String mesaj;
    @Column(name = "rating", nullable = false)
    private String rating;
    @ManyToOne
    @JoinColumn(name="idClientF")
    private Client client;
    @ManyToOne
    @JoinColumn(name="idProdusF")
    Produs produs;
    public Review() {
    }

    public Review(String idReview, String mesaj, String rating) {
        this.idReview = idReview;
        this.mesaj = mesaj;
        this.rating = rating;
    }

    public Review(String idReview, String mesaj, String rating,Client client,Produs produs){
        this.idReview = idReview;
        this.mesaj = mesaj;
        this.rating = rating;
        this.client=client;
        this.produs=produs;
    }

    public String getIdReview() {
        return idReview;
    }

    public void setIdReview(String idReview) {
        this.idReview = idReview;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }
}
