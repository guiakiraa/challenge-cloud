package com.mottu.gerenciamento_motos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "localizacao")
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "O ponto X é obrigatório")
    @DecimalMin(value = "-180.0", message = "O ponto X (longitude) deve ser maior ou igual a -180")
    @Max(value = 180, message = "O ponto X (longitude) deve ser menor ou igual a 180")
    private double pontoX;

    @NotEmpty(message = "O ponto Y é obrigatório")
    @DecimalMin(value = "-90.0", message = "O ponto Y (latitude) deve ser maior ou igual a -90")
    @Max(value = 90, message = "O ponto Y (latitude) deve ser menor ou igual a 90")
    private double pontoY;

    @NotEmpty(message = "A data e hora da localização são obrigatórias")
    @FutureOrPresent(message = "A data e hora devem ser no presente ou futuro")
    private LocalDateTime dataHora;

    @NotEmpty(message = "A fonte da localização é obrigatória")
    @Enumerated(EnumType.STRING)
    private FonteEnum fonte;

    @NotEmpty(message = "A moto da localização é obrigatória")
    @ManyToOne
    @JoinColumn(name = "fk_moto")
    private Moto moto;
}
