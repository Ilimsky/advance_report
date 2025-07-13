package org.example.inventory_backend;

import org.example.inventory_backend.dto.SkedDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SkedWebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public SkedWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifySkedUpdate(SkedDTO skedDTO) {
        messagingTemplate.convertAndSend(
                "/topic/sked-updates",
                new SkedUpdateMessage(skedDTO.getId(), skedDTO.isAvailable())
        );
    }
}