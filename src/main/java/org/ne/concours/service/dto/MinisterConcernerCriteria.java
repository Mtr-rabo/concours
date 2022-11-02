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
 * Criteria class for the {@link org.ne.concours.domain.MinisterConcerner} entity. This class is used
 * in {@link org.ne.concours.web.rest.MinisterConcernerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /minister-concerners?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MinisterConcernerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter domaine;

    private StringFilter description;

    public MinisterConcernerCriteria() {
    }

    public MinisterConcernerCriteria(MinisterConcernerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.domaine = other.domaine == null ? null : other.domaine.copy();
        this.description = other.description == null ? null : other.description.copy();
    }

    @Override
    public MinisterConcernerCriteria copy() {
        return new MinisterConcernerCriteria(this);
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

    public StringFilter getDomaine() {
        return domaine;
    }

    public void setDomaine(StringFilter domaine) {
        this.domaine = domaine;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MinisterConcernerCriteria that = (MinisterConcernerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(domaine, that.domaine) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        domaine,
        description
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MinisterConcernerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (domaine != null ? "domaine=" + domaine + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
            "}";
    }

}
