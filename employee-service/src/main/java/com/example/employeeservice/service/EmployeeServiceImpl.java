package com.example.employeeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.repo.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Аннотация указывает, что этот класс — компонент уровня сервиса (Spring автоматически управляет этим бином).
public class EmployeeServiceImpl implements EmployeeService {

    // Репозиторий для взаимодействия с сущностью Employee в базе данных.
    private final EmployeeRepository employeeRepository;

    // Внедряем зависимость через конструктор (рекомендуемый способ).
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Создаёт нового сотрудника, сохраняя его в базе данных.
     * @param employeeDTO DTO с данными о новом сотруднике.
     * @return DTO с данными сохранённого сотрудника.
     */
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Преобразуем DTO в сущность Employee.
        Employee employee = mapToEntity(employeeDTO);

        // Сохраняем сущность в базе данных.
        Employee savedEmployee = employeeRepository.save(employee);

        // Преобразуем сохранённую сущность обратно в DTO и возвращаем.
        return mapToDTO(savedEmployee);
    }

    /**
     * Получает список всех сотрудников в виде DTO.
     * @return Список EmployeeDTO.
     */
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        // Получаем всех сотрудников из базы и преобразуем их в DTO.
        return employeeRepository.findAll()
                .stream() // Превращаем список в поток данных.
                .map(this::mapToDTO) // Применяем преобразование для каждого сотрудника.
                .collect(Collectors.toList()); // Сохраняем результат в список.
    }

    /**
     * Ищет сотрудника по ID.
     * @param id Идентификатор сотрудника.
     * @return Optional с EmployeeDTO, если сотрудник найден.
     */
    @Override
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        // Ищем сотрудника по ID и преобразуем его в DTO, если найден.
        return employeeRepository.findById(id)
                .map(this::mapToDTO); // Если сотрудник найден, выполняем преобразование.
    }

    /**
     * Обновляет данные сотрудника по его ID.
     * @param id Идентификатор сотрудника.
     * @param employeeDTO Новые данные для обновления.
     * @return Обновлённый EmployeeDTO.
     */
    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        // Ищем сотрудника по ID. Если не найден, выбрасываем исключение.
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Обновляем имя сотрудника.
        employee.setName(employeeDTO.getName());

        // Сохраняем обновлённую сущность в базе.
        Employee updatedEmployee = employeeRepository.save(employee);

        // Преобразуем сущность в DTO и возвращаем.
        return mapToDTO(updatedEmployee);
    }

    /**
     * Удаляет сотрудника по его ID.
     * @param id Идентификатор сотрудника.
     */
    @Override
    public void delete(Long id) {
        // Удаляем сотрудника из базы данных по его ID.
        employeeRepository.deleteById(id);
    }

    /**
     * Преобразует сущность Employee в DTO.
     * @param employee Сущность Employee.
     * @return EmployeeDTO.
     */
    private EmployeeDTO mapToDTO(Employee employee) {
        // Преобразование сущности в DTO.
        return new EmployeeDTO(employee.getId(), employee.getName());
    }

    /**
     * Преобразует DTO в сущность Employee.
     * @param employeeDTO DTO сотрудника.
     * @return Сущность Employee.
     */
    private Employee mapToEntity(EmployeeDTO employeeDTO) {
        // Преобразование DTO в сущность.
        return new Employee(employeeDTO.getId(), employeeDTO.getName());
    }
}
