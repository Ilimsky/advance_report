package com.example.dept_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dept_service.dto.DeptDTO;
import com.example.dept_service.entity.Dept;
import com.example.dept_service.repo.DeptRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Аннотация указывает, что этот класс — компонент уровня сервиса (Spring автоматически управляет этим бином).
public class DeptServiceImpl implements DeptService {

    // Репозиторий для взаимодействия с сущностью Employee в базе данных.
    private final DeptRepository deptRepository;

    // Внедряем зависимость через конструктор (рекомендуемый способ).
    @Autowired
    public DeptServiceImpl(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    /**
     * Создаёт новый филиал, сохраняя его в базе данных.
     * @param deptDTO DTO с данными о новом филиале.
     * @return DTO с данными сохранённого филиале.
     */
    @Override
    public DeptDTO createDept(DeptDTO deptDTO) {
        // Преобразуем DTO в сущность Dept.
        Dept dept = mapToEntity(deptDTO);

        // Сохраняем сущность в базе данных.
        Dept savedDept = deptRepository.save(dept);

        // Преобразуем сохранённую сущность обратно в DTO и возвращаем.
        return mapToDTO(savedDept);
    }

    /**
     * Получает список всех филиалов в виде DTO.
     * @return Список DeptDTO.
     */
    @Override
    public List<DeptDTO> getAllDepts() {
        // Получаем все филиалы из базы и преобразуем их в DTO.
        return deptRepository.findAll()
                .stream() // Превращаем список в поток данных.
                .map(this::mapToDTO) // Применяем преобразование для каждого филиала.
                .collect(Collectors.toList()); // Сохраняем результат в список.
    }

    /**
     * Ищет филиал по ID.
     * @param id Идентификатор филиала.
     * @return Optional с DeptDTO, если филиал найден.
     */
    @Override
    public Optional<DeptDTO> getDeptById(Long id) {
        // Ищем филиал по ID и преобразуем его в DTO, если найден.
        return deptRepository.findById(id)
                .map(this::mapToDTO); // Если филиал найден, выполняем преобразование.
    }

    /**
     * Обновляет данные филиала по его ID.
     * @param id Идентификатор филиала.
     * @param employeeDTO Новые данные для обновления.
     * @return Обновлённый DeptDTO.
     */
    @Override
    public DeptDTO updateDept(Long id, DeptDTO deptDTO) {
        // Ищем филиал по ID. Если не найден, выбрасываем исключение.
        Dept dept = deptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dept not found"));

        // Обновляем название филиала.
        dept.setName(deptDTO.getName());

        // Сохраняем обновлённую сущность в базе.
        Dept updatedDept = deptRepository.save(dept);

        // Преобразуем сущность в DTO и возвращаем.
        return mapToDTO(updatedDept);
    }

    /**
     * Удаляет филиал по его ID.
     * @param id Идентификатор филиала.
     */
    @Override
    public void delete(Long id) {
        // Удаляем филиал из базы данных по его ID.
        deptRepository.deleteById(id);
    }

    /**
     * Преобразует сущность Dept в DTO.
     * @param employee Сущность Dept.
     * @return DeptDTO.
     */
    private DeptDTO mapToDTO(Dept dept) {
        // Преобразование сущности в DTO.
        return new DeptDTO(dept.getId(), dept.getName());
    }

    /**
     * Преобразует DTO в сущность Dept.
     * @param employeeDTO DTO филиала.
     * @return Сущность Dept.
     */
    private Dept mapToEntity(DeptDTO deptDTO) {
        // Преобразование DTO в сущность.
        return new Dept(deptDTO.getId(), deptDTO.getName());
    }
}