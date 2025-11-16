package be.ucll.backend.campusapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Campus {
    @Id
    private String name;
    private String adres;
    private int parkeerplaatsen;
    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Lokaal> lokalen;

    public Campus() {
    }

    @PrePersist
    @PreUpdate
    public void normalize() {
        this.name = this.name.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getParkeerplaatsen() {
        return parkeerplaatsen;
    }

    public void setParkeerplaatsen(int parkeerplaatsen) {
        this.parkeerplaatsen = parkeerplaatsen;
    }

    public List<Lokaal> getLokalen() {
        return lokalen;
    }

    public void setLokalen(List<Lokaal> lokalen) {
        this.lokalen = lokalen;
    }
}
