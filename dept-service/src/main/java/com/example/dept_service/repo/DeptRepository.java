package com.example.dept_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dept_service.entity.Dept;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long>{

}
