package org.ne.concours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Depot.
 */
@Entity
@Table(name = "depot")
public class Depot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_depot")
    private Instant dateDepot;

    @Column(name = "is_archive")
    private Boolean isArchive;

    @Column(name = "is_acepte")
    private Boolean isAcepte;

    @ManyToOne
    @JsonIgnoreProperties(value = "depots", allowSetters = true)
    private Offre offre;

    @ManyToOne
    @JsonIgnoreProperties(value = "depots", allowSetters = true)
    private Candidat candidat;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDepot() {
        return dateDepot;
    }

    public Depot dateDepot(Instant dateDepot) {
        this.dateDepot = dateDepot;
        return this;
    }

    public void setDateDepot(Instant dateDepot) {
        this.dateDepot = dateDepot;
    }

    public Boolean isIsArchive() {
        return isArchive;
    }

    public Depot isArchive(Boolean isArchive) {
        this.isArchive = isArchive;
        return this;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Boolean isIsAcepte() {
        return isAcepte;
    }

    public Depot isAcepte(Boolean isAcepte) {
        this.isAcepte = isAcepte;
        return this;
    }

    public void setIsAcepte(Boolean isAcepte) {
        this.isAcepte = isAcepte;
    }

    public Offre getOffre() {
        return offre;
    }

    public Depot offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Depot candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depot)) {
            return false;
        }
        return id != null && id.equals(((Depot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Depot{" +
            "id=" + getId() +
            ", dateDepot='" + getDateDepot() + "'" +
            ", isArchive='" + isIsArchive() + "'" +
            ", isAcepte='" + isIsAcepte() + "'" +
            "}";
    }
}
