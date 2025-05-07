package io.github.psm0015.libraryapi.repository;

import java.util.UUID;
import io.github.psm0015.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
