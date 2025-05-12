package com.mottu.gerenciamento_motos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
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
    private double pontoX;
    private double pontoY;
    @PastOrPresent(message = "Data da localização inválida")
    private LocalDateTime dataHora;
    @Enumerated(EnumType.STRING)
    private FonteEnum fonte;
    @ManyToOne
    @JoinColumn(name = "id_moto")
    private Moto moto;
}
