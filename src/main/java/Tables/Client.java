package Tables;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ClientC")
public class Client {
    @Id
    @Column(name = "idClient", nullable = false, unique = true)
    private String idClient;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUserFK")
    private User user;
    @OneToMany(targetEntity = Card.class, mappedBy = "client",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Card> cards;
    @OneToMany(targetEntity = Retur.class, mappedBy = "client",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Retur> returs;
    @OneToMany(targetEntity = Review.class, mappedBy = "client",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Review> reviews;
    @OneToMany(targetEntity = Comanda.class, mappedBy = "client",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comanda> comands;

    public Client(String idClient, User user) {
        this.idClient = idClient;
        this.user = user;
        /*this.cards = cards;
        this.returs = returs;
        this.reviews = reviews;
        this.comands = comands;*/
    }

    public User getUser() {
        return user;
    }

    public void setUserA(User user) {
        this.user = user;
    }

    public String getIdClient() {
        return idClient;
    }

    public Client() {
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Retur> getReturs() {
        return returs;
    }

    public void setReturs(List<Retur> returs) {
        this.returs = returs;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Comanda> getComands() {
        return comands;
    }

    public void setComands(List<Comanda> comands) {
        this.comands = comands;
    }
    public void addCard(Card card){
        cards.add(card);
    }
}
