package org.example.inventory_backend.repository;

import org.example.inventory_backend.model.Sked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkedRepository extends JpaRepository<Sked, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Sked r WHERE r.skedNumber = :skedNumber AND r.department.id = :departmentId")
    boolean existsBySkedNumberAndDepartment_Id(
            @Param("skedNumber") String skedNumber,
            @Param("departmentId") Long departmentId
    );

    @Query("SELECT s FROM Sked s " +
            "LEFT JOIN FETCH s.department " +
            "LEFT JOIN FETCH s.employee " +
            "WHERE s.department.id = :departmentId " +
            "ORDER BY s.skedNumber DESC")
    List<Sked> findByDepartmentById(@Param("departmentId") Long departmentId);

    @EntityGraph(attributePaths = {"department", "employee"})
    Page<Sked> findByDepartment_Id(Long departmentId, Pageable pageable);


}
