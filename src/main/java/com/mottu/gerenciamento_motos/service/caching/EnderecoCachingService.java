package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.dto.EnderecoDTO;
import com.mottu.gerenciamento_motos.mapper.EnderecoMapper;
import com.mottu.gerenciamento_motos.model.Endereco;
import com.mottu.gerenciamento_motos.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoCachingService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    private EnderecoDTO mapearParaDTO(Endereco endereco) {
        return enderecoMapper.toDto(endereco);
    }

    @Cacheable(value = "listarEnderecos")
    public List<EnderecoDTO> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream().map(this::mapearParaDTO).toList();
    }

    @Cacheable(value = "buscarEnderecoPorId", key = "#id")
    public Optional<EnderecoDTO> findById(Long id) {
        return enderecoRepository.findById(id)
                .map(this::mapearParaDTO);
    }

    @Cacheable(value = "paginarFiliais", key = "#pageRequest")
    public Page<EnderecoDTO> paginar(PageRequest pageRequest) {
        Page<Endereco> enderecos = enderecoRepository.findAll(pageRequest);
        return enderecos.map(this::mapearParaDTO);
    }

    @CacheEvict(value = {"listarEnderecos", "paginarFiliais", "buscarEnderecoPorId"}, allEntries = true)
    public void clearCache() {
    }
}
