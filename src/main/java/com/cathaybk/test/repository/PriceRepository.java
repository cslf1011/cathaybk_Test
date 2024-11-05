package com.cathaybk.test.repository;

import com.cathaybk.test.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price,Integer> {

    @Query(value = "SELECT * FROM price p WHERE p.date = :date", nativeQuery = true)
    Optional<Price> findByDate(@Param("date") LocalDate date);


    //修改價格
    @Modifying
    @Query(value = "UPDATE price SET price = :newPrice WHERE date = :date", nativeQuery = true)
    int updatePriceByAndDate(@Param("newPrice") BigDecimal newPrice,@Param("date") LocalDate date);

    //新增價格

    @Query(value = "INSERT INTO price (product_id, date, price) VALUES (:productId, :date, :price)", nativeQuery = true)
    void insertPrice(@Param("productId")String productId,
                     @Param("date") LocalDate date,
                     @Param("price") BigDecimal price);

    //檢查日期是否已經有價格
    boolean existsByDate(LocalDate date);


    //刪除某日價格
    @Modifying
    @Query(value ="DELETE FROM price p WHERE p.date = :date", nativeQuery = true)
    int deleteByDate(@Param("date") LocalDate date);


    @Query(value = "SELECT p FROM price p WHERE p.date BETWEEN :startDate AND :endDate",nativeQuery = true)
    List<Price> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
