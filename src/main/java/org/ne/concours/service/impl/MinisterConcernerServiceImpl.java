package org.ne.concours.service.impl;

import org.ne.concours.service.MinisterConcernerService;
import org.ne.concours.domain.MinisterConcerner;
import org.ne.concours.repository.MinisterConcernerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MinisterConcerner}.
 */
@Service
@Transactional
public class MinisterConcernerServiceImpl implements MinisterConcernerService {

    private final Logger log = LoggerFactory.getLogger(MinisterConcernerServiceImpl.class);

    private final MinisterConcernerRepository ministerConcernerRepository;

    public MinisterConcernerServiceImpl(MinisterConcernerRepository ministerConcernerRepository) {
        this.ministerConcernerRepository = ministerConcernerRepository;
    }

    @Override
    public MinisterConcerner save(MinisterConcerner ministerConcerner) {
        log.debug("Request to save MinisterConcerner : {}", ministerConcerner);
        return ministerConcernerRepository.save(ministerConcerner);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MinisterConcerner> findAll(Pageable pageable) {
        log.debug("Request to get all MinisterConcerners");
        return ministerConcernerRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MinisterConcerner> findOne(Long id) {
        log.debug("Request to get MinisterConcerner : {}", id);
        return ministerConcernerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MinisterConcerner : {}", id);
        ministerConcernerRepository.deleteById(id);
    }
}
