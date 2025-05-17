package com.mottu.gerenciamento_motos.service.paginacao;

import com.mottu.gerenciamento_motos.dto.LocalizacaoDTO;
import com.mottu.gerenciamento_motos.service.caching.LocalizacaoCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LocalizacaoPaginacaoService {

    @Autowired
    private LocalizacaoCachingService localizacaoCachingService;

    public Page<LocalizacaoDTO> paginar(PageRequest pageRequest) {
        return localizacaoCachingService.paginar(pageRequest);
    }
}
