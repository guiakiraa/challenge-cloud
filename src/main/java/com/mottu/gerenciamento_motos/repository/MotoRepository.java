package com.mottu.gerenciamento_motos.repository;

import com.mottu.gerenciamento_motos.model.Moto;
import com.mottu.gerenciamento_motos.projection.MotoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {

    @Query("SELECT m.placa AS placa, m.modelo AS modelo, m.ano AS ano, m.tipoCombustivel AS tipoCombustivel " +
            "FROM Moto m WHERE m.filial.id = :idFilial")
    List<MotoProjection> findMotosByFilialId(@Param("idFilial") Long filialId);

}
