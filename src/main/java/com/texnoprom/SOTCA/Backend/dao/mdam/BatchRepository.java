package com.texnoprom.SOTCA.Backend.dao.mdam;

import com.texnoprom.SOTCA.Backend.model.mdam.RegisterBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BatchRepository extends PagingAndSortingRepository<RegisterBatch, Long> {
    Page<RegisterBatch> findByType(String type, Pageable pageable);

}
