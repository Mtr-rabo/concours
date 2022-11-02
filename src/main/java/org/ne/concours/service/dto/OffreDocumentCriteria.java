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
 * Criteria class for the {@link org.ne.concours.domain.OffreDocument} entity. This class is used
 * in {@link org.ne.concours.web.rest.OffreDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offre-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OffreDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter offreId;

    private LongFilter documentAFournirId;

    public OffreDocumentCriteria() {
    }

    public OffreDocumentCriteria(OffreDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.documentAFournirId = other.documentAFournirId == null ? null : other.documentAFournirId.copy();
    }

    @Override
    public OffreDocumentCriteria copy() {
        return new OffreDocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }

    public LongFilter getDocumentAFournirId() {
        return documentAFournirId;
    }

    public void setDocumentAFournirId(LongFilter documentAFournirId) {
        this.documentAFournirId = documentAFournirId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OffreDocumentCriteria that = (OffreDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(documentAFournirId, that.documentAFournirId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        offreId,
        documentAFournirId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
                (documentAFournirId != null ? "documentAFournirId=" + documentAFournirId + ", " : "") +
            "}";
    }

}
