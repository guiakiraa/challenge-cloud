package com.mottu.gerenciamento_motos.dto;

import com.mottu.gerenciamento_motos.model.Filial;
import com.mottu.gerenciamento_motos.model.ModeloEnum;
import com.mottu.gerenciamento_motos.model.TipoCombustivelEnum;

public class MotoDTO {
    private long id;

    private String placa;

    private ModeloEnum modelo;

    private int ano;

    private TipoCombustivelEnum tipoCombustivel;

    private Filial filial;
}
