package Tables;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produs")
public class Produs {
    @Id
    @Column(name = "idProdus", nullable = false, unique = true)
    private String idProdus;
    @Column(name = "culoare", nullable = false)
    private String culoare;
    @Column(name = "marime", nullable = false)
    private String marime;
    @Column(name = "pret", nullable = false)
    private String pret;
    @Column(name = "stoc", nullable = false)
    private int stoc;
    @Column(name = "tipProdus", nullable = false)
    private String tipProdus;

    @Column(name = "categorie", nullable = false)
    private String categorie;
    @Column(name ="rating", nullable = false)
    private String rating;
    @ManyToMany(mappedBy = "produse")
    private List<Comanda> comenzi;
    @OneToMany(targetEntity = Review.class, mappedBy = "produs",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Review> reviews;
    public Produs() {
    }

    public Produs(String idProdus, String culoare, String marime, String pret, int stoc, String tipProdus, String categorie,String rating) {
        this.idProdus = idProdus;
        this.culoare = culoare;
        this.marime = marime;
        this.pret = pret;
        this.stoc = stoc;
        this.tipProdus = tipProdus;
        this.categorie = categorie;
        this.rating=rating;
    }

    public String getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }


    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public String getMarime() {
        return marime;
    }

    public void setMarime(String marime) {
        this.marime = marime;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public String getTipProdus() {
        return tipProdus;
    }

    public void setTipProdus(String tipProdus) {
        this.tipProdus = tipProdus;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;

    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Comanda> getComenzi() {
        return comenzi;
    }

    public void setComenzi(List<Comanda> comenzi) {
        this.comenzi = comenzi;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
