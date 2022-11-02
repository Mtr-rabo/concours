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
 * Criteria class for the {@link org.ne.concours.domain.Offre} entity. This class is used
 * in {@link org.ne.concours.web.rest.OffreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OffreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter nomPoste;

    private StringFilter descrip;

    private InstantFilter dateOuverture;

    private InstantFilter dateCloture;

    private BooleanFilter isArchive;

    private InstantFilter dateConcours;

    private IntegerFilter ageLimite;

    private LongFilter ministerConcernerId;

    private LongFilter categrieId;

    private LongFilter optionId;

    public OffreCriteria() {
    }

    public OffreCriteria(OffreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.nomPoste = other.nomPoste == null ? null : other.nomPoste.copy();
        this.descrip = other.descrip == null ? null : other.descrip.copy();
        this.dateOuverture = other.dateOuverture == null ? null : other.dateOuverture.copy();
        this.dateCloture = other.dateCloture == null ? null : other.dateCloture.copy();
        this.isArchive = other.isArchive == null ? null : other.isArchive.copy();
        this.dateConcours = other.dateConcours == null ? null : other.dateConcours.copy();
        this.ageLimite = other.ageLimite == null ? null : other.ageLimite.copy();
        this.ministerConcernerId = other.ministerConcernerId == null ? null : other.ministerConcernerId.copy();
        this.categrieId = other.categrieId == null ? null : other.categrieId.copy();
        this.optionId = other.optionId == null ? null : other.optionId.copy();
    }

    @Override
    public OffreCriteria copy() {
        return new OffreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getNomPoste() {
        return nomPoste;
    }

    public void setNomPoste(StringFilter nomPoste) {
        this.nomPoste = nomPoste;
    }

    public StringFilter getDescrip() {
        return descrip;
    }

    public void setDescrip(StringFilter descrip) {
        this.descrip = descrip;
    }

    public InstantFilter getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(InstantFilter dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public InstantFilter getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(InstantFilter dateCloture) {
        this.dateCloture = dateCloture;
    }

    public BooleanFilter getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(BooleanFilter isArchive) {
        this.isArchive = isArchive;
    }

    public InstantFilter getDateConcours() {
        return dateConcours;
    }

    public void setDateConcours(InstantFilter dateConcours) {
        this.dateConcours = dateConcours;
    }

    public IntegerFilter getAgeLimite() {
        return ageLimite;
    }

    public void setAgeLimite(IntegerFilter ageLimite) {
        this.ageLimite = ageLimite;
    }

    public LongFilter getMinisterConcernerId() {
        return ministerConcernerId;
    }

    public void setMinisterConcernerId(LongFilter ministerConcernerId) {
        this.ministerConcernerId = ministerConcernerId;
    }

    public LongFilter getCategrieId() {
        return categrieId;
    }

    public void setCategrieId(LongFilter categrieId) {
        this.categrieId = categrieId;
    }

    public LongFilter getOptionId() {
        return optionId;
    }

    public void setOptionId(LongFilter optionId) {
        this.optionId = optionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OffreCriteria that = (OffreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(nomPoste, that.nomPoste) &&
            Objects.equals(descrip, that.descrip) &&
            Objects.equals(dateOuverture, that.dateOuverture) &&
            Objects.equals(dateCloture, that.dateCloture) &&
            Objects.equals(isArchive, that.isArchive) &&
            Objects.equals(dateConcours, that.dateConcours) &&
            Objects.equals(ageLimite, that.ageLimite) &&
            Objects.equals(ministerConcernerId, that.ministerConcernerId) &&
            Objects.equals(categrieId, that.categrieId) &&
            Objects.equals(optionId, that.optionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        nomPoste,
        descrip,
        dateOuverture,
        dateCloture,
        isArchive,
        dateConcours,
        ageLimite,
        ministerConcernerId,
        categrieId,
        optionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (nomPoste != null ? "nomPoste=" + nomPoste + ", " : "") +
                (descrip != null ? "descrip=" + descrip + ", " : "") +
                (dateOuverture != null ? "dateOuverture=" + dateOuverture + ", " : "") +
                (dateCloture != null ? "dateCloture=" + dateCloture + ", " : "") +
                (isArchive != null ? "isArchive=" + isArchive + ", " : "") +
                (dateConcours != null ? "dateConcours=" + dateConcours + ", " : "") +
                (ageLimite != null ? "ageLimite=" + ageLimite + ", " : "") +
                (ministerConcernerId != null ? "ministerConcernerId=" + ministerConcernerId + ", " : "") +
                (categrieId != null ? "categrieId=" + categrieId + ", " : "") +
                (optionId != null ? "optionId=" + optionId + ", " : "") +
            "}";
    }

}
