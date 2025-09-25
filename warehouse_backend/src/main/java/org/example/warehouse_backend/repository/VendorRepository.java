package org.example.warehouse_backend.repository;

import org.example.warehouse_backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {}