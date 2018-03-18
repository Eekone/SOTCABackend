package com.texnoprom.SOTCA.Backend.dao.mdam;


import com.texnoprom.SOTCA.Backend.model.mdam.Register;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface RegisterRepository extends PagingAndSortingRepository<Register, Long> {

}
