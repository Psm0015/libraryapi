package io.github.psm0015.libraryapi.dtos;

import io.github.psm0015.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 100, min = 2, message = "O campo tem q ter pelomenos 2 caracteres e no máximo 100")
        String nome,
        @NotNull(message = "Campo obrigatório")
        @Past(message = "A data não pode ser no futuro")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 50, min = 2, message = "O campo tem q ter pelomenos 2 caracteres e no máximo 50")
        String nacionalidade) {
    public Autor convertAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
