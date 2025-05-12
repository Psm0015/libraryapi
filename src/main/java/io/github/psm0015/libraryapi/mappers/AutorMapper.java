package io.github.psm0015.libraryapi.mappers;

import io.github.psm0015.libraryapi.dtos.AutorDTO;
import io.github.psm0015.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);

}
