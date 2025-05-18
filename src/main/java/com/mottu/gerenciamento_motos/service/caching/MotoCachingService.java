package com.mottu.gerenciamento_motos.service.caching;

import com.mottu.gerenciamento_motos.dto.LocalizacaoDTO;
import com.mottu.gerenciamento_motos.dto.MotoDTO;
import com.mottu.gerenciamento_motos.mapper.LocalizacaoMapper;
import com.mottu.gerenciamento_motos.mapper.MotoMapper;
import com.mottu.gerenciamento_motos.model.Localizacao;
import com.mottu.gerenciamento_motos.model.Moto;
import com.mottu.gerenciamento_motos.projection.MotoProjection;
import com.mottu.gerenciamento_motos.repository.LocalizacaoRepository;
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

    @Autowired
    private MotoMapper motoMapper;

    private MotoDTO mapearParaDTO(Moto moto) {
        return motoMapper.toDto(moto);
    }

    @Cacheable(value = "listarMotos")
    public List<MotoDTO> findAll() {
        List<Moto> motos = motoRepository.findAll();
        return motos.stream().map(this::mapearParaDTO).toList();
    }

    @Cacheable(value = "buscarMotoPorId", key = "#id")
    public Optional<MotoDTO> findById(Long id) {
        return motoRepository.findById(id)
                .map(this::mapearParaDTO);
    }

    @Cacheable(value = "paginarMotos", key = "#pageRequest")
    public Page<MotoDTO> paginar(PageRequest pageRequest) {
        Page<Moto> motos = motoRepository.findAll(pageRequest);
        return motos.map(this::mapearParaDTO);
    }

    @Cacheable(value = "buscarMotosDaFilial", key = "#idFilial")
    public List<MotoProjection> findMotosByFilialId(Long idFilial) {
        return motoRepository.findMotosByFilialId(idFilial);
    }

    @CacheEvict(value = {"listarMotos", "paginarMotos", "buscarMotoPorId"}, allEntries = true)
    public void clearCache() {
    }
}
