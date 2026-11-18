package com.promtexmaster.PGPAKS.Backend.dao.ptm;

import com.promtexmaster.PGPAKS.Backend.model.ptm.DBVersion;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DBVersionRepository extends JpaRepository<DBVersion, Long> {

    DBVersion findTopByOrderByIddbversionDesc();

}
