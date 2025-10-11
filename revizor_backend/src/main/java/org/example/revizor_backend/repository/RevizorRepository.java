package org.example.revizor_backend.repository;

import org.example.revizor_backend.model.Revizor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevizorRepository extends JpaRepository<Revizor, Long> {
}
