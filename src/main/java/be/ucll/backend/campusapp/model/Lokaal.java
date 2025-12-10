package be.ucll.backend.campusapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "lokaal",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "campus_name"})
)
public class Lokaal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String typeLokaal;

    private int aantalPersonen;

    private int verdieping;

    @ManyToOne
    @JoinColumn(name = "campus_name")
    @JsonBackReference
    private Campus campus;

    @ManyToMany
    @JoinTable(
            name = "lokaal_reservatie",
            joinColumns = @JoinColumn(name = "lokaal_id"),
            inverseJoinColumns = @JoinColumn(name = "reservatie_id")
    )
    @JsonManagedReference
    private List<Reservatie> reservaties;

    public Lokaal() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeLokaal() {
        return typeLokaal;
    }

    public void setTypeLokaal(String typeLokaal) {
        this.typeLokaal = typeLokaal;
    }

    public int getAantalPersonen() {
        return aantalPersonen;
    }

    public void setAantalPersonen(int aantalPersonen) {
        this.aantalPersonen = aantalPersonen;
    }

    public int getVerdieping() {
        return verdieping;
    }

    public void setVerdieping(int verdieping) {
        this.verdieping = verdieping;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public List<Reservatie> getReservaties() {
        return reservaties;
    }

    public void setReservaties(List<Reservatie> reservaties) {
        this.reservaties = reservaties;
    }
}
