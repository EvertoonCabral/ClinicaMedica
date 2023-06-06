package repository;

import br.unipar.clinicamedica.projection.PacienteProjection;

import models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {



    List<Paciente> findAllByOrderByNomeAsc();

}
