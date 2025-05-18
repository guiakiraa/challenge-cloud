package com.mottu.gerenciamento_motos.repository;

import com.mottu.gerenciamento_motos.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {
    @Query(value = """
        SELECT f.* FROM filial f
        JOIN endereco e ON f.fk_endereco = e.id
        WHERE LOWER(e.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))
        ORDER BY f.nome DESC
    """, nativeQuery = true)
    List<Filial> findFiliaisDaCidade(@Param("cidade") String cidade);
}
