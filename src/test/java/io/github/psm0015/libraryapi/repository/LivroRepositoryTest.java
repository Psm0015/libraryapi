package io.github.psm0015.libraryapi.repository;

import io.github.psm0015.libraryapi.model.Autor;
import io.github.psm0015.libraryapi.model.GeneroLivro;
import io.github.psm0015.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@SpringBootTest
class LivroRepositoryTest {


    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository repository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("45655-54352");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Ciências Espaciais");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = autorRepository
                .findById(UUID.fromString("c89f6bf4-2d10-4517-9df6-3ad06a884bf1"))
                .orElse(null);
        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("45655-54352");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2003, 4, 10));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        Livro livro = repository.findById(UUID.fromString("9648f54a-92de-43a2-b8af-12def09561eb")).orElse(null);
        livro.setAutor(autorRepository.findById(UUID.fromString("74ecd0f3-2740-4681-a9eb-710a48b4227a")).orElse(null));

        Livro save = repository.save(livro);
        System.out.println("Livro atualizado: " + save);

    }

    @Test
    void deleteLivro(){
        repository.deleteById(UUID.fromString("9648f54a-92de-43a2-b8af-12def09561eb"));
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        Livro livro = repository.findById(UUID.fromString("7c926da7-e566-4502-b96f-b412b4f21b53")).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> livros = repository.findByTituloContaining("Eu sei Lá");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPreco(){
        List<Livro> livros = repository.findByTituloAndPreco(
                "Eu sei Lá 2",
                BigDecimal.valueOf(90.00
                ));
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        List<Livro> livros = repository.listarTodosOrdenadoPeloTitulo();
        livros.forEach(System.out::println);
    }
    @Test
    void listarAutoresDosLivrosComQueryJPQL(){
        List<Autor> autores = repository.listarAutoresDosLivros();
        autores.forEach(System.out::println);
    }

    @Test
    void listarTitulosLivrosComQueryJPQL(){
        List<String> titulos = repository.listarNomesLivros();
        titulos.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileirosComQueryJPQL(){
        List<String> generos = repository.listarGenerosAutoresBrasileiros();
        generos.forEach(System.out::println);
    }

    @Test
    void findByGeneroComQueryParamComQueryJPQL(){
        List<Livro> dataPublicacao = repository.findByGenero(GeneroLivro.BIOGRAFIA, "preco");
        dataPublicacao.forEach(System.out::println);
    }
    @Test
    void findByGeneroPositionalParametersComQueryJPQL(){
        List<Livro> dataPublicacao = repository.findByGeneroPositionalParameters(GeneroLivro.BIOGRAFIA, "preco");
        dataPublicacao.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest(){
        repository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest(){
        repository.updateDataPublicacao(UUID.fromString("7c926da7-e566-4502-b96f-b412b4f21b53"), LocalDate.of(2000, 5, 13));
    }

    @Test
    void carregarLivros(){
        List<Autor> autores = autorRepository.findAll();
        List<Livro> livros = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            List<GeneroLivro> generos = List.of(GeneroLivro.values());
            Livro livro = new Livro();

            //GERAR ISBN
            String isbnSemDigito = "978";

            for (int c = 0; c < 9; c++) {
                isbnSemDigito += (int) (Math.random() * 10);
            }

            int soma = 0;

            for (int dv = 0; dv < isbnSemDigito.length(); dv++) {
                int digito = Character.getNumericValue(isbnSemDigito.charAt(dv));
                soma += (dv % 2 == 0) ? digito : digito * 3;
            }

            int resto = soma % 10;
            //GERAR ISBN FIM//

            //GERAR DATA//
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int ano = random.nextInt(1950, 2005);
            int mes = random.nextInt(1, 13);
            int dia = random.nextInt(1, 29);


            livro.setIsbn(isbnSemDigito + ((resto == 0) ? 0 : 10 - resto));
            livro.setTitulo(UUID.randomUUID().toString().substring(0, 8) + " " + UUID.randomUUID().toString().substring(0, 8));
            livro.setDataPublicacao(LocalDate.of(ano, mes, dia));
            livro.setGenero(generos.get(random.nextInt(generos.size())));
            livro.setPreco(BigDecimal.valueOf(random.nextInt(10,200)));
            livro.setAutor(autores.get(random.nextInt(autores.size())));
            livros.add(livro);
        }

        repository.saveAll(livros);

    }


}