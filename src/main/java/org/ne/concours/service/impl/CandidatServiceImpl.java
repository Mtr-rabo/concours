package org.ne.concours.service.impl;

import org.ne.concours.service.CandidatService;
import org.ne.concours.domain.Candidat;
import org.ne.concours.repository.CandidatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Candidat}.
 */
@Service
@Transactional
public class CandidatServiceImpl implements CandidatService {

    private final Logger log = LoggerFactory.getLogger(CandidatServiceImpl.class);

    private final CandidatRepository candidatRepository;

    public CandidatServiceImpl(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Candidat save(Candidat candidat) {
        log.debug("Request to save Candidat : {}", candidat);
        return candidatRepository.save(candidat);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Candidat> findAll(Pageable pageable) {
        log.debug("Request to get all Candidats");
        return candidatRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Candidat> findOne(Long id) {
        log.debug("Request to get Candidat : {}", id);
        return candidatRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidat : {}", id);
        candidatRepository.deleteById(id);
    }
}
