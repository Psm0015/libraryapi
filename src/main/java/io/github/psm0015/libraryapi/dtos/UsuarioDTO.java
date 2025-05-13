package io.github.psm0015.libraryapi.dtos;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
