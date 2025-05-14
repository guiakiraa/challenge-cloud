package com.mottu.gerenciamento_motos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "Valor inválido para placa. Ela não pode ser vazio")
    @Size(min = 7, max = 8, message = "Valor inválido para placa. Ela precisa ter de 7 a 8 caracteres (XXX1X11 ou XXX-1111)")
    private String placa;
    @Enumerated(EnumType.STRING)
    private ModeloEnum modelo;
    @Min(value = 2019, message = "Valor inválido para ano da moto. Ela precisa ser maior que 2019 pois a Mottu foi fundada em 2020 ")
    private int ano;
    @Enumerated(EnumType.STRING)
    private TipoCombustivelEnum tipoCombustivel;
    @ManyToOne
    @JoinColumn(name = "fk_filial")
    private Filial filial;

}
