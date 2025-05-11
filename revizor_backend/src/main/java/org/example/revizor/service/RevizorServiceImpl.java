package org.example.revizor.service;

import org.example.revizor.dto.RevizorDTO;
import org.example.revizor.mapper.RevizorMapper;
import org.example.revizor.model.Revizor;
import org.example.revizor.repository.RevizorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevizorServiceImpl extends GenericServiceImpl<Revizor, RevizorDTO, Long> implements RevizorService {


    @Autowired
    public RevizorServiceImpl(RevizorRepository repository, RevizorMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void updateEntity(Revizor entity, RevizorDTO dto) {
        entity.setName(dto.getName());
    }

//    private final RevizorRepository revizorRepository;
//    private final RevizorMapper revizorMapper;
//
//    @Autowired
//    public RevizorServiceImpl(RevizorRepository revizorRepository, RevizorMapper revizorMapper) {
//        this.revizorRepository = revizorRepository;
//        this.revizorMapper = revizorMapper;
//    }
//    @Override
//    public List<RevizorDTO> getAllRevizors() {
//        return revizorRepository.findAll()
//                .stream()
//                .map(revizorMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//    @Override
//    public RevizorDTO createRevizor(RevizorDTO revizorDTO) {
//        Revizor revizor = revizorMapper.toEntity(revizorDTO);
//        Revizor savedRevizor = revizorRepository.save(revizor);
//        return revizorMapper.toDTO(savedRevizor);
//    }
//    @Override
//    public Optional<RevizorDTO> getRevizorById(Long id) {
//        return revizorRepository.findById(id)
//                .map(revizorMapper::toDTO);
//    }
//    @Override
//    public void deleteRevizor(Long id) {
//        revizorRepository.deleteById(id);
//    }
//    @Override
//    public RevizorDTO updateRevizor(Long id, RevizorDTO revizorDTO) {
//        Revizor revizor = revizorRepository.findById(id)
//                .orElseThrow(() -> new RevizorNotFoundException("Revizor not found"));
//
//        revizor.setName(revizorDTO.getName());
//
//        Revizor updatedRevizor = revizorRepository.save(revizor);
//        return revizorMapper.toDTO(updatedRevizor);
//    }
}