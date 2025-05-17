package com.mottu.gerenciamento_motos.service.paginacao;

import com.mottu.gerenciamento_motos.dto.FilialDTO;
import com.mottu.gerenciamento_motos.service.caching.FilialCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilialPaginacaoService {
    @Autowired
    private FilialCachingService filialCachingService;

    @Transactional(readOnly = true)
    public Page<FilialDTO> paginar(PageRequest pageRequest) {
        return filialCachingService.paginar(pageRequest);
    }
}
