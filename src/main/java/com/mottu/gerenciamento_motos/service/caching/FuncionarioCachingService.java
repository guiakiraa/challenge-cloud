package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.dto.FilialDTO;
import com.mottu.gerenciamento_motos.dto.FuncionarioDTO;
import com.mottu.gerenciamento_motos.mapper.FilialMapper;
import com.mottu.gerenciamento_motos.mapper.FuncionarioMapper;
import com.mottu.gerenciamento_motos.model.Filial;
import com.mottu.gerenciamento_motos.model.Funcionario;
import com.mottu.gerenciamento_motos.repository.FilialRepository;
import com.mottu.gerenciamento_motos.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioCachingService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    private FuncionarioDTO mapearParaDTO(Funcionario funcionario) {
        return funcionarioMapper.toDto(funcionario);
    }

    @Cacheable(value = "listarFuncionarios")
    public List<FuncionarioDTO> findAll() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream().map(this::mapearParaDTO).toList();
    }

    @Cacheable(value = "buscarFuncionarioPorId", key = "#id")
    public Optional<FuncionarioDTO> findById(Long id) {
        return funcionarioRepository.findById(id)
                .map(this::mapearParaDTO);
    }

    @Cacheable(value = "paginarFuncionarios", key = "#pageRequest")
    public Page<FuncionarioDTO> paginar(PageRequest pageRequest) {
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageRequest);
        return funcionarios.map(this::mapearParaDTO);
    }

    @CacheEvict(value = {"listarFuncionarios", "paginarFuncionarios", "buscarFuncionarioPorId"}, allEntries = true)
    public void clearCache() {
    }
}
