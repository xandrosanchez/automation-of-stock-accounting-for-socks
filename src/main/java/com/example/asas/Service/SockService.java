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
        Sock sock = checkingForSocks(color, cottonPart);
        if (sock == null) {
            sock = new Sock(color, cottonPart, quantity);
        } else {
            sock.setQuantity(sock.getQuantity() + quantity);
        }
        return sockRepository.save(sock);
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
