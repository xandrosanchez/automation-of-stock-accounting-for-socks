package com.example.asas.Service;

import com.example.asas.model.Sock;
import com.example.asas.repository.SockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SockService {
    @Autowired
    private SockRepository sockRepository;

    public Sock incomeSock(String color, int cottonPart, int quantity) {
        Sock sock = checkingForSocks(color.toLowerCase(), cottonPart);
        if (sock == null) {
            sock = new Sock(color.toLowerCase(), cottonPart, quantity);
        } else {
            sock.setQuantity(sock.getQuantity() + quantity);
        }
        return sockRepository.save(sock);
    }

    public Sock outcomeSock(String color, int cottonPart, int quantity) {
        Sock sock = checkingForSocks(color, cottonPart);
        if (sock == null) {
            return null;
        } else {
            quantity = sock.getQuantity() - quantity;
            if (quantity <= 0) {
                sockRepository.delete(sock);
                sock.setQuantity(0);
                return sock;
            } else {
                sock.setQuantity(quantity);
                return sockRepository.save(sock);
            }
        }
    }

    public int getTotalQuantity(String color, String operation, int cottonPart) {
        return switch (operation) {
            case "moreThan" -> sockRepository.sumQuantityByColorIgnoreCaseAndCottonPartGreaterThan(color, cottonPart);
            case "lessThan" -> sockRepository.sumQuantityByColorIgnoreCaseAndCottonPartLessThan(color, cottonPart);
            case "equal" -> sockRepository.sumQuantityByColorIgnoreCaseAndCottonPartEquals(color, cottonPart);
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };
    }


    private Sock checkingForSocks(String color, int cottonPart) {
        Collection<Sock> socks = sockRepository.findSockByColorIgnoreCaseAndCottonPart(color, cottonPart);
        if (socks.isEmpty()) {
            return null;
        } else {
            return socks.iterator().next();
        }
    }

}
