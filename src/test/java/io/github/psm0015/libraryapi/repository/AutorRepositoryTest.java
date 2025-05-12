package io.github.psm0015.libraryapi.repository;

import io.github.psm0015.libraryapi.model.Autor;
import io.github.psm0015.libraryapi.model.GeneroLivro;
import io.github.psm0015.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Milene");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2003, 4, 10));

        autor = repository.save(autor);

        System.out.println(autor);

    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("24af96b5-54c6-4168-9dbc-d1d7b025e8eb");

        Optional<Autor> autor = repository.findById(id);

        if(autor.isPresent()){
            System.out.println("Dados do Autor");
            System.out.println(autor.get());

            Autor autorE = autor.get();
            autorE.setDataNascimento(LocalDate.of(2003,6,29));
            autorE = repository.save(autorE);
            System.out.println("Autor Atualizado");
            System.out.println(autorE);

        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());

    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("24af96b5-54c6-4168-9dbc-d1d7b025e8eb");
        repository.deleteById(id);
    }

    @Test
    public void deletePorObjTest(){
        var id = UUID.fromString("82783436-199b-45d2-8b91-a2c2973b2548");
        Autor autor = repository.findById(id).get();
        repository.delete(autor);
    }

    @Test
    void SalvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Pedro Mota");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2003, 6, 29));

        Livro livro = new Livro();
        livro.setIsbn("54675-56782");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Eu sei Lá");
        livro.setDataPublicacao(LocalDate.of(2025, 5,7));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("15935-54562");
        livro2.setPreco(BigDecimal.valueOf(90));
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setTitulo("Eu sei Lá 2");
        livro2.setDataPublicacao(LocalDate.of(2025, 5,6));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    void listarLivrosAutor(){
        Autor autor = repository.findById(UUID.fromString("5e66c6be-d614-4ac8-a835-267c38d78ab5")).get();
        autor.setLivros(livroRepository.findByAutor(autor));
        autor.getLivros().forEach(System.out::println);

    }

    @Test
    void carregarAutores() {
        List<String> sobrenomes = List.of(
                "Silva", "Souza", "Oliveira", "Santos", "Pereira", "Lima",
                "Costa", "Ferreira", "Almeida", "Gomes", "Rodrigues", "Martins",
                "Barbosa", "Ribeiro", "Carvalho", "Monteiro", "Cardoso", "Moura",
                "Batista", "Moreira"
        );

        List<Autor> autores = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {
            // Gera nome composto com dois sobrenomes diferentes
            String primeiro = sobrenomes.get(random.nextInt(sobrenomes.size()));
            String segundo;
            do {
                segundo = sobrenomes.get(random.nextInt(sobrenomes.size()));
            } while (segundo.equals(primeiro));
            String nomeCompleto = primeiro + " " + segundo;

            // Gera uma data de nascimento entre 1950 e 2004 (inclusive)
            int ano = random.nextInt(1950, 2005);
            int mes = random.nextInt(1, 13);
            int dia = random.nextInt(1, 29);

            Autor autor = new Autor();
            autor.setNome(nomeCompleto);
            autor.setNacionalidade("Brasileira");
            autor.setDataNascimento(LocalDate.of(ano, mes, dia));

            autores.add(autor);
        }

        repository.saveAll(autores);



    }


}
