package com.mottu.gerenciamento_motos.dto;

import com.mottu.gerenciamento_motos.model.Filial;
import com.mottu.gerenciamento_motos.model.ModeloEnum;
import com.mottu.gerenciamento_motos.model.TipoCombustivelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class MotoDTO {
    private String placa;
    private ModeloEnum modelo;
    private int ano;
    private TipoCombustivelEnum tipoCombustivel;
    private Filial filial;
}
