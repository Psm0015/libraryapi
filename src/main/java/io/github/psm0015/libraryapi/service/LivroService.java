package io.github.psm0015.libraryapi.service;

import io.github.psm0015.libraryapi.model.Livro;
import io.github.psm0015.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID uuid) {
        return livroRepository.findById(uuid);
    }
}
