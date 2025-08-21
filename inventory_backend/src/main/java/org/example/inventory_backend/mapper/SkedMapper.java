package org.example.inventory_backend.mapper;

import org.example.inventory_backend.dto.SkedDTO;
import org.example.inventory_backend.model.*;
import org.springframework.stereotype.Component;

@Component
public class SkedMapper {
    public SkedDTO toDTO(Sked sked) {
        SkedDTO dto = new SkedDTO();
        dto.setId(sked.getId());
        dto.setDepartmentId(sked.getDepartment().getId());
        dto.setEmployeeId(sked.getEmployee().getId());
        dto.setDepartmentIdentifier(sked.getDepartmentIdentifier());
        dto.setEmployeeIdentifier(sked.getEmployeeIdentifier());

        dto.setAssetCategory(sked.getAssetCategory());
        dto.setDateReceived(sked.getDateReceived());
        dto.setSkedNumber(sked.getSkedNumber());
        dto.setItemName(sked.getItemName());
        dto.setSerialNumber(sked.getSerialNumber());
        dto.setCount(sked.getCount());
        dto.setMeasure(sked.getMeasure());
        dto.setPrice(sked.getPrice());
        dto.setPlace(sked.getPlace());
        dto.setComments(sked.getComments());

        dto.setAvailable(sked.isAvailable());
        return dto;
    }

    public Sked toEntity(SkedDTO dto, Department department, Employee employee) {
        Sked sked = new Sked();
        sked.setDepartment(department);
        sked.setEmployee(employee);
        sked.setSkedNumber(dto.getSkedNumber());
        sked.setDepartmentIdentifier(dto.getDepartmentIdentifier());
        sked.setEmployeeIdentifier(dto.getEmployeeIdentifier());

        // Новые поля
        sked.setAssetCategory(dto.getAssetCategory());
        sked.setDateReceived(dto.getDateReceived());
        sked.setItemName(dto.getItemName());
        sked.setSerialNumber(dto.getSerialNumber());
        sked.setCount(dto.getCount());
        sked.setMeasure(dto.getMeasure());
        sked.setPrice(dto.getPrice());
        sked.setPlace(dto.getPlace());
        sked.setComments(dto.getComments());

        sked.setAvailable(dto.isAvailable());
        return sked;
    }
}