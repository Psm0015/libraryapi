package io.github.psm0015.libraryapi.mappers;

import io.github.psm0015.libraryapi.dtos.UsuarioDTO;
import io.github.psm0015.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

}
