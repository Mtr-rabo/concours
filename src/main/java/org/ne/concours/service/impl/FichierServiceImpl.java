package org.ne.concours.service.impl;

import org.ne.concours.service.FichierService;
import org.ne.concours.domain.Fichier;
import org.ne.concours.repository.FichierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fichier}.
 */
@Service
@Transactional
public class FichierServiceImpl implements FichierService {

    private final Logger log = LoggerFactory.getLogger(FichierServiceImpl.class);

    private final FichierRepository fichierRepository;

    public FichierServiceImpl(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    @Override
    public Fichier save(Fichier fichier) {
        log.debug("Request to save Fichier : {}", fichier);
        return fichierRepository.save(fichier);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Fichier> findAll(Pageable pageable) {
        log.debug("Request to get all Fichiers");
        return fichierRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Fichier> findOne(Long id) {
        log.debug("Request to get Fichier : {}", id);
        return fichierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fichier : {}", id);
        fichierRepository.deleteById(id);
    }
}
