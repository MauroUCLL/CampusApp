package be.ucll.backend.campusapp.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String voorNaam;
    private String naam;
    private Date geboorteDatum;
    private String mail;
    @ManyToMany
    @JoinTable(
            name = "user_reservatie",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "reservatie_id")
    )
    private List<Reservatie> reservaties;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getVoorNaam() {
        return voorNaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Reservatie> getReservaties() {
        return reservaties;
    }

    public void setReservaties(List<Reservatie> reservaties) {
        this.reservaties = reservaties;
    }
}
