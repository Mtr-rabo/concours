package org.ne.concours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Offre.
 */
@Entity
@Table(name = "offre")
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "nom_poste")
    private String nomPoste;

    @Column(name = "descrip")
    private String descrip;

    @Column(name = "date_ouverture")
    private Instant dateOuverture;

    @Column(name = "date_cloture")
    private Instant dateCloture;

    @Column(name = "is_archive")
    private Boolean isArchive;

    @Column(name = "date_concours")
    private Instant dateConcours;

    @Column(name = "age_limite")
    private Integer ageLimite;

    @ManyToOne
    @JsonIgnoreProperties(value = "offres", allowSetters = true)
    private MinisterConcerner ministerConcerner;

    @ManyToOne
    @JsonIgnoreProperties(value = "offres", allowSetters = true)
    private Categorie categrie;

    @ManyToOne
    @JsonIgnoreProperties(value = "offres", allowSetters = true)
    private Option option;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Offre code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNomPoste() {
        return nomPoste;
    }

    public Offre nomPoste(String nomPoste) {
        this.nomPoste = nomPoste;
        return this;
    }

    public void setNomPoste(String nomPoste) {
        this.nomPoste = nomPoste;
    }

    public String getDescrip() {
        return descrip;
    }

    public Offre descrip(String descrip) {
        this.descrip = descrip;
        return this;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Instant getDateOuverture() {
        return dateOuverture;
    }

    public Offre dateOuverture(Instant dateOuverture) {
        this.dateOuverture = dateOuverture;
        return this;
    }

    public void setDateOuverture(Instant dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Instant getDateCloture() {
        return dateCloture;
    }

    public Offre dateCloture(Instant dateCloture) {
        this.dateCloture = dateCloture;
        return this;
    }

    public void setDateCloture(Instant dateCloture) {
        this.dateCloture = dateCloture;
    }

    public Boolean isIsArchive() {
        return isArchive;
    }

    public Offre isArchive(Boolean isArchive) {
        this.isArchive = isArchive;
        return this;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Instant getDateConcours() {
        return dateConcours;
    }

    public Offre dateConcours(Instant dateConcours) {
        this.dateConcours = dateConcours;
        return this;
    }

    public void setDateConcours(Instant dateConcours) {
        this.dateConcours = dateConcours;
    }

    public Integer getAgeLimite() {
        return ageLimite;
    }

    public Offre ageLimite(Integer ageLimite) {
        this.ageLimite = ageLimite;
        return this;
    }

    public void setAgeLimite(Integer ageLimite) {
        this.ageLimite = ageLimite;
    }

    public MinisterConcerner getMinisterConcerner() {
        return ministerConcerner;
    }

    public Offre ministerConcerner(MinisterConcerner ministerConcerner) {
        this.ministerConcerner = ministerConcerner;
        return this;
    }

    public void setMinisterConcerner(MinisterConcerner ministerConcerner) {
        this.ministerConcerner = ministerConcerner;
    }

    public Categorie getCategrie() {
        return categrie;
    }

    public Offre categrie(Categorie categorie) {
        this.categrie = categorie;
        return this;
    }

    public void setCategrie(Categorie categorie) {
        this.categrie = categorie;
    }

    public Option getOption() {
        return option;
    }

    public Offre option(Option option) {
        this.option = option;
        return this;
    }

    public void setOption(Option option) {
        this.option = option;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offre)) {
            return false;
        }
        return id != null && id.equals(((Offre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offre{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nomPoste='" + getNomPoste() + "'" +
            ", descrip='" + getDescrip() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", isArchive='" + isIsArchive() + "'" +
            ", dateConcours='" + getDateConcours() + "'" +
            ", ageLimite=" + getAgeLimite() +
            "}";
    }
}
