package io.github.psm0015.libraryapi.service;

import io.github.psm0015.libraryapi.exceptions.OperacaoNaoPermitidaExeption;
import io.github.psm0015.libraryapi.model.Autor;
import io.github.psm0015.libraryapi.model.Usuario;
import io.github.psm0015.libraryapi.repository.AutorRepository;
import io.github.psm0015.libraryapi.repository.LivroRepository;
import io.github.psm0015.libraryapi.security.SecurityService;
import io.github.psm0015.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }
    public void atualizar(Autor autor){
        validator.validar(autor);
        if(autor.getId() == null) throw new IllegalArgumentException("Para atualizar é necessário que o autor ja esteja salvo na base");

        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaExeption(
                    "Não é permitido excluir um Autor que possui livros cadastrados!");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome,nacionalidade);
        }else if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }else if(nome != null){
            return autorRepository.findByNome(nome);
        }
        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
