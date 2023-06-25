package com.example.asas.controller;

import com.example.asas.Service.SockService;
import com.example.asas.model.Sock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SockController {
    @Autowired
    private SockService sockService;

    @PostMapping("/income")
    public ResponseEntity<Void> registerIncome(@RequestBody Sock sock) {
        try {
            sockService.incomeSock(sock.getColor(), sock.getCottonPart(), sock.getQuantity());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/outcome")
    public ResponseEntity<Void> registerOutcome(@RequestBody Sock sock) {
        try {
            sockService.outcomeSock(sock.getColor(), sock.getCottonPart(), sock.getQuantity());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<Integer> getTotalSocksCount(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) Integer cottonPart
    ) {
        if (color == null || operation == null || cottonPart == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            int totalQuantity = sockService.getTotalQuantity(color, operation, cottonPart);
            return ResponseEntity.ok(totalQuantity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
