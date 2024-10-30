package com.example.departmentservice.service;

import com.example.departmentservice.dto.DepartmentDTO;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.repo.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Аннотация указывает, что этот класс — компонент уровня сервиса (Spring автоматически управляет этим бином).
public class DepartmentServiceImpl implements DepartmentService {

    // Репозиторий для взаимодействия с сущностью Employee в базе данных.
    private final DepartmentRepository departmentRepository;

    // Внедряем зависимость через конструктор (рекомендуемый способ).
    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Создаёт новый филиал, сохраняя его в базе данных.
     * @param departmentDTO DTO с данными о новом филиале.
     * @return DTO с данными сохранённого филиале.
     */
    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        // Преобразуем DTO в сущность Department.
        Department department = mapToEntity(departmentDTO);

        // Сохраняем сущность в базе данных.
        Department savedDepartment = departmentRepository.save(department);

        // Преобразуем сохранённую сущность обратно в DTO и возвращаем.
        return mapToDTO(savedDepartment);
    }

    /**
     * Получает список всех филиалов в виде DTO.
     * @return Список DepartmentDTO.
     */
    @Override
    public List<DepartmentDTO> getAllDepartments() {
        // Получаем все филиалы из базы и преобразуем их в DTO.
        return departmentRepository.findAll()
                .stream() // Превращаем список в поток данных.
                .map(this::mapToDTO) // Применяем преобразование для каждого филиала.
                .collect(Collectors.toList()); // Сохраняем результат в список.
    }

    /**
     * Ищет филиал по ID.
     * @param id Идентификатор филиала.
     * @return Optional с DepartmentDTO, если филиал найден.
     */
    @Override
    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        // Ищем филиал по ID и преобразуем его в DTO, если найден.
        return departmentRepository.findById(id)
                .map(this::mapToDTO); // Если филиал найден, выполняем преобразование.
    }

    /**
     * Обновляет данные филиала по его ID.
     * @param id Идентификатор филиала.
     * @param departmentDTO Новые данные для обновления.
     * @return Обновлённый DepartmentDTO.
     */
    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        // Ищем филиал по ID. Если не найден, выбрасываем исключение.
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Обновляем название филиала.
        department.setName(departmentDTO.getName());

        // Сохраняем обновлённую сущность в базе.
        Department updatedDepartment = departmentRepository.save(department);

        // Преобразуем сущность в DTO и возвращаем.
        return mapToDTO(updatedDepartment);
    }

    /**
     * Удаляет филиал по его ID.
     * @param id Идентификатор филиала.
     */
    @Override
    public void delete(Long id) {
        // Удаляем филиал из базы данных по его ID.
        departmentRepository.deleteById(id);
    }

    /**
     * Преобразует сущность Department в DTO.
     * @param department Сущность Department.
     * @return DepartmentDTO.
     */
    private DepartmentDTO mapToDTO(Department department) {
        // Преобразование сущности в DTO.
        return new DepartmentDTO(department.getId(), department.getName());
    }

    /**
     * Преобразует DTO в сущность Department.
     * @param departmentDTO DTO филиала.
     * @return Сущность Department.
     */
    private Department mapToEntity(DepartmentDTO departmentDTO) {
        // Преобразование DTO в сущность.
        return new Department(departmentDTO.getId(), departmentDTO.getName());
    }
}