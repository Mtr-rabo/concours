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

/**
 * Criteria class for the {@link org.ne.concours.domain.EpreuveAconcourir} entity. This class is used
 * in {@link org.ne.concours.web.rest.EpreuveAconcourirResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /epreuve-aconcourirs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EpreuveAconcourirCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private IntegerFilter duree;

    private IntegerFilter coefficiant;

    private StringFilter noteEleminatoire;

    private LongFilter offreId;

    public EpreuveAconcourirCriteria() {
    }

    public EpreuveAconcourirCriteria(EpreuveAconcourirCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.duree = other.duree == null ? null : other.duree.copy();
        this.coefficiant = other.coefficiant == null ? null : other.coefficiant.copy();
        this.noteEleminatoire = other.noteEleminatoire == null ? null : other.noteEleminatoire.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
    }

    @Override
    public EpreuveAconcourirCriteria copy() {
        return new EpreuveAconcourirCriteria(this);
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

    public IntegerFilter getDuree() {
        return duree;
    }

    public void setDuree(IntegerFilter duree) {
        this.duree = duree;
    }

    public IntegerFilter getCoefficiant() {
        return coefficiant;
    }

    public void setCoefficiant(IntegerFilter coefficiant) {
        this.coefficiant = coefficiant;
    }

    public StringFilter getNoteEleminatoire() {
        return noteEleminatoire;
    }

    public void setNoteEleminatoire(StringFilter noteEleminatoire) {
        this.noteEleminatoire = noteEleminatoire;
    }

    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EpreuveAconcourirCriteria that = (EpreuveAconcourirCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(duree, that.duree) &&
            Objects.equals(coefficiant, that.coefficiant) &&
            Objects.equals(noteEleminatoire, that.noteEleminatoire) &&
            Objects.equals(offreId, that.offreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        duree,
        coefficiant,
        noteEleminatoire,
        offreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EpreuveAconcourirCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (duree != null ? "duree=" + duree + ", " : "") +
                (coefficiant != null ? "coefficiant=" + coefficiant + ", " : "") +
                (noteEleminatoire != null ? "noteEleminatoire=" + noteEleminatoire + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
            "}";
    }

}
