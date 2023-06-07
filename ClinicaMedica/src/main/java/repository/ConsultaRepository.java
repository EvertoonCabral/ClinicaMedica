package repository;


import models.Consulta;
import models.Medico;
import models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteAndDataHoraBetween(Paciente paciente, LocalDateTime startDateTime, LocalDateTime endDateTime);

    boolean existsByMedicoAndDataHora(Medico medico, LocalDateTime dataHora);

}
