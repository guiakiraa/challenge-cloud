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
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Valor inválido para nome. Ele não pode ser vazio")
    @Size(min = 1, max = 100, message = "Valor inválido para nome. Ele precisa ter de 1 a 100 caracteres")
    private String nome;
    @ManyToOne
    @JoinColumn(name = "fk_endereco")
    private Endereco endereco;
}
