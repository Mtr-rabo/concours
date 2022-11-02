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
 * Criteria class for the {@link org.ne.concours.domain.Depot} entity. This class is used
 * in {@link org.ne.concours.web.rest.DepotResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /depots?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepotCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dateDepot;

    private BooleanFilter isArchive;

    private BooleanFilter isAcepte;

    private LongFilter offreId;

    private LongFilter candidatId;

    public DepotCriteria() {
    }

    public DepotCriteria(DepotCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateDepot = other.dateDepot == null ? null : other.dateDepot.copy();
        this.isArchive = other.isArchive == null ? null : other.isArchive.copy();
        this.isAcepte = other.isAcepte == null ? null : other.isAcepte.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.candidatId = other.candidatId == null ? null : other.candidatId.copy();
    }

    @Override
    public DepotCriteria copy() {
        return new DepotCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(InstantFilter dateDepot) {
        this.dateDepot = dateDepot;
    }

    public BooleanFilter getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(BooleanFilter isArchive) {
        this.isArchive = isArchive;
    }

    public BooleanFilter getIsAcepte() {
        return isAcepte;
    }

    public void setIsAcepte(BooleanFilter isAcepte) {
        this.isAcepte = isAcepte;
    }

    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }

    public LongFilter getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(LongFilter candidatId) {
        this.candidatId = candidatId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepotCriteria that = (DepotCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateDepot, that.dateDepot) &&
            Objects.equals(isArchive, that.isArchive) &&
            Objects.equals(isAcepte, that.isAcepte) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(candidatId, that.candidatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateDepot,
        isArchive,
        isAcepte,
        offreId,
        candidatId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepotCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateDepot != null ? "dateDepot=" + dateDepot + ", " : "") +
                (isArchive != null ? "isArchive=" + isArchive + ", " : "") +
                (isAcepte != null ? "isAcepte=" + isAcepte + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
                (candidatId != null ? "candidatId=" + candidatId + ", " : "") +
            "}";
    }

}
