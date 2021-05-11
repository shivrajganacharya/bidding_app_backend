package com.demo.springjwt;

import com.demo.springjwt.models.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidsRepository extends JpaRepository<Bids, Integer> {
}
