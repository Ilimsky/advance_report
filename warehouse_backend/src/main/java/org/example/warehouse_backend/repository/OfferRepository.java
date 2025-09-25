package org.example.warehouse_backend.repository;

import org.example.warehouse_backend.entity.Offer;
import org.example.warehouse_backend.entity.OfferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findByProductIdAndStatus(Long productId, OfferStatus status, Pageable p);
    Page<Offer> findByVendorId(Long vendorId, Pageable p);
}