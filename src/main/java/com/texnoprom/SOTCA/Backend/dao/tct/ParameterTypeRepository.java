package com.texnoprom.SOTCA.Backend.dao.tct;

import com.texnoprom.SOTCA.Backend.model.tct.ParameterType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ParameterTypeRepository extends PagingAndSortingRepository<ParameterType, Long> {

    List<ParameterType> findTop25ByOrderByParameterTypesId();

}
