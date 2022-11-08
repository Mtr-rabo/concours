package org.ne.concours.service.impl;

import org.ne.concours.service.OffreDocumentService;
import org.ne.concours.domain.OffreDocument;
import org.ne.concours.repository.OffreDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OffreDocument}.
 */
@Service
@Transactional
public class OffreDocumentServiceImpl implements OffreDocumentService {

    private final Logger log = LoggerFactory.getLogger(OffreDocumentServiceImpl.class);

    private final OffreDocumentRepository offreDocumentRepository;

    public OffreDocumentServiceImpl(OffreDocumentRepository offreDocumentRepository) {
        this.offreDocumentRepository = offreDocumentRepository;
    }

    @Override
    public OffreDocument save(OffreDocument offreDocument) {
        log.debug("Request to save OffreDocument : {}", offreDocument);
        return offreDocumentRepository.save(offreDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OffreDocument> findAll() {
        log.debug("Request to get all OffreDocuments");
        return offreDocumentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OffreDocument> findByOffre(Long id) {
        log.debug("Request to get all OffreDocuments");
        return offreDocumentRepository.findByOffreId(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OffreDocument> findOne(Long id) {
        log.debug("Request to get OffreDocument : {}", id);
        return offreDocumentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OffreDocument : {}", id);
        offreDocumentRepository.deleteById(id);
    }
}
