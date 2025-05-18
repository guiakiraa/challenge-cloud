package com.mottu.gerenciamento_motos.controller;

import com.mottu.gerenciamento_motos.dto.LocalizacaoDTO;
import com.mottu.gerenciamento_motos.model.Localizacao;
import com.mottu.gerenciamento_motos.projection.LocalizacaoMotoProjection;
import com.mottu.gerenciamento_motos.repository.LocalizacaoRepository;
import com.mottu.gerenciamento_motos.service.caching.LocalizacaoCachingService;
import com.mottu.gerenciamento_motos.service.paginacao.LocalizacaoPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/localizacoes")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoRepository repository;

    @Autowired
    private LocalizacaoCachingService cachingService;

    @Autowired
    private LocalizacaoPaginacaoService paginacaoService;

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas as localizações"
    )
    @GetMapping()
    public List<LocalizacaoDTO> listar() {
        return cachingService.findAll();
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas os localizações e exibe em forma de páginas. Evitando muitos resultados de um vez"
    )
    @GetMapping("/paginadas")
    public ResponseEntity<Page<LocalizacaoDTO>> paginar(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LocalizacaoDTO> localizacoesPaginadas = paginacaoService.paginar(pageRequest);
        return new ResponseEntity<>(localizacoesPaginadas, HttpStatus.OK);
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca uma localização pelo ID"
    )
    @GetMapping(value = "/{id}")
    public LocalizacaoDTO buscarPorId(@PathVariable Long id) {
        Optional<LocalizacaoDTO> localizacao = cachingService.findById(id);
        if (localizacao.isPresent()) {
            return localizacao.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Inserção de informação",
            summary = "Cadastra uma nova localização"
    )
    @PostMapping("/inserir")
    public Localizacao salvar(@RequestBody Localizacao localizacao) {
        return repository.save(localizacao);
    }

    @Operation(
            tags = "Atualização de informação",
            summary = "Atualiza uma localização já existente pelo ID"
    )
    @PutMapping(value = "/atualizar/{id}")
    public Localizacao atualizar(@PathVariable Long id, @RequestBody Localizacao localizacao) {
        Optional<Localizacao> localizacaoOptional = repository.findById(id);
        if (localizacaoOptional.isPresent()) {
            Localizacao localizacaoAtual = localizacaoOptional.get();
            localizacaoAtual.setPontoY(localizacao.getPontoY());
            localizacaoAtual.setPontoX(localizacao.getPontoX());
            localizacaoAtual.setFonte(localizacao.getFonte());
            localizacaoAtual.setDataHora(localizacao.getDataHora());
            repository.save(localizacaoAtual);
            cachingService.clearCache();
            return localizacaoAtual;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Remoção de informação",
            summary = "Remove uma localização pelo ID"
    )
    @DeleteMapping("/remover/{id}")
    public Localizacao remover(@PathVariable Long id) {
        Optional<Localizacao> localizacao = repository.findById(id);
        if (localizacao.isPresent()) {
            repository.delete(localizacao.get());
            cachingService.clearCache();
            return localizacao.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca a última localização de uma moto específica"
    )
    @GetMapping("/ultimaLocalizacaoMoto/{idMoto}")
    public LocalizacaoMotoProjection buscarUltimaLocalizacaoMoto(Long idMoto) {
        LocalizacaoMotoProjection localizacaoMotoProjection = repository.findUltimaLocalizacaoDaMoto(idMoto);
        if (localizacaoMotoProjection != null) {
            return localizacaoMotoProjection;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca a últimas localizações de todas as motos"
    )
    @GetMapping("/ultimasLocalizacoesMotos")
    public List<LocalizacaoMotoProjection> buscarUltimasLocalizacoesMotos() {
        return repository.findUltimasLocalizacoesDeTodasAsMotos();
    }
}
