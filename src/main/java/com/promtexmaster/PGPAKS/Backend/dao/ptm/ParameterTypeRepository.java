package com.promtexmaster.PGPAKS.Backend.dao.ptm;

import com.promtexmaster.PGPAKS.Backend.model.ptm.ParameterType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ParameterTypeRepository extends PagingAndSortingRepository<ParameterType, Long> {

    List<ParameterType> findTop25ByOrderByParameterTypesId();

}
