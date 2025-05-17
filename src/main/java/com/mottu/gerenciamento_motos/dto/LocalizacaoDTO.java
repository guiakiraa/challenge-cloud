package com.mottu.gerenciamento_motos.dto;

import com.mottu.gerenciamento_motos.model.FonteEnum;
import com.mottu.gerenciamento_motos.model.Moto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class LocalizacaoDTO {
    private long id;
    private double pontoX;
    private double pontoY;
    private FonteEnum fonte;
    private Moto moto;
}
