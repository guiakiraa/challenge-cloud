package com.mottu.gerenciamento_motos.mapper;

import com.mottu.gerenciamento_motos.dto.FilialDTO;
import com.mottu.gerenciamento_motos.model.Filial;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FilialMapper {
    FilialDTO toDto(Filial filial);
    Filial toEntity(FilialDTO filialDto);
}
