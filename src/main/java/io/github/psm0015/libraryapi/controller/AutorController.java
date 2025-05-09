package io.github.psm0015.libraryapi.controller;

import io.github.psm0015.libraryapi.dtos.AutorDTO;
import io.github.psm0015.libraryapi.dtos.ErroResposta;
import io.github.psm0015.libraryapi.exceptions.OperacaoNaoPermitidaExeption;
import io.github.psm0015.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.psm0015.libraryapi.model.Autor;
import io.github.psm0015.libraryapi.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> detalhar(@PathVariable String id){
        Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor){
        try{
            Autor autorEntity = autor.convertAutor();
            autorService.salvar(autorEntity);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntity.getId()).toUri();

            return ResponseEntity.created(uri).build();
        }catch (RegistroDuplicadoException e){
            ErroResposta erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") String id){
        try {
            Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));
            if (autorOptional.isPresent()) {
                Autor autor = autorOptional.get();
                autorService.deletar(autor);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (OperacaoNaoPermitidaExeption e) {
            ErroResposta erroDTO = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        return ResponseEntity.ok(
                autorService.pesquisar(nome, nacionalidade)
                        .stream()
                        .map(autor -> new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        )).collect(Collectors.toList()));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto){
        try {
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
        } catch (RegistroDuplicadoException e) {
            ErroResposta erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

}
