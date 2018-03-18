package com.texnoprom.SOTCA.Backend.dao.tct;

import com.texnoprom.SOTCA.Backend.model.tct.DBVersion;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DBVersionRepository extends PagingAndSortingRepository<DBVersion, Long> {

}
