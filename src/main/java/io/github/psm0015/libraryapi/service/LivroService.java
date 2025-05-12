package io.github.psm0015.libraryapi.service;

import io.github.psm0015.libraryapi.model.GeneroLivro;
import io.github.psm0015.libraryapi.model.Livro;
import io.github.psm0015.libraryapi.repository.LivroRepository;
import io.github.psm0015.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.psm0015.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID uuid) {
        return livroRepository.findById(uuid);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao, Integer pagina, Integer pageSize){

        Specification<Livro> specs = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));
        if(isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }
        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }
        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }
        if(nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, pageSize);

        return livroRepository.findAll(specs, pageRequest);
    }

    public void atulizar(Livro livro) {
        livroValidator.validar(livro);
        if(livro.getId() == null) throw new IllegalArgumentException("Para atualizar é necessário que o autor ja esteja salvo na base");
        livroRepository.save(livro);
    }

}
