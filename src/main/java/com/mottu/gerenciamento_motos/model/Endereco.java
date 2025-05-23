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
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "O logradouro não pode estar em branco")
    @Size(max = 255, message = "O logradouro deve ter no máximo 255 caracteres")
    private String logradouro;

    @Min(value = 1, message = "O número deve ser no mínimo 1")
    @Max(value = 99999, message = "O número deve ter no máximo 5 dígitos")
    private int numero;

    @NotEmpty(message = "O bairro não pode estar em branco")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @NotEmpty(message = "A cidade não pode estar em branco")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotEmpty(message = "O estado não pode estar em branco")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres (UF)")
    @Pattern(regexp = "[A-Z]{2}", message = "O estado deve conter apenas letras maiúsculas (UF)")
    private String estado;

    @NotEmpty(message = "O CEP não pode estar em branco")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato XXXXX-XXX")
    private String cep;

    @Size(max = 255, message = "O complemento deve ter no máximo 255 caracteres")
    private String complemento;
}

