package com.mottu.gerenciamento_motos.controller;

import com.mottu.gerenciamento_motos.dto.FuncionarioDTO;
import com.mottu.gerenciamento_motos.model.Funcionario;
import com.mottu.gerenciamento_motos.repository.FuncionarioRepository;
import com.mottu.gerenciamento_motos.service.caching.FuncionarioCachingService;
import com.mottu.gerenciamento_motos.service.paginacao.FuncionarioPaginacaoService;
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
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private FuncionarioCachingService cachingService;

    @Autowired
    private FuncionarioPaginacaoService paginacaoService;

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todos os funcionários"
    )
    @GetMapping()
    public List<FuncionarioDTO> listar() {
        return cachingService.findAll();
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todos os funcionários e exibe em forma de páginas. Evitando muitos resultados de um vez"
    )
    @GetMapping("/paginadas")
    public ResponseEntity<Page<FuncionarioDTO>> paginar(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FuncionarioDTO> funcionariosPaginados = paginacaoService.paginar(pageRequest);
        return new ResponseEntity<>(funcionariosPaginados, HttpStatus.OK);
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca um funcionário pelo ID"
    )
    @GetMapping(value = "/{id}")
    public FuncionarioDTO buscarPorId(@PathVariable Long id) {
        Optional<FuncionarioDTO> funcionario = cachingService.findById(id);
        if (funcionario.isPresent()) {
            return funcionario.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Inserção de informação",
            summary = "Cadastra um novo funcionário"
    )
    @PostMapping("/inserir")
    public Funcionario salvar(@RequestBody Funcionario funcionario) {
        return repository.save(funcionario);
    }

    @Operation(
            tags = "Atualização de informação",
            summary = "Atualiza um funcionário já existente pelo ID"
    )
    @PutMapping(value = "/atualizar/{id}")
    public Funcionario atualizar(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        Optional<Funcionario> funcionarioOptional = repository.findById(id);
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionarioAtual = funcionarioOptional.get();
            funcionarioAtual.setNome(funcionario.getNome());
            repository.save(funcionario);
            cachingService.clearCache();
            return funcionario;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Remoção de informação",
            summary = "Remove um funcionário pelo ID"
    )
    @DeleteMapping("/remover/{id}")
    public Funcionario remover(@PathVariable Long id) {
        Optional<Funcionario> funcionario = repository.findById(id);
        if (funcionario.isPresent()) {
            repository.delete(funcionario.get());
            cachingService.clearCache();
            return funcionario.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
