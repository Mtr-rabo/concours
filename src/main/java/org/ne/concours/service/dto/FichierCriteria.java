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
 * Criteria class for the {@link org.ne.concours.domain.Fichier} entity. This class is used
 * in {@link org.ne.concours.web.rest.FichierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fichiers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FichierCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomFichier;

    private LongFilter depotId;

    public FichierCriteria() {
    }

    public FichierCriteria(FichierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomFichier = other.nomFichier == null ? null : other.nomFichier.copy();
        this.depotId = other.depotId == null ? null : other.depotId.copy();
    }

    @Override
    public FichierCriteria copy() {
        return new FichierCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(StringFilter nomFichier) {
        this.nomFichier = nomFichier;
    }

    public LongFilter getDepotId() {
        return depotId;
    }

    public void setDepotId(LongFilter depotId) {
        this.depotId = depotId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FichierCriteria that = (FichierCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomFichier, that.nomFichier) &&
            Objects.equals(depotId, that.depotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomFichier,
        depotId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FichierCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomFichier != null ? "nomFichier=" + nomFichier + ", " : "") +
                (depotId != null ? "depotId=" + depotId + ", " : "") +
            "}";
    }

}
