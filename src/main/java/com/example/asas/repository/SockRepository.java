package com.example.asas.repository;

import com.example.asas.model.Sock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SockRepository extends JpaRepository<Sock, Long> {
}
