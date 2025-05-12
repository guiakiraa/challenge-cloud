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
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    @Size(min = 3, max = 250, message = "Valor inválido para logradouro. Ele precisa ter de 3 a 250 caracteres")
    private String logradouro;
    @Min(value = 1, message = "Valor inválid para número do logradouro. Ele deve ser maior que 0")
    private int numero;
    @NotEmpty
    @Size(min = 3, max = 50, message = "Valor inválido para bairro. Ele precisa ter de 3 a 50 caracteres")
    private String bairro;
    @NotEmpty
    @Size(min = 3, max = 50, message = "Valor inválido para cidade. Ele precisa ter de 3 a 50 caracteres")
    private String cidade;
    @NotEmpty
    @Size(min = 3, max = 50, message = "Valor inválido para estado. Ele precisa ter de 3 a 50 caracteres")
    private String estado;
    @NotEmpty
    @Size(min = 8, max = 9, message = "Valor inválido para CEP. Ele precisa ter de 8 a 9 caracteres")
    private String cep;
    @Size(min = 1, max = 100, message = "Valor inválido para complemento. Ele precisa ter de 1 a 100 caracteres")
    private String complemento;
}
