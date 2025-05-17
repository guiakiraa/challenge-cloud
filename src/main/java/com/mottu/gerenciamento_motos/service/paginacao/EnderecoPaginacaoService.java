package com.mottu.gerenciamento_motos.service.paginacao;

import com.mottu.gerenciamento_motos.dto.EnderecoDTO;
import com.mottu.gerenciamento_motos.service.caching.EnderecoCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoPaginacaoService {

    @Autowired
    private EnderecoCachingService enderecoCachingService;

    @Transactional(readOnly = true)
    public Page<EnderecoDTO> paginar(PageRequest pageRequest) {
        return enderecoCachingService.paginar(pageRequest);
    }
}
