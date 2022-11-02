package org.ne.concours.service.impl;

import org.ne.concours.service.OffreService;
import org.ne.concours.domain.Offre;
import org.ne.concours.repository.OffreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Offre}.
 */
@Service
@Transactional
public class OffreServiceImpl implements OffreService {

    private final Logger log = LoggerFactory.getLogger(OffreServiceImpl.class);

    private final OffreRepository offreRepository;

    public OffreServiceImpl(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    @Override
    public Offre save(Offre offre) {
        log.debug("Request to save Offre : {}", offre);
        return offreRepository.save(offre);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offre> findAll(Pageable pageable) {
        log.debug("Request to get all Offres");
        return offreRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Offre> findOne(Long id) {
        log.debug("Request to get Offre : {}", id);
        return offreRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offre : {}", id);
        offreRepository.deleteById(id);
    }
}
