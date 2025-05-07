package io.github.psm0015.libraryapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")
public class Livro {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, name = "isbn", nullable = false)
    private String isbn;

    @Column(length = 150, name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, name = "genero", nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne(
//            cascade = CascadeType.ALL
        fetch = FetchType.LAZY // Não traz os dados do Autor
//            fetch = FetchType.EAGER //traz os dados do Autor(Padrão)
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
