package com.mottu.gerenciamento_motos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "O nome do funcionário não pode estar em branco")
    @Size(max = 150, message = "O nome do funcionário deve ter no máximo 150 caracteres")
    private String nome;

    @NotEmpty(message = "A filial do funcionário é obrigatória")
    @ManyToOne
    @JoinColumn(name = "fk_filial")
    private Filial filial;
}
