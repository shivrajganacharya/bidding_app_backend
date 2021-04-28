package com.demo.springjwt.repository;

import com.demo.springjwt.models.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, Integer> {
    @Query("select d from Items d where d.datetime <= :datetime")
    List findAllWithDatetimeBefore(@Param("datetime") Date datetime);

    @Query("select d from Items d where d.on_sale = 1")
    List findAllByOn_saleEquals();
}
