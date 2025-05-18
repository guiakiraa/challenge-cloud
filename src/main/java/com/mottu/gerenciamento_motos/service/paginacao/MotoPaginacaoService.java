package com.mottu.gerenciamento_motos.service.paginacao;

import com.mottu.gerenciamento_motos.dto.MotoDTO;
import com.mottu.gerenciamento_motos.service.caching.MotoCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MotoPaginacaoService {
    @Autowired
    private MotoCachingService motoCachingService;

    public Page<MotoDTO> paginar(PageRequest pageRequest) {
        return motoCachingService.paginar(pageRequest);
    }
}
