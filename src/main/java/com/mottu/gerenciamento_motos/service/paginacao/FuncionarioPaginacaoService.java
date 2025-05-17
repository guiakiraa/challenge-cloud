package com.mottu.gerenciamento_motos.service.paginacao;

import com.mottu.gerenciamento_motos.dto.FuncionarioDTO;
import com.mottu.gerenciamento_motos.service.caching.FuncionarioCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioPaginacaoService {

    @Autowired
    private FuncionarioCachingService funcionarioCachingService;

    public Page<FuncionarioDTO> paginar(PageRequest pageRequest) {
        return funcionarioCachingService.paginar(pageRequest);
    }
}
