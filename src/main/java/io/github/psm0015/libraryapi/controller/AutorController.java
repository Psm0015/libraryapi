package io.github.psm0015.libraryapi.controller;

import io.github.psm0015.libraryapi.dtos.AutorDTO;
import io.github.psm0015.libraryapi.mappers.AutorMapper;
import io.github.psm0015.libraryapi.model.Autor;
import io.github.psm0015.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE','OPERADOR')")
    public ResponseEntity<AutorDTO> detalhar(@PathVariable String id) {
        Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));

        return autorService.obterPorId(UUID.fromString(id)).map(autor -> {
            AutorDTO dto = autorMapper.toDto(autor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {

        log.info("Cadastrando novo autor: {}", dto.nome());

        Autor autor = autorMapper.toEntity(dto);

        autorService.salvar(autor);

        URI uri = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> deletar(@PathVariable("id") String id) {
        log.info("Deletando autor de id: {}", id);
        Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            autorService.deletar(autor);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','OPERADOR')")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

//        log.trace("Pesquisa autores trace");
//        log.debug("Pesquisa autores debug");
//        log.info("Pesquisa autores info");
//        log.warn("Pesquisa autores warn");
//        log.error("Pesquisa autores error");

        return ResponseEntity.ok(
                autorService.pesquisaByExample(nome, nacionalidade)
                        .stream()
                        .map(autorMapper::toDto)
                        .collect(Collectors.toList()));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        log.info("Pesquisando autor de id: {}", id);
        Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }

}
