package com.mottu.gerenciamento_motos.repository;

import com.mottu.gerenciamento_motos.projection.LocalizacaoMotoProjection;
import com.mottu.gerenciamento_motos.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {

    @Query(value = "SELECT " +
            "m.placa AS placa, " +
            "m.modelo AS modelo, " +
            "l.pontoX AS pontoX, " +
            "l.pontoY AS pontoY, " +
            "l.data_hora AS dataHora " +
            "FROM localizacao l " +
            "JOIN moto m ON l.fk_moto = m.id " +
            "WHERE m.id = :idMoto " +
            "ORDER BY l.data_hora DESC " +
            "LIMIT 1",
            nativeQuery = true)
    public LocalizacaoMotoProjection findUltimaLocalizacaoDaMoto(@Param("idMoto") Long idMoto);

}
