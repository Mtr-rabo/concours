package org.ne.concours.service.impl;

import org.ne.concours.service.OptionService;
import org.ne.concours.domain.Option;
import org.ne.concours.repository.OptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Option}.
 */
@Service
@Transactional
public class OptionServiceImpl implements OptionService {

    private final Logger log = LoggerFactory.getLogger(OptionServiceImpl.class);

    private final OptionRepository optionRepository;

    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public Option save(Option option) {
        log.debug("Request to save Option : {}", option);
        return optionRepository.save(option);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Option> findAll(Pageable pageable) {
        log.debug("Request to get all Options");
        return optionRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Option> findOne(Long id) {
        log.debug("Request to get Option : {}", id);
        return optionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Option : {}", id);
        optionRepository.deleteById(id);
    }
}
