package org.ne.concours.service.impl;

import org.ne.concours.service.EpreuveAconcourirService;
import org.ne.concours.domain.EpreuveAconcourir;
import org.ne.concours.repository.EpreuveAconcourirRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EpreuveAconcourir}.
 */
@Service
@Transactional
public class EpreuveAconcourirServiceImpl implements EpreuveAconcourirService {

    private final Logger log = LoggerFactory.getLogger(EpreuveAconcourirServiceImpl.class);

    private final EpreuveAconcourirRepository epreuveAconcourirRepository;

    public EpreuveAconcourirServiceImpl(EpreuveAconcourirRepository epreuveAconcourirRepository) {
        this.epreuveAconcourirRepository = epreuveAconcourirRepository;
    }

    @Override
    public EpreuveAconcourir save(EpreuveAconcourir epreuveAconcourir) {
        log.debug("Request to save EpreuveAconcourir : {}", epreuveAconcourir);
        return epreuveAconcourirRepository.save(epreuveAconcourir);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EpreuveAconcourir> findAll(Pageable pageable) {
        log.debug("Request to get all EpreuveAconcourirs");
        return epreuveAconcourirRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EpreuveAconcourir> findOne(Long id) {
        log.debug("Request to get EpreuveAconcourir : {}", id);
        return epreuveAconcourirRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EpreuveAconcourir : {}", id);
        epreuveAconcourirRepository.deleteById(id);
    }
}
