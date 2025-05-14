package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.model.Funcionario;
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

    @Cacheable(value = "findAll")
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    @Cacheable(value = "findAllPaginada", key = "#pageRequest")
    public Page<Funcionario> paginar(PageRequest pageRequest) {
        return funcionarioRepository.findAll(pageRequest);
    }

    @Cacheable(value = "findById", key = "#id")
    public Optional<Funcionario> findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @CacheEvict(value = {"findAll", "paginar", "findById"}, allEntries = true)
    public void clearCache() {
    }
}
