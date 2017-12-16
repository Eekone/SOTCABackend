package com.texnoprom.SOTCA.Backend;

import com.texnoprom.SOTCA.Backend.model.RegisterBatch;
import com.texnoprom.SOTCA.Backend.repository.BatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private BatchRepository personRepository;

    public RegisterService(BatchRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Page<RegisterBatch> findAllPageable(Pageable pageable) {
        return personRepository.findAll(pageable);
    }
}
