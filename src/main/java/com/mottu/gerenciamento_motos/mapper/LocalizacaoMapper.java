package com.mottu.gerenciamento_motos.mapper;

import com.mottu.gerenciamento_motos.dto.LocalizacaoDTO;
import com.mottu.gerenciamento_motos.model.Localizacao;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LocalizacaoMapper {
    LocalizacaoDTO toDTO(Localizacao localizacao);
    Localizacao toEntity(LocalizacaoDTO localizacaoDto);
}
