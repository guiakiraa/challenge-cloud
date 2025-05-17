package com.mottu.gerenciamento_motos.mapper;

import com.mottu.gerenciamento_motos.dto.FuncionarioDTO;
import com.mottu.gerenciamento_motos.model.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FuncionarioMapper {
    Funcionario toEntity(FuncionarioDTO funcionarioDto);
    FuncionarioDTO toDto(Funcionario funcionario);
}
