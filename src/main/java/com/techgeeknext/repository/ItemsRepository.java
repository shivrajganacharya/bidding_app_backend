package com.techgeeknext.repository;

import com.techgeeknext.model.ItemsDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<ItemsDao, Integer> {
}
