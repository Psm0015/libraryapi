package io.github.psm0015.libraryapi.controller;

import io.github.psm0015.libraryapi.dtos.CadastroLivroDTO;
import io.github.psm0015.libraryapi.dtos.ErroResposta;
import io.github.psm0015.libraryapi.dtos.ResultadoPesquisaLivroDTO;
import io.github.psm0015.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.psm0015.libraryapi.mappers.LivroMapper;
import io.github.psm0015.libraryapi.model.Livro;
import io.github.psm0015.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> detalhar(@PathVariable String id){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoPesquisaLivroDTO dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);
        URI uri = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(uri).build();
    }

}
