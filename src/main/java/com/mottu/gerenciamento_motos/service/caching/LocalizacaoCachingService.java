package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.dto.LocalizacaoDTO;
import com.mottu.gerenciamento_motos.mapper.LocalizacaoMapper;
import com.mottu.gerenciamento_motos.model.Localizacao;
import com.mottu.gerenciamento_motos.projection.LocalizacaoMotoProjection;
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

    @Autowired
    private LocalizacaoMapper localizacaoMapper;

    private LocalizacaoDTO mapearParaDTO(Localizacao localizacao) {
        return localizacaoMapper.toDto(localizacao);
    }

    @Cacheable(value = "listarLocalizacoes")
    public List<LocalizacaoDTO> findAll() {
        List<Localizacao> localizacoes = localizacaoRepository.findAll();
        return localizacoes.stream().map(this::mapearParaDTO).toList();
    }

    @Cacheable(value = "buscarLocalizacaoPorId", key = "#id")
    public Optional<LocalizacaoDTO> findById(Long id) {
        return localizacaoRepository.findById(id)
                .map(this::mapearParaDTO);
    }

    @Cacheable(value = "paginarLocalizacoes", key = "#pageRequest")
    public Page<LocalizacaoDTO> paginar(PageRequest pageRequest) {
        Page<Localizacao> localizacoes = localizacaoRepository.findAll(pageRequest);
        return localizacoes.map(this::mapearParaDTO);
    }

    @Cacheable(value = "buscarUltimaLocalizacaoDaMoto", key = "#idMoto")
    public LocalizacaoMotoProjection findUltimaLocalizacaoDaMoto(Long idMoto) {
        return localizacaoRepository.findUltimaLocalizacaoDaMoto(idMoto);
    }

    @CacheEvict(value = {"listarLocalizacoes", "paginarLocalizacoes", "buscarLocalizacaoPorId",
            "buscarUltimaLocalizacaoDaMoto"}, allEntries = true)
    public void clearCache() {
    }
}
