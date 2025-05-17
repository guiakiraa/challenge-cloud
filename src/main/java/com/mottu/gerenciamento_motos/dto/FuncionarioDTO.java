package com.mottu.gerenciamento_motos.dto;

import com.mottu.gerenciamento_motos.model.Filial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class FuncionarioDTO {
    private long id;
    private String nome;
    private Filial filial;
}
