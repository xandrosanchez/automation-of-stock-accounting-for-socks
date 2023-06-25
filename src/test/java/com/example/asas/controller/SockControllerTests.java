package com.example.asas.controller;

import com.example.asas.Service.SockService;
import com.example.asas.model.Sock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SockControllerTests {

    @Test
    public void testRegisterIncome_ValidRequest_ReturnsOkResponse() {
        // Arrange
        SockService sockService = Mockito.mock(SockService.class);
        SockController sockController = new SockController(sockService);
        Sock sock = new Sock("blue", 80, 10);

        // Act
        ResponseEntity<Void> response = sockController.registerIncome(sock);

        // Assert
        verify(sockService, times(1)).incomeSock(anyString(), anyInt(), anyInt());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRegisterOutcome_ValidRequest_ReturnsOkResponse() {
        // Arrange
        SockService sockService = Mockito.mock(SockService.class);
        SockController sockController = new SockController(sockService);
        Sock sock = new Sock("blue", 80, 5);

        // Act
        ResponseEntity<Void> response = sockController.registerOutcome(sock);

        // Assert
        verify(sockService, times(1)).outcomeSock(anyString(), anyInt(), anyInt());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetTotalSocksCount_ValidRequest_ReturnsTotalQuantity() {
        // Arrange
        SockService sockService = Mockito.mock(SockService.class);
        SockController sockController = new SockController(sockService);
        String color = "blue";
        String operation = "moreThan";
        int cottonPart = 80;
        int totalQuantity = 15;
        when(sockService.getTotalQuantity(eq(color), eq(operation), eq(cottonPart))).thenReturn(totalQuantity);

        // Act
        ResponseEntity<Integer> response = sockController.getTotalSocksCount(color, operation, cottonPart);

        // Assert
        verify(sockService, times(1)).getTotalQuantity(eq(color), eq(operation), eq(cottonPart));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(totalQuantity, response.getBody());
    }

    @Test
    public void testGetTotalSocksCount_InvalidRequest_ReturnsBadRequestResponse() {
        // Arrange
        SockService sockService = Mockito.mock(SockService.class);
        SockController sockController = new SockController(sockService);
        String color = null;
        String operation = null;
        Integer cottonPart = null;

        // Act
        ResponseEntity<Integer> response = sockController.getTotalSocksCount(color, operation, cottonPart);

        // Assert
        verifyNoInteractions(sockService);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
