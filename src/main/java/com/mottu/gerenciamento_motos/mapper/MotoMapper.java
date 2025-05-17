package com.mottu.gerenciamento_motos.mapper;

import com.mottu.gerenciamento_motos.dto.MotoDTO;
import com.mottu.gerenciamento_motos.model.Moto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MotoMapper {
    MotoDTO toDto(Moto moto);
    Moto toEntity(MotoDTO motoDto);
}
