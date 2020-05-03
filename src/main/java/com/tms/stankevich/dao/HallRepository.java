package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Long> {
    Optional<Hall> findById(Long id);
    Optional<Hall> findByName(String name);
    List<Hall> findAll();
    List<Hall> findAllByOrderByName();
}