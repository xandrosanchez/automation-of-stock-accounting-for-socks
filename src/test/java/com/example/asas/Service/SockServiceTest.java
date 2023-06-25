package com.example.asas.Service;

import com.example.asas.model.Sock;
import com.example.asas.repository.SockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

public class SockServiceTest {
    @Mock
    private SockRepository sockRepository;

    @InjectMocks
    private SockService sockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIncomeSock() {
        // Arrange
        String color = "black";
        int cottonPart = 80;
        int quantity = 10;

        Sock existingSock = new Sock(color, cottonPart, 5);
        Mockito.when(sockRepository.findSockByColorIgnoreCaseAndCottonPart(color, cottonPart))
                .thenReturn(Collections.singletonList(existingSock));
        Mockito.when(sockRepository.save(Mockito.any(Sock.class)))
                .thenReturn(existingSock);

        // Act
        Sock result = sockService.incomeSock(color, cottonPart, quantity);

        // Assert
        Assertions.assertEquals(15, result.getQuantity());
        Mockito.verify(sockRepository, Mockito.times(1)).save(existingSock);
    }

    @Test
    public void testOutcomeSock() {
        // Arrange
        String color = "black";
        int cottonPart = 80;
        int quantity = 7;

        Sock existingSock = new Sock(color, cottonPart, 10);
        Mockito.when(sockRepository.findSockByColorIgnoreCaseAndCottonPart(color, cottonPart))
                .thenReturn(Collections.singletonList(existingSock));
        Mockito.when(sockRepository.save(Mockito.any(Sock.class)))
                .thenReturn(existingSock);

        // Act
        Sock result = sockService.outcomeSock(color, cottonPart, quantity);

        // Assert
        Assertions.assertEquals(3, result.getQuantity());
        Mockito.verify(sockRepository, Mockito.times(1)).save(existingSock);
    }

    @Test
    public void testOutcomeSock_NoSockFound() {
        // Arrange
        String color = "black";
        int cottonPart = 80;
        int quantity = 7;

        Mockito.when(sockRepository.findSockByColorIgnoreCaseAndCottonPart(color, cottonPart))
                .thenReturn(Collections.emptyList());

        // Act
        Sock result = sockService.outcomeSock(color, cottonPart, quantity);

        // Assert
        Assertions.assertNull(result);
        Mockito.verify(sockRepository, Mockito.never()).save(Mockito.any(Sock.class));
    }

    @Test
    public void testGetTotalQuantity_MoreThan() {
        // Arrange
        String color = "black";
        int cottonPart = 80;

        int expectedQuantity = 15;
        Mockito.when(sockRepository.sumQuantityByColorIgnoreCaseAndCottonPartGreaterThan(color, cottonPart))
                .thenReturn(expectedQuantity);

        // Act
        int result = sockService.getTotalQuantity(color, "moreThan", cottonPart);

        // Assert
        Assertions.assertEquals(expectedQuantity, result);
    }

    @Test
    public void testGetTotalQuantity_LessThan() {
        // Arrange
        String color = "black";
        int cottonPart = 80;

        int expectedQuantity = 5;
        Mockito.when(sockRepository.sumQuantityByColorIgnoreCaseAndCottonPartLessThan(color, cottonPart))
                .thenReturn(expectedQuantity);

        // Act
        int result = sockService.getTotalQuantity(color, "lessThan", cottonPart);

        // Assert
        Assertions.assertEquals(expectedQuantity, result);
    }

    @Test
    public void testGetTotalQuantity_Equal() {
        // Arrange
        String color = "black";
        int cottonPart = 80;

        int expectedQuantity = 10;
        Mockito.when(sockRepository.sumQuantityByColorIgnoreCaseAndCottonPartEquals(color, cottonPart))
                .thenReturn(expectedQuantity);

        // Act
        int result = sockService.getTotalQuantity(color, "equal", cottonPart);

        // Assert
        Assertions.assertEquals(expectedQuantity, result);
    }

    @Test
    public void testGetTotalQuantity_InvalidOperation() {
        // Arrange
        String color = "black";
        int cottonPart = 80;

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                sockService.getTotalQuantity(color, "invalidOperation", cottonPart));
    }
}
