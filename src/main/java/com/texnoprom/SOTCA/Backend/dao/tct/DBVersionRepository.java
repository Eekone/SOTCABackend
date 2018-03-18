package com.texnoprom.SOTCA.Backend.dao.tct;

import com.texnoprom.SOTCA.Backend.model.tct.DBVersion;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DBVersionRepository extends JpaRepository<DBVersion, Long> {

    DBVersion findTopByOrderByIddbversionDesc();

}
