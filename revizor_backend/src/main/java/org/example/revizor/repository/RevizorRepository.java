package org.example.revizor.repository;

import org.example.revizor.model.Revizor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevizorRepository extends JpaRepository<Revizor, Long> {
}
