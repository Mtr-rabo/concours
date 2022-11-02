package org.ne.concours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EpreuveAconcourir.
 */
@Entity
@Table(name = "epreuve_aconcourir")
public class EpreuveAconcourir implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "coefficiant")
    private Integer coefficiant;

    @Column(name = "note_eleminatoire")
    private String noteEleminatoire;

    @ManyToOne
    @JsonIgnoreProperties(value = "epreuveAconcourirs", allowSetters = true)
    private Offre offre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public EpreuveAconcourir nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getDuree() {
        return duree;
    }

    public EpreuveAconcourir duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Integer getCoefficiant() {
        return coefficiant;
    }

    public EpreuveAconcourir coefficiant(Integer coefficiant) {
        this.coefficiant = coefficiant;
        return this;
    }

    public void setCoefficiant(Integer coefficiant) {
        this.coefficiant = coefficiant;
    }

    public String getNoteEleminatoire() {
        return noteEleminatoire;
    }

    public EpreuveAconcourir noteEleminatoire(String noteEleminatoire) {
        this.noteEleminatoire = noteEleminatoire;
        return this;
    }

    public void setNoteEleminatoire(String noteEleminatoire) {
        this.noteEleminatoire = noteEleminatoire;
    }

    public Offre getOffre() {
        return offre;
    }

    public EpreuveAconcourir offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EpreuveAconcourir)) {
            return false;
        }
        return id != null && id.equals(((EpreuveAconcourir) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EpreuveAconcourir{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", duree=" + getDuree() +
            ", coefficiant=" + getCoefficiant() +
            ", noteEleminatoire='" + getNoteEleminatoire() + "'" +
            "}";
    }
}
