package com.mottu.gerenciamento_motos.repository;

import com.mottu.gerenciamento_motos.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
}
