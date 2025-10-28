package org.example.inventory_backend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class SkedUpdateMessage {
    private Long skedId;
    private boolean available;
}