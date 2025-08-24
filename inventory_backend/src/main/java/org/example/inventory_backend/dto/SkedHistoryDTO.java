package org.example.inventory_backend.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.example.inventory_backend.model.SkedHistory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Data
public class SkedHistoryDTO {
    private Long id;
    private Long skedId; // Явно передаем skedId
    private String actionType;
    private LocalDateTime actionDate;
    private String performedBy;
    private String reason;
    private Map<String, Object> previousData;
    private Map<String, Object> newData;

    public static SkedHistoryDTO fromEntity(SkedHistory history) {
        SkedHistoryDTO dto = new SkedHistoryDTO();
        dto.setId(history.getId());
        dto.setSkedId(history.getSked().getId()); // Явно устанавливаем skedId
        dto.setActionType(history.getActionType());
        dto.setActionDate(history.getActionDate());
        dto.setPerformedBy(history.getPerformedBy());
        dto.setReason(history.getReason());

        // Парсим JSON строки в Map
        dto.setPreviousData(parseJsonData(history.getPreviousData()));
        dto.setNewData(parseJsonData(history.getNewData()));

        return dto;
    }

    private static Map<String, Object> parseJsonData(String jsonData) {
        if (jsonData == null) return null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return Collections.singletonMap("rawData", jsonData);
        }
    }
}