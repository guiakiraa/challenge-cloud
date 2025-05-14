package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.model.Localizacao;
import com.mottu.gerenciamento_motos.repository.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalizacaoCachingService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Cacheable(value = "findAll")
    public List<Localizacao> findAll() {
        return localizacaoRepository.findAll();
    }

    @Cacheable(value = "findAllPaginada", key = "#pageRequest")
    public Page<Localizacao> paginar(PageRequest pageRequest) {
        return localizacaoRepository.findAll(pageRequest);
    }

    @Cacheable(value = "findById", key = "#id")
    public Optional<Localizacao> findById(Long id) {
        return localizacaoRepository.findById(id);
    }

    @CacheEvict(value = {"findAll", "paginar", "findById"}, allEntries = true)
    public void clearCache() {
    }
}
