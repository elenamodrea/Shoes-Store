package Tables;

import javax.persistence.*;

@Entity
@Table(name = "adminA")
public class Admin {
    @Id
    @Column(name = "idAmin", nullable = false, unique = true)
    private String idAdmin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUserFK")
    private User user;
    public Admin() {
    }

    public Admin(String idAdmin, User user) {
        this.idAdmin = idAdmin;
        this.user = user;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
