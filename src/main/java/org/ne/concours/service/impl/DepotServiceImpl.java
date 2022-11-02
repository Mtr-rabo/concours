package org.ne.concours.service.impl;

import org.ne.concours.service.DepotService;
import org.ne.concours.domain.Depot;
import org.ne.concours.repository.DepotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Depot}.
 */
@Service
@Transactional
public class DepotServiceImpl implements DepotService {

    private final Logger log = LoggerFactory.getLogger(DepotServiceImpl.class);

    private final DepotRepository depotRepository;

    public DepotServiceImpl(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    @Override
    public Depot save(Depot depot) {
        log.debug("Request to save Depot : {}", depot);
        return depotRepository.save(depot);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Depot> findAll(Pageable pageable) {
        log.debug("Request to get all Depots");
        return depotRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Depot> findOne(Long id) {
        log.debug("Request to get Depot : {}", id);
        return depotRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Depot : {}", id);
        depotRepository.deleteById(id);
    }
}
