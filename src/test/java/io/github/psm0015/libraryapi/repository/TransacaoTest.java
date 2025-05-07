package io.github.psm0015.libraryapi.repository;

import io.github.psm0015.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacaoTest {
    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit - Confimar as Alterações
     * Rollback - Desfazer Alterações
     */

    @Test
    @Transactional
    void tansacaoSimples(){
        transacaoService.executar();
    }
}
