package com.mottu.gerenciamento_motos.controller;

import com.mottu.gerenciamento_motos.dto.FilialDTO;
import com.mottu.gerenciamento_motos.model.Filial;
import com.mottu.gerenciamento_motos.repository.FilialRepository;
import com.mottu.gerenciamento_motos.service.caching.FilialCachingService;
import com.mottu.gerenciamento_motos.service.paginacao.FilialPaginacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/filiais")
public class FilialController {

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private FilialCachingService filialCachingService;

    @Autowired
    private FilialPaginacaoService filialPaginacaoService;

    @GetMapping("/todas")
    public List<FilialDTO> listarFiliais() {
        return filialCachingService.findAll();
    }

    @GetMapping("/paginadas")
    public ResponseEntity<Page<FilialDTO>> paginarFiliais(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size
                                                                                ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FilialDTO> filiaisPaginadas = filialPaginacaoService.paginar(pageRequest);
        return new ResponseEntity<>(filiaisPaginadas, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public FilialDTO buscarFilialPorId(@PathVariable Long id) {
        Optional<FilialDTO> filial = filialCachingService.findById(id);
        if (filial.isPresent()) {
            return filial.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/inserir")
    public Filial salvarFilial(@RequestBody Filial filial) {
        return filialRepository.save(filial);
    }

    @PutMapping(value = "/atualizar/{id}")
    public Filial atualizarFilial(@PathVariable Long id, @RequestBody Filial filial) {
        Optional<Filial> filialOptional = filialRepository.findById(id);
        if (filialOptional.isPresent()) {
            Filial filialAtual = filialOptional.get();
            filialAtual.setNome(filial.getNome());
            filialRepository.save(filialAtual);
            filialCachingService.clearCache();
            return filialAtual;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remover/{id}")
    public Filial removerFilial(@PathVariable Long id) {
        Optional<Filial> filial = filialRepository.findById(id);
        if (filial.isPresent()) {
            filialRepository.delete(filial.get());
            filialCachingService.clearCache();
            return filial.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
