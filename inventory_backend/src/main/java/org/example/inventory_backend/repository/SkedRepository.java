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

    // Новый метод: возвращает все используемые номера в филиале
    @Query("SELECT s.skedNumber FROM Sked s WHERE s.department.id = :departmentId")
    List<String> findAllNumbersByDepartment(@Param("departmentId") Long departmentId);

    // Ищем только активные номера (у которых numberReleased = false)
    @Query("SELECT s.skedNumber FROM Sked s WHERE s.department.id = :departmentId AND s.numberReleased = false")
    List<String> findAllActiveNumbersByDepartment(@Param("departmentId") Long departmentId);

    // В других методах тоже добавляем условие, если нужно показывать только активные
    @Query("SELECT s FROM Sked s WHERE s.department.id = :departmentId AND s.numberReleased = false")
    List<Sked> findActiveByDepartmentById(@Param("departmentId") Long departmentId);
}
