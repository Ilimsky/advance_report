package org.example.reference_backend.service;

import jakarta.transaction.Transactional;
import org.example.reference_backend.model.Role;
import org.example.reference_backend.model.User;
import org.example.reference_backend.repository.UserRepo;
import org.example.reference_backend.dto.UserDepartmentBindingDTO;
import org.example.reference_backend.exception.EntityNotFoundException;
import org.example.reference_backend.mapper.UserDepartmentBindingMapper;
import org.example.reference_backend.model.Department;
import org.example.reference_backend.model.UserDepartmentBinding;
import org.example.reference_backend.repository.DepartmentRepository;
import org.example.reference_backend.repository.UserDepartmentBindingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDepartmentBindingServiceImpl implements UserDepartmentBindingService {
    private static final Logger logger = LoggerFactory.getLogger(UserDepartmentBindingServiceImpl.class);
    private final UserDepartmentBindingRepository bindingRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepo userRepository;
    private final UserDepartmentBindingMapper bindingMapper;

    public UserDepartmentBindingServiceImpl(UserDepartmentBindingRepository bindingRepository,
                                            DepartmentRepository departmentRepository,
                                            UserRepo userRepository,
                                            UserDepartmentBindingMapper bindingMapper) {
        this.bindingRepository = bindingRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.bindingMapper = bindingMapper;
    }

    @Transactional
    @Override
    public UserDepartmentBindingDTO createBinding(UserDepartmentBindingDTO bindingDTO) {
        logger.info("Creating binding for userId={}, departmentId={}",
                bindingDTO.getUser().getId(), bindingDTO.getDepartment().getId());

        User user = userRepository.findById(bindingDTO.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь", bindingDTO.getUser().getId()));

        logger.debug("User found: id={}, username={}", user.getId(), user.getUsername());

        if (!user.getRoles().contains(Role.ROLE_USER)) {
            logger.warn("Attempt to bind non-ROLE_USER user: userId={}, roles={}",
                    user.getId(), user.getRoles());
            throw new IllegalArgumentException("Только пользователи с ролью ROLE_USER могут быть привязаны к филиалам");
        }

        Department department = departmentRepository.findById(bindingDTO.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Филиал", bindingDTO.getDepartment().getId()));

        logger.debug("Department found: id={}, name={}", department.getId(), department.getName());

        if (bindingRepository.existsByUserId(user.getId())) {
            logger.warn("User already has binding: userId={}", user.getId());
            throw new IllegalStateException("Пользователь уже привязан к филиалу");
        }

        UserDepartmentBinding binding = bindingMapper.toEntity(bindingDTO, department, user);
        UserDepartmentBinding savedBinding = bindingRepository.save(binding);

        logger.info("Binding created successfully: bindingId={}, userId={}, departmentId={}",
                savedBinding.getId(), user.getId(), department.getId());

        return bindingMapper.toDTO(savedBinding);
    }

    @Override
    public List<UserDepartmentBindingDTO> getAllBindings() {
        logger.debug("Retrieving all user-department bindings");
        List<UserDepartmentBindingDTO> bindings = bindingRepository.findAll().stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());
        logger.debug("Retrieved {} bindings", bindings.size());
        return bindings;
    }

    @Override
    public List<UserDepartmentBindingDTO> getBindingsByUser(Long userId) {
        logger.debug("Retrieving bindings for userId={}", userId);

        if (!userRepository.existsById(userId)) {
            logger.warn("User not found when retrieving bindings: userId={}", userId);
            throw new EntityNotFoundException("User", userId);
        }

        List<UserDepartmentBindingDTO> bindings = bindingRepository.findByUserId(userId).stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());

        logger.debug("Retrieved {} bindings for userId={}", bindings.size(), userId);
        return bindings;
    }

    @Override
    public List<UserDepartmentBindingDTO> getBindingsByDepartment(Long departmentId) {
        logger.debug("Retrieving bindings for departmentId={}", departmentId);

        if (!departmentRepository.existsById(departmentId)) {
            logger.warn("Department not found when retrieving bindings: departmentId={}", departmentId);
            throw new EntityNotFoundException("Department", departmentId);
        }

        List<UserDepartmentBindingDTO> bindings = bindingRepository.findByDepartmentId(departmentId).stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());

        logger.debug("Retrieved {} bindings for departmentId={}", bindings.size(), departmentId);
        return bindings;
    }

    @Override
    public UserDepartmentBindingDTO getBindingById(Long bindingId) {
        logger.debug("Retrieving binding by id={}", bindingId);
        UserDepartmentBinding binding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> {
                    logger.warn("Binding not found: bindingId={}", bindingId);
                    return new EntityNotFoundException("UserDepartmentBinding", bindingId);
                });
        logger.debug("Binding retrieved: bindingId={}, userId={}, departmentId={}",
                binding.getId(), binding.getUser().getId(), binding.getDepartment().getId());
        return bindingMapper.toDTO(binding);
    }


    @Transactional
    @Override
    public UserDepartmentBindingDTO updateBinding(Long bindingId, UserDepartmentBindingDTO updatedBindingDTO) {
        logger.info("Updating binding: bindingId={}, newUserId={}, newDepartmentId={}",
                bindingId, updatedBindingDTO.getUser().getId(), updatedBindingDTO.getDepartment().getId());

        UserDepartmentBinding existingBinding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> {
                    logger.error("Binding not found: id={}", bindingId);
                    return new EntityNotFoundException("UserDepartmentBinding", bindingId);
                });

        logger.debug("Existing binding found: userId={}, departmentId={}",
                existingBinding.getUser().getId(), existingBinding.getDepartment().getId());

        Department department = departmentRepository.findById(updatedBindingDTO.getDepartment().getId())
                .orElseThrow(() -> {
                    logger.error("Department not found: id={}", updatedBindingDTO.getDepartment().getId());
                    return new EntityNotFoundException("Department", updatedBindingDTO.getDepartment().getId());
                });

        logger.debug("New department found: id={}, name={}", department.getId(), department.getName());

        User user = userRepository.findById(updatedBindingDTO.getUser().getId())
                .orElseThrow(() -> {
                    logger.error("User not found: id={}", updatedBindingDTO.getUser().getId());
                    return new EntityNotFoundException("User", updatedBindingDTO.getUser().getId());
                });

        logger.debug("New user found: id={}, username={}", user.getId(), user.getUsername());

        if (!user.getRoles().contains(Role.ROLE_USER)) {
            logger.error("User id={} does not have ROLE_USER for binding update, roles={}",
                    user.getId(), user.getRoles());
            throw new IllegalArgumentException("Only users with ROLE_USER can be bound to departments");
        }

        // Log if there are actual changes
        boolean hasChanges = !existingBinding.getUser().getId().equals(user.getId()) ||
                !existingBinding.getDepartment().getId().equals(department.getId());

        if (hasChanges) {
            logger.info("Changes detected - oldUserId={}, oldDepartmentId={}, newUserId={}, newDepartmentId={}",
                    existingBinding.getUser().getId(), existingBinding.getDepartment().getId(),
                    user.getId(), department.getId());
        } else {
            logger.debug("No changes detected for bindingId={}", bindingId);
        }

        existingBinding.setDepartment(department);
        existingBinding.setUser(user);
        UserDepartmentBinding updatedBinding = bindingRepository.save(existingBinding);

        logger.info("Binding updated successfully: bindingId={}, userId={}, departmentId={}",
                bindingId, user.getId(), department.getId());

        return bindingMapper.toDTO(updatedBinding);
    }

    @Transactional
    @Override
    public void deleteBinding(Long bindingId) {
        logger.info("Deleting binding: bindingId={}", bindingId);

        if (!bindingRepository.existsById(bindingId)) {
            logger.warn("Binding not found for deletion: bindingId={}", bindingId);
            throw new EntityNotFoundException("UserDepartmentBinding", bindingId);
        }

        bindingRepository.findById(bindingId).ifPresent(bindingToDelete -> logger.debug("Binding to delete: userId={}, departmentId={}",
                bindingToDelete.getUser().getId(), bindingToDelete.getDepartment().getId()));

        bindingRepository.deleteById(bindingId);
        logger.info("Binding deleted successfully: bindingId={}", bindingId);
    }

    @Transactional
    @Override
    public void deleteBindingByUser(Long userId) {
        logger.info("Deleting all bindings for user: userId={}", userId);

        if (!userRepository.existsById(userId)) {
            logger.warn("User not found for binding deletion: userId={}", userId);
            throw new EntityNotFoundException("User", userId);
        }

        List<UserDepartmentBinding> bindingsToDelete = bindingRepository.findByUserId(userId);
        logger.debug("Found {} bindings to delete for userId={}", bindingsToDelete.size(), userId);

        if (!bindingsToDelete.isEmpty()) {
            bindingsToDelete.forEach(binding ->
                    logger.debug("Deleting binding: bindingId={}, departmentId={}",
                            binding.getId(), binding.getDepartment().getId()));
        }

        bindingRepository.deleteByUserId(userId);
        logger.info("All bindings deleted successfully for userId={}", userId);
    }
}