package models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

    @Entity
    @Table(name = "consulta")
    public class Consulta {

        @Id
        @GeneratedValue
        private Long id;

        @ManyToOne
        @JoinColumn(name = "medico_id")
        private Paciente paciente;

        @ManyToOne
        @JoinColumn(name = "medico_id")
        private Medico medico;

        private LocalDateTime dataHora;

        private boolean cancelada;

        private String motivoCancelamento;

        public Consulta(Long id, Paciente paciente, Medico medico, LocalDateTime dataHora) {
            this.id = id;
            this.paciente = paciente;
            this.medico = medico;
            this.dataHora = dataHora;
            this.cancelada = false;
            this.motivoCancelamento = "";
        }

        public Consulta() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Paciente getPaciente() {
            return paciente;
        }

        public void setPaciente(Paciente paciente) {
            this.paciente = paciente;
        }

        public Medico getMedico() {
            return medico;
        }

        public void setMedico(Medico medico) {
            this.medico = medico;
        }

        public LocalDateTime getDataHora() {
            return dataHora;
        }

        public void setDataHora(LocalDateTime dataHora) {
            this.dataHora = dataHora;
        }

        public boolean isCancelada() {
            return cancelada;
        }

        public void setCancelada(boolean cancelada) {
            this.cancelada = cancelada;
        }

        public String getMotivoCancelamento() {
            return motivoCancelamento;
        }

        public void setMotivoCancelamento(String motivoCancelamento) {
            this.motivoCancelamento = motivoCancelamento;
        }
    }
