package com.demo.repository;

import com.demo.model.ItemsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ItemsRepository extends JpaRepository<ItemsDao, Integer> {
    // List<ItemsDao> findItemsDaoByTransport_dateBefore(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date date);

    @Query("select d from ItemsDao d where d.datetime <= :datetime")
    List findAllWithDatetimeBefore(@Param("datetime") Date datetime);
}
