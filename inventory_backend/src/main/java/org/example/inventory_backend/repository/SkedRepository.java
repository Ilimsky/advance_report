package org.example.inventory_backend.repository;

import org.example.inventory_backend.model.Sked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkedRepository extends JpaRepository<Sked, Long> {
    @Query("SELECT r FROM Sked r WHERE r.department.id = :departmentId")
    List<Sked> findByDepartmentById(@Param("departmentId") Long departmentId);

    @Query("SELECT COUNT(r) > 0 FROM Sked r WHERE r.skedNumber = :skedNumber AND r.department.id = :departmentId")
    boolean existsBySkedNumberAndDepartment_Id(@Param("skedNumber") String skedNumber,
                                               @Param("departmentId") Long departmentId);
}