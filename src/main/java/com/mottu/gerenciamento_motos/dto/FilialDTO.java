package com.mottu.gerenciamento_motos.dto;

import com.mottu.gerenciamento_motos.model.Endereco;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class FilialDTO {
    private long id;
    private String nome;
    private Endereco endereco;
}
