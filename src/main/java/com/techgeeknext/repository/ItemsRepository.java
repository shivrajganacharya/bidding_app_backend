package com.techgeeknext.repository;

import com.techgeeknext.model.ItemsDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemsRepository extends JpaRepository<ItemsDao, Integer> {
}
