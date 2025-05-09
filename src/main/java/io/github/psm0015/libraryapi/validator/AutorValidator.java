package io.github.psm0015.libraryapi.validator;

import io.github.psm0015.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.psm0015.libraryapi.model.Autor;
import io.github.psm0015.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        if(autorEncontrado.isPresent()){
            return !autor.getId().equals(autorEncontrado.get().getId());
        }
        return false;

    }

}
