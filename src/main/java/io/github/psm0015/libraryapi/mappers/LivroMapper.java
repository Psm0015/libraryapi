package io.github.psm0015.libraryapi.mappers;

import io.github.psm0015.libraryapi.dtos.CadastroLivroDTO;
import io.github.psm0015.libraryapi.dtos.ResultadoPesquisaLivroDTO;
import io.github.psm0015.libraryapi.model.Livro;
import io.github.psm0015.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
