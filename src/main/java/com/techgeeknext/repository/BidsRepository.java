package com.techgeeknext.repository;

import com.techgeeknext.model.BidsDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidsRepository extends JpaRepository<BidsDao, Integer> {

}
