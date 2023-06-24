package com.example.asas.repository;

import com.example.asas.model.Sock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SockRepository extends JpaRepository<Sock, Long> {
    Collection<Sock> findSockByColorIgnoreCaseAndCottonPart(String color, int cottonPart);
    int countByColorIgnoreCaseAndCottonPartGreaterThan(String color, int cottonPart);
    int countByColorIgnoreCaseAndCottonPartLessThan(String color, int cottonPart);
    int countByColorIgnoreCaseAndCottonPartEquals(String color, int cottonPart);
}
