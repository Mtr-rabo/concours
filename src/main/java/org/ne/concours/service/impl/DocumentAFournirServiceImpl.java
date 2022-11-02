package org.ne.concours.service.impl;

import org.ne.concours.service.DocumentAFournirService;
import org.ne.concours.domain.DocumentAFournir;
import org.ne.concours.repository.DocumentAFournirRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentAFournir}.
 */
@Service
@Transactional
public class DocumentAFournirServiceImpl implements DocumentAFournirService {

    private final Logger log = LoggerFactory.getLogger(DocumentAFournirServiceImpl.class);

    private final DocumentAFournirRepository documentAFournirRepository;

    public DocumentAFournirServiceImpl(DocumentAFournirRepository documentAFournirRepository) {
        this.documentAFournirRepository = documentAFournirRepository;
    }

    @Override
    public DocumentAFournir save(DocumentAFournir documentAFournir) {
        log.debug("Request to save DocumentAFournir : {}", documentAFournir);
        return documentAFournirRepository.save(documentAFournir);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentAFournir> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentAFournirs");
        return documentAFournirRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentAFournir> findOne(Long id) {
        log.debug("Request to get DocumentAFournir : {}", id);
        return documentAFournirRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentAFournir : {}", id);
        documentAFournirRepository.deleteById(id);
    }
}
