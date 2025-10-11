package com.example.backend.repository;

import com.example.backend.model.UserDepartmentBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentBindingRepository extends JpaRepository<UserDepartmentBinding, Long> {
    List<UserDepartmentBinding> findByUserId(Long userId);
    List<UserDepartmentBinding> findByDepartmentId(Long departmentId);
    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
}