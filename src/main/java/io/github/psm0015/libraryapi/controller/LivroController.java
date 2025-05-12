package io.github.psm0015.libraryapi.controller;

import io.github.psm0015.libraryapi.dtos.CadastroLivroDTO;
import io.github.psm0015.libraryapi.dtos.ErroResposta;
import io.github.psm0015.libraryapi.dtos.ResultadoPesquisaLivroDTO;
import io.github.psm0015.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.psm0015.libraryapi.mappers.LivroMapper;
import io.github.psm0015.libraryapi.model.GeneroLivro;
import io.github.psm0015.libraryapi.model.Livro;
import io.github.psm0015.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id ){
        return livroService.obterPorId(UUID.fromString(id)).map(
                livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }
        ).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(required = false) GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10", value = "page-size") Integer pageSize
    ){
        Page<Livro> paginaResultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, pageSize);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entity = livroMapper.toEntity(dto);
                    livro.setDataPublicacao(entity.getDataPublicacao());
                    livro.setIsbn(entity.getIsbn());
                    livro.setPreco(entity.getPreco());
                    livro.setGenero(entity.getGenero());
                    livro.setTitulo(entity.getTitulo());
                    livro.setAutor(entity.getAutor());
                    livroService.atulizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

}
