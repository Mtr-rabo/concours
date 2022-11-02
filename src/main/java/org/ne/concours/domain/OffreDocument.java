package org.ne.concours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OffreDocument.
 */
@Entity
@Table(name = "offre_document")
public class OffreDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "offreDocuments", allowSetters = true)
    private Offre offre;

    @ManyToOne
    @JsonIgnoreProperties(value = "offreDocuments", allowSetters = true)
    private DocumentAFournir documentAFournir;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offre getOffre() {
        return offre;
    }

    public OffreDocument offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public DocumentAFournir getDocumentAFournir() {
        return documentAFournir;
    }

    public OffreDocument documentAFournir(DocumentAFournir documentAFournir) {
        this.documentAFournir = documentAFournir;
        return this;
    }

    public void setDocumentAFournir(DocumentAFournir documentAFournir) {
        this.documentAFournir = documentAFournir;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffreDocument)) {
            return false;
        }
        return id != null && id.equals(((OffreDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreDocument{" +
            "id=" + getId() +
            "}";
    }
}
