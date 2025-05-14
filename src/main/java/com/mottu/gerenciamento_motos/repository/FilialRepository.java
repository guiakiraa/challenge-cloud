package com.mottu.gerenciamento_motos.repository;

import com.mottu.gerenciamento_motos.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {
    @Query("from Filial f where upper(f.nome) = upper(:nome)")
    public List<Filial> buscarFilialPorNome(String nome);
}
