package io.github.psm0015.libraryapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Entity
@Table
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "campo obrigatório")
    @Column(nullable = false, length = 150)
    private String clientId;
    @NotBlank(message = "campo obrigatório")
    @Column(nullable = false, length = 400)
    private String clientSecret;
    @NotBlank(message = "campo obrigatório")
    @Column(nullable = false, length = 200)
    private String redirectURI;
    @Column(length = 50)
    private String scope;

}
