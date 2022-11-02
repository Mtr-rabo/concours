package org.ne.concours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "fichier")
public class Fichier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom_fichier")
    private String nomFichier;

    @Lob
    @Column(name = "fich")
    private byte[] fich;

    @Column(name = "fich_content_type")
    private String fichContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "fichiers", allowSetters = true)
    private Depot depot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public Fichier nomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
        return this;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public byte[] getFich() {
        return fich;
    }

    public Fichier fich(byte[] fich) {
        this.fich = fich;
        return this;
    }

    public void setFich(byte[] fich) {
        this.fich = fich;
    }

    public String getFichContentType() {
        return fichContentType;
    }

    public Fichier fichContentType(String fichContentType) {
        this.fichContentType = fichContentType;
        return this;
    }

    public void setFichContentType(String fichContentType) {
        this.fichContentType = fichContentType;
    }

    public Depot getDepot() {
        return depot;
    }

    public Fichier depot(Depot depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fichier)) {
            return false;
        }
        return id != null && id.equals(((Fichier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fichier{" +
            "id=" + getId() +
            ", nomFichier='" + getNomFichier() + "'" +
            ", fich='" + getFich() + "'" +
            ", fichContentType='" + getFichContentType() + "'" +
            "}";
    }
}
