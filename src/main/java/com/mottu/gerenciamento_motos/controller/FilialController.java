package com.mottu.gerenciamento_motos.controller;

import com.mottu.gerenciamento_motos.dto.FilialDTO;
import com.mottu.gerenciamento_motos.model.Filial;
import com.mottu.gerenciamento_motos.repository.FilialRepository;
import com.mottu.gerenciamento_motos.service.caching.FilialCachingService;
import com.mottu.gerenciamento_motos.service.paginacao.FilialPaginacaoService;
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
@RequestMapping(value = "/filiais")
public class FilialController {

    @Autowired
    private FilialRepository repository;

    @Autowired
    private FilialCachingService cachingService;

    @Autowired
    private FilialPaginacaoService paginacaoService;

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas as filiais"
    )
    @GetMapping()
    public List<FilialDTO> listar() {
        return cachingService.findAll();
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas as filias e exibe em forma de páginas. Evitando muitos resultados de um vez"
    )
    @GetMapping("/paginadas")
    public ResponseEntity<Page<FilialDTO>> paginar(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size
                                                                                ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FilialDTO> filiaisPaginadas = paginacaoService.paginar(pageRequest);
        return new ResponseEntity<>(filiaisPaginadas, HttpStatus.OK);
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca uma filial pelo ID"
    )
    @GetMapping(value = "/{id}")
    public FilialDTO buscarPorId(@PathVariable Long id) {
        Optional<FilialDTO> filial = cachingService.findById(id);
        if (filial.isPresent()) {
            return filial.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Inserção de informação",
            summary = "Cadastra uma nova filial"
    )
    @PostMapping("/inserir")
    public Filial salvar(@RequestBody Filial filial) {
        return repository.save(filial);
    }

    @Operation(
            tags = "Atualização de informação",
            summary = "Atualiza uma filial já existente pelo ID"
    )
    @PutMapping(value = "/atualizar/{id}")
    public Filial atualizar(@PathVariable Long id, @RequestBody Filial filial) {
        Optional<Filial> filialOptional = repository.findById(id);
        if (filialOptional.isPresent()) {
            Filial filialAtual = filialOptional.get();
            filialAtual.setNome(filial.getNome());
            repository.save(filialAtual);
            cachingService.clearCache();
            return filialAtual;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Remoção de informação",
            summary = "Remove uma filial pelo ID"
    )
    @DeleteMapping("/remover/{id}")
    public Filial remover(@PathVariable Long id) {
        Optional<Filial> filial = repository.findById(id);
        if (filial.isPresent()) {
            repository.delete(filial.get());
            cachingService.clearCache();
            return filial.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas as filiais a partir de uma cidada"
    )
    @GetMapping("/endereco/{cidade}")
    public List<FilialDTO> buscarFiliaisDaCidade(@PathVariable String cidade) {
        return cachingService.findFiliaisByCidade(cidade);
    }
}
