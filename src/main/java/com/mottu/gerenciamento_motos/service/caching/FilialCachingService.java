package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.dto.FilialDTO;
import com.mottu.gerenciamento_motos.mapper.FilialMapper;
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

    @Autowired
    private FilialMapper filialMapper;

    private FilialDTO mapearParaDTO(Filial filial) {
        return filialMapper.toDto(filial);
    }

    @Cacheable(value = "listarFiliais")
    public List<FilialDTO> findAll() {
        List<Filial> filials = filialRepository.findAll();
        return filials.stream().map(this::mapearParaDTO).toList();
    }

    @Cacheable(value = "buscarFilialPorId", key = "#id")
    public Optional<FilialDTO> findById(Long id) {
        return filialRepository.findById(id)
                .map(this::mapearParaDTO);
    }

    @Cacheable(value = "paginarFiliais", key = "#pageRequest")
    public Page<FilialDTO> paginar(PageRequest pageRequest) {
        Page<Filial> filials = filialRepository.findAll(pageRequest);
        return filials.map(this::mapearParaDTO);
    }

    @Cacheable(value = "buscarFiliaisDaCidade", key = "#cidade")
    public List<FilialDTO> findFiliaisByCidade(String cidade) {
        List<Filial> filials = filialRepository.findFiliaisDaCidade(cidade);
        return filials.stream().map(this::mapearParaDTO).toList();
    }

    @CacheEvict(value = {"listarFiliais", "paginarFiliais", "buscarFilialPorId",
            "buscarFiliaisDaCidade"}, allEntries = true)
    public void clearCache() {
    }
}
