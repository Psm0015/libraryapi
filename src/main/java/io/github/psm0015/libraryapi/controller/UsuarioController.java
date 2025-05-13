package io.github.psm0015.libraryapi.controller;

import io.github.psm0015.libraryapi.dtos.UsuarioDTO;
import io.github.psm0015.libraryapi.mappers.UsuarioMapper;
import io.github.psm0015.libraryapi.model.Usuario;
import io.github.psm0015.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }

}
