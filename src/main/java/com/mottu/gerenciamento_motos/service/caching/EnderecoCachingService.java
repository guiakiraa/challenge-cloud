package com.mottu.gerenciamento_motos.service.caching;

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

    @Cacheable(value = "findAll")
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    @Cacheable(value = "findAllPaginada", key = "#pageRequest")
    public Page<Endereco> paginar(PageRequest pageRequest) {
        return enderecoRepository.findAll(pageRequest);
    }

    @Cacheable(value = "findById", key = "#id")
    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    @CacheEvict(value = {"findAll", "paginar", "findById"}, allEntries = true)
    public void clearCache() {
    }
}
