package com.mottu.gerenciamento_motos.controller;

import com.mottu.gerenciamento_motos.dto.EnderecoDTO;
import com.mottu.gerenciamento_motos.dto.MotoDTO;
import com.mottu.gerenciamento_motos.model.Endereco;
import com.mottu.gerenciamento_motos.model.Moto;
import com.mottu.gerenciamento_motos.projection.MotoProjection;
import com.mottu.gerenciamento_motos.repository.EnderecoRepository;
import com.mottu.gerenciamento_motos.repository.MotoRepository;
import com.mottu.gerenciamento_motos.service.caching.EnderecoCachingService;
import com.mottu.gerenciamento_motos.service.caching.MotoCachingService;
import com.mottu.gerenciamento_motos.service.paginacao.EnderecoPaginacaoService;
import com.mottu.gerenciamento_motos.service.paginacao.MotoPaginacaoService;
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
@RequestMapping(value = "/motos")
public class MotoController {

    @Autowired
    private MotoRepository repository;

    @Autowired
    private MotoCachingService cachingService;

    @Autowired
    private MotoPaginacaoService paginacaoService;

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas as motos"
    )
    @GetMapping()
    public List<MotoDTO> listar() {
        return cachingService.findAll();
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas as motos e exibe em forma de páginas. Evitando muitos resultados de um vez"
    )
    @GetMapping("/paginadas")
    public ResponseEntity<Page<MotoDTO>> paginar(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MotoDTO> motosPaginadas = paginacaoService.paginar(pageRequest);
        return new ResponseEntity<>(motosPaginadas, HttpStatus.OK);
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca uma moto pelo ID"
    )
    @GetMapping(value = "/{id}")
    public MotoDTO buscarPorId(@PathVariable Long id) {
        Optional<MotoDTO> moto = cachingService.findById(id);
        if (moto.isPresent()) {
            return moto.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Inserção de informação",
            summary = "Cadastra uma nova moto"
    )
    @PostMapping("/inserir")
    public Moto salvar(@RequestBody Moto moto) {
        return repository.save(moto);
    }

    @Operation(
            tags = "Atualização de informação",
            summary = "Atualiza uma moto já existente pelo ID"
    )
    @PutMapping(value = "/atualizar/{id}")
    public Moto atualizar(@PathVariable Long id, @RequestBody Moto moto) {
        Optional<Moto> motoOptional = repository.findById(id);
        if (motoOptional.isPresent()) {
            Moto motoAtual = motoOptional.get();
            motoAtual.setAno(moto.getAno());
            motoAtual.setPlaca(moto.getPlaca());
            motoAtual.setModelo(moto.getModelo());
            motoAtual.setTipoCombustivel(moto.getTipoCombustivel());
            repository.save(motoAtual);
            cachingService.clearCache();
            return motoAtual;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Remoção de informação",
            summary = "Remove uma moto pelo ID"
    )
    @DeleteMapping("/remover/{id}")
    public Moto remover(@PathVariable Long id) {
        Optional<Moto> moto = repository.findById(id);
        if (moto.isPresent()) {
            repository.delete(moto.get());
            cachingService.clearCache();
            return moto.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            tags = "Retorno de informação",
            summary = "Busca todas as motos de uma filial pelo id da filial"
    )
    @GetMapping("/filial/{id}")
    public List<MotoProjection> buscarMotosPeloIdFilial(@PathVariable Long id) {
        return cachingService.findMotosByFilialId(id);
    }
}
