package com.lviv.hnatko.repository;

import com.lviv.hnatko.entity.AppUser;
import com.lviv.hnatko.entity.PresentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PresentOrderRepository extends JpaRepository<PresentOrder, Integer> {

    Optional<PresentOrder> findByIdAndBuyer(Integer presentOrderId, AppUser buyer);

    List<PresentOrder> findByBuyer(AppUser buyer);
}
