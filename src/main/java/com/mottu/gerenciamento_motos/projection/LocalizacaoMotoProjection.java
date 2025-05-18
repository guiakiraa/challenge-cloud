package com.mottu.gerenciamento_motos.projection;

import java.time.LocalDateTime;

public interface LocalizacaoMotoProjection {
    String getPlaca();
    String getModelo();
    double getPontoX();
    double getPontoY();
    LocalDateTime getDataHora();
}
