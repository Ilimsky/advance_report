package org.example.usermanagementservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentAssignmentRepository extends JpaRepository<UserDepartmentAssignment, Long> {
    List<UserDepartmentAssignment> findByUserId(Long userId);
    List<UserDepartmentAssignment> findByDepartmentId(Long departmentId);
    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
}