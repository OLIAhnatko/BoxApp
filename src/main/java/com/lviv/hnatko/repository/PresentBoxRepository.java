package com.lviv.hnatko.repository;


import com.lviv.hnatko.entity.PresentBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PresentBoxRepository extends JpaRepository<PresentBox, Integer> {

    Optional<PresentBox> findById(Integer presentBoxId);
}
