package com.mottu.gerenciamento_motos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "O nome da filial não pode estar em branco")
    @Size(max = 100, message = "O nome da filial deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_endereco")
    private Endereco endereco;
}
