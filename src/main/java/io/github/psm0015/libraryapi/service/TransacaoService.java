package io.github.psm0015.libraryapi.service;

import io.github.psm0015.libraryapi.model.Autor;
import io.github.psm0015.libraryapi.model.GeneroLivro;
import io.github.psm0015.libraryapi.model.Livro;
import io.github.psm0015.libraryapi.repository.AutorRepository;
import io.github.psm0015.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    public void  atualizacaoSemAtualizar(){
    //
    }
    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Lucas");
        autor.setNacionalidade("Espanhola");
        autor.setDataNascimento(LocalDate.of(1989, 3, 20));

        autor = autorRepository.save(autor);


        Livro livro = new Livro();
        livro.setIsbn("54698-48623");
        livro.setPreco(BigDecimal.valueOf(52));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("O cara que sei lá");
        livro.setDataPublicacao(LocalDate.of(2024,6,12));

        if(autor.getNome().equals("Lucas")){
            throw new RuntimeException("RollBack!");
        }

    }


}
