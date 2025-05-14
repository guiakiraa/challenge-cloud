package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.model.Moto;
import com.mottu.gerenciamento_motos.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoCachingService {

    @Autowired
    private MotoRepository motoRepository;

    @Cacheable(value = "findAll")
    public List<Moto> findAll() {
        return motoRepository.findAll();
    }

    @Cacheable(value = "findAllPaginada", key = "#pageRequest")
    public Page<Moto> paginar(PageRequest pageRequest) {
        return motoRepository.findAll(pageRequest);
    }

    @Cacheable(value = "findById", key = "#id")
    public Optional<Moto> findById(Long id) {
        return motoRepository.findById(id);
    }

    @CacheEvict(value = {"findAll", "paginar", "findById"}, allEntries = true)
    public void clearCache() {
    }
}
