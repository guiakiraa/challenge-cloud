package com.mottu.gerenciamento_motos.mapper;

import com.mottu.gerenciamento_motos.dto.EnderecoDTO;
import com.mottu.gerenciamento_motos.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EnderecoMapper {
    EnderecoDTO toDto(Endereco endereco);
    Endereco toEntity(EnderecoDTO enderecoDto);
}
