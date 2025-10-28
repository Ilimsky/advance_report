package org.example.revizor_backend.audit;

import org.example.revizor_backend.model.Audit;
import org.example.revizor_backend.repository.AuditRepository;
import org.example.revizor_backend.service.AuditServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

class AuditServiceImplTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditServiceImpl auditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Позитивный тест: Поиск по ID
    @Test
    void testFindByIdSuccess(){
        Audit audit = new Audit();
        audit.setId(1L);
        when(auditRepository.findById(1L)).thenReturn(Optional.of(audit));

//        Audit result = auditService.getAuditById(1L);

//        assertNotNull(result);
//        assertEquals(1L, result.getId());
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void createAudit() {
    }

    @Test
    void getAllAudits() {
    }

    @Test
    void getAuditsByIds() {
    }

    @Test
    void getAuditById() {
    }

    @Test
    void updateAudit() {
    }

    @Test
    void deleteAudit() {
    }

    @Test
    void deleteAuditsByIds() {
    }
}