package com.mottu.gerenciamento_motos.controller;

import com.mottu.gerenciamento_motos.dto.EnderecoDTO;
import com.mottu.gerenciamento_motos.model.Endereco;
import com.mottu.gerenciamento_motos.repository.EnderecoRepository;
import com.mottu.gerenciamento_motos.service.caching.EnderecoCachingService;
import com.mottu.gerenciamento_motos.service.paginacao.EnderecoPaginacaoService;
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
@RequestMapping(value = "/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private EnderecoCachingService cachingService;

    @Autowired
    private EnderecoPaginacaoService paginacaoService;

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas ao endereços"
    )
    @GetMapping("/todas")
    public List<EnderecoDTO> listar() {
        return cachingService.findAll();
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas os endereços e exibe em forma de páginas. Evitando muitos resultados de um vez"
    )
    @GetMapping("/paginadas")
    public ResponseEntity<Page<EnderecoDTO>> paginar(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<EnderecoDTO> enderecosPaginados = paginacaoService.paginar(pageRequest);
        return new ResponseEntity<>(enderecosPaginados, HttpStatus.OK);
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca um endereço pelo ID"
    )
    @GetMapping(value = "/{id}")
    public EnderecoDTO buscarPorId(@PathVariable Long id) {
        Optional<EnderecoDTO> endereco = cachingService.findById(id);
        if (endereco.isPresent()) {
            return endereco.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Inserção de informação",
            summary = "Cadastra um novo endereço"
    )
    @PostMapping("/inserir")
    public Endereco salvar(@RequestBody Endereco endereco) {
        return repository.save(endereco);
    }

    @Operation(
            tags = "Atualização de informação",
            summary = "Atualiza um endereço já existente pelo ID"
    )
    @PutMapping(value = "/atualizar/{id}")
    public Endereco atualizar(@PathVariable Long id, @RequestBody Endereco endereco) {
        Optional<Endereco> enderecoOptional = repository.findById(id);
        if (enderecoOptional.isPresent()) {
            Endereco enderecoAtual = enderecoOptional.get();
            enderecoAtual.setLogradouro(endereco.getLogradouro());
            enderecoAtual.setNumero(endereco.getNumero());
            enderecoAtual.setComplemento(endereco.getComplemento());
            enderecoAtual.setCidade(endereco.getCidade());
            enderecoAtual.setEstado(endereco.getEstado());
            enderecoAtual.setCep(endereco.getCep());
            enderecoAtual.setBairro(endereco.getBairro());
            repository.save(enderecoAtual);
            cachingService.clearCache();
            return enderecoAtual;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Remoção de informação",
            summary = "Remove um endereco pelo ID"
    )
    @DeleteMapping("/remover/{id}")
    public Endereco remover(@PathVariable Long id) {
        Optional<Endereco> endereco = repository.findById(id);
        if (endereco.isPresent()) {
            repository.delete(endereco.get());
            cachingService.clearCache();
            return endereco.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
