package repository;

import models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    @Query("SELECT m FROM Medico m WHERE lower(m.nome) LIKE %:nome%")
    List<Medico> findByNomeContainingAllIgnoringCase(String nome);

    List<Medico> findAllByOrderByNomeAsc();

}