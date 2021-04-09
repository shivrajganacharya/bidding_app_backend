package com.techgeeknext.repository;

import com.techgeeknext.model.BidsDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidsRepository extends JpaRepository<BidsDao, Integer> {
}
