package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.model.Filial;
import com.mottu.gerenciamento_motos.repository.FilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilialCachingService {
    @Autowired
    private FilialRepository filialRepository;

    @Cacheable(value = "findAll")
    public List<Filial> findAll() {
        return filialRepository.findAll();
    }

    @Cacheable(value = "findAllPaginada", key = "#pageRequest")
    public Page<Filial> paginar(PageRequest pageRequest) {
        return filialRepository.findAll(pageRequest);
    }

    @Cacheable(value = "findById", key = "#id")
    public Optional<Filial> findById(Long id) {
        return filialRepository.findById(id);
    }

    @CacheEvict(value = {"findAll", "paginar", "findById"}, allEntries = true)
    public void clearCache() {
    }
}
