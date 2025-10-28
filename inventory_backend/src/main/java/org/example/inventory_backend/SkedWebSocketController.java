package org.example.inventory_backend;

import org.example.inventory_backend.dto.SkedDTO;
import org.example.inventory_backend.service.SkedService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SkedWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SkedService skedService;

    public SkedWebSocketController(SimpMessagingTemplate messagingTemplate, SkedService skedService) {
        this.messagingTemplate = messagingTemplate;
        this.skedService = skedService;
    }

    @MessageMapping("/skeds/{skedId}/availability")
    public void updateAvailability(
            @DestinationVariable Long skedId,
            @Payload Boolean available
    ) {
        SkedDTO skedDTO = skedService.getSkedById(skedId);
        skedDTO.setAvailable(available);
        SkedDTO updated = skedService.updateSked(skedId, skedDTO);

        messagingTemplate.convertAndSend(
                "/topic/skeds/" + skedId + "/updates",
                new AvailabilityUpdate(updated.getId(), updated.isAvailable())
        );
    }
}
