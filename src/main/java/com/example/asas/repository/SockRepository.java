package com.example.asas.repository;

import com.example.asas.model.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface SockRepository extends JpaRepository<Sock, Long> {
    Collection<Sock> findSockByColorIgnoreCaseAndCottonPart(String color, int cottonPart);
    @Query("SELECT SUM(s.quantity) FROM Sock s WHERE LOWER(s.color) = LOWER(:color) AND s.cottonPart > :cottonPart")
    int sumQuantityByColorIgnoreCaseAndCottonPartGreaterThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query("SELECT SUM(s.quantity) FROM Sock s WHERE LOWER(s.color) = LOWER(:color) AND s.cottonPart < :cottonPart")
    int sumQuantityByColorIgnoreCaseAndCottonPartLessThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query("SELECT SUM(s.quantity) FROM Sock s WHERE LOWER(s.color) = LOWER(:color) AND s.cottonPart = :cottonPart")
    int sumQuantityByColorIgnoreCaseAndCottonPartEquals(@Param("color") String color, @Param("cottonPart") int cottonPart);

}
