package org.example.warehouse_backend.entity;

public enum OrderStatus {
    NEW,                         // заказ создан, но ещё не оплачен
    AWAITING_PAYMENT,            // ожидает оплаты
    PAID,                        // оплачен
    AWAITING_VENDOR_CONFIRMATION,// ждёт подтверждения продавца
    CONFIRMED,                   // подтверждён продавцом
    PREPARING_FOR_SHIPMENT,      // продавец готовит заказ
    SHIPPED,                     // передан службе доставки
    DELIVERED,                   // доставлен покупателю
    COMPLETED,                   // завершён
    CANCELLED_BY_USER,           // отменён покупателем
    CANCELLED_BY_VENDOR,         // отменён продавцом
    REFUND_REQUESTED,            // запрошен возврат
    REFUND_APPROVED,             // возврат одобрен
    REFUND_COMPLETED              // возврат произведён
}