package com.demo.repository;

import com.demo.model.BidsDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidsRepository extends JpaRepository<BidsDao, Integer> {

}
