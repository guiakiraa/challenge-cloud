package com.mottu.gerenciamento_motos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "moto")
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "A placa da moto não pode estar em branco")
    @Pattern(regexp = "[A-Z]{3}\\d[A-Z]\\d{2}|[A-Z]{3}\\d{4}", message = "A placa deve estar no formato antigo (AAA1234) ou Mercosul (AAA1A23)")
    private String placa;

    @Min(value = 1900, message = "O ano da moto deve ser igual ou superior a 1900")
    @Max(value = 2100, message = "O ano da moto deve ser igual ou inferior a 2025")
    private int ano;

    @NotEmpty(message = "O tipo de combustível é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoCombustivelEnum tipoCombustivel;

    @NotEmpty(message = "A filial da moto é obrigatória")
    @ManyToOne
    @JoinColumn(name = "fk_filial")
    private Filial filial;
}
