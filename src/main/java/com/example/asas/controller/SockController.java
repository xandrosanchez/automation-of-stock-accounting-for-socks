package com.example.asas.controller;

import com.example.asas.Service.SockService;
import com.example.asas.model.Sock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/socks")
@Validated
public class SockController {
    private final SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

    @Operation(summary = "Регистрирует поступление носков")
    @PostMapping("/income")
    public ResponseEntity<Void> registerIncome(@Valid @RequestBody Sock sock) {
        try {
            sockService.incomeSock(sock.getColor(), sock.getCottonPart(), sock.getQuantity());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Регистрирует списание носков")
    @PostMapping("/outcome")
    public ResponseEntity<Void> registerOutcome(@Valid @RequestBody Sock sock) {
        try {
            sockService.outcomeSock(sock.getColor(), sock.getCottonPart(), sock.getQuantity());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Получает общее количество носков")
    @GetMapping
    public ResponseEntity<Integer> getTotalSocksCount(
            @Parameter(in = ParameterIn.QUERY, name = "color", description = "Цвет носков")
            @RequestParam(required = false) String color,
            @Parameter(in = ParameterIn.QUERY, name = "operation", description = "Операция сравнения количества носков")
            @RequestParam(required = false) String operation,
            @Parameter(in = ParameterIn.QUERY, name = "cottonPart", description = "Содержание хлопка в носках")
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
