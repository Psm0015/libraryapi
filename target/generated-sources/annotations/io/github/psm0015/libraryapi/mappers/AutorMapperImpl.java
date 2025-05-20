package io.github.psm0015.libraryapi.mappers;

import io.github.psm0015.libraryapi.dtos.AutorDTO;
import io.github.psm0015.libraryapi.model.Autor;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T11:26:35-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class AutorMapperImpl implements AutorMapper {

    @Override
    public Autor toEntity(AutorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Autor autor = new Autor();

        autor.setId( dto.id() );
        autor.setNome( dto.nome() );
        autor.setDataNascimento( dto.dataNascimento() );
        autor.setNacionalidade( dto.nacionalidade() );

        return autor;
    }

    @Override
    public AutorDTO toDto(Autor autor) {
        if ( autor == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        LocalDate dataNascimento = null;
        String nacionalidade = null;

        id = autor.getId();
        nome = autor.getNome();
        dataNascimento = autor.getDataNascimento();
        nacionalidade = autor.getNacionalidade();

        AutorDTO autorDTO = new AutorDTO( id, nome, dataNascimento, nacionalidade );

        return autorDTO;
    }
}
