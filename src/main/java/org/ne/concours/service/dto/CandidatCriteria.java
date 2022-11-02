package org.ne.concours.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link org.ne.concours.domain.Candidat} entity. This class is used
 * in {@link org.ne.concours.web.rest.CandidatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /candidats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CandidatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter telephone;

    private InstantFilter dateNaissance;

    private StringFilter lieuNaissance;

    private IntegerFilter age;

    private StringFilter adresse;

    public CandidatCriteria() {
    }

    public CandidatCriteria(CandidatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.dateNaissance = other.dateNaissance == null ? null : other.dateNaissance.copy();
        this.lieuNaissance = other.lieuNaissance == null ? null : other.lieuNaissance.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
    }

    @Override
    public CandidatCriteria copy() {
        return new CandidatCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public InstantFilter getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(InstantFilter dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public StringFilter getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(StringFilter lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CandidatCriteria that = (CandidatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(dateNaissance, that.dateNaissance) &&
            Objects.equals(lieuNaissance, that.lieuNaissance) &&
            Objects.equals(age, that.age) &&
            Objects.equals(adresse, that.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        prenom,
        telephone,
        dateNaissance,
        lieuNaissance,
        age,
        adresse
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (dateNaissance != null ? "dateNaissance=" + dateNaissance + ", " : "") +
                (lieuNaissance != null ? "lieuNaissance=" + lieuNaissance + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
            "}";
    }

}
