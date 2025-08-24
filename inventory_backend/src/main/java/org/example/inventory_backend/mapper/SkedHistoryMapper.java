package org.example.inventory_backend.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.inventory_backend.dto.SkedHistoryDTO;
import org.example.inventory_backend.model.SkedHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SkedHistoryMapper {
    private final ObjectMapper objectMapper;

    @Autowired
    public SkedHistoryMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SkedHistoryDTO toDTO(SkedHistory history) {
        SkedHistoryDTO dto = new SkedHistoryDTO();
        dto.setId(history.getId());
        dto.setActionType(history.getActionType());
        dto.setActionDate(history.getActionDate());
        dto.setPerformedBy(history.getPerformedBy());
        dto.setReason(history.getReason());

        try {
            if (history.getPreviousData() != null) {
                dto.setPreviousData(objectMapper.readValue(history.getPreviousData(), Map.class));
            }
            if (history.getNewData() != null) {
                dto.setNewData(objectMapper.readValue(history.getNewData(), Map.class));
            }
        } catch (JsonProcessingException e) {
            // Логируем ошибку, но не прерываем выполнение
        }

        return dto;
    }
}