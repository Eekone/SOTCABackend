package com.promtexmaster.PGPAKS.Backend;

import com.promtexmaster.PGPAKS.Backend.dao.mdam.BatchRepository;
import com.promtexmaster.PGPAKS.Backend.model.mdam.RegisterBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private BatchRepository batchRepository;

    public RegisterService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Transactional
    public Page<RegisterBatch> findByType(String name, Pageable pageable) {
        return batchRepository.findByType(name, pageable);
    }

}
