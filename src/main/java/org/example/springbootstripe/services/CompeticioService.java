package org.example.springbootstripe.services;

    import org.example.springbootstripe.model.Competicio;
    import org.example.springbootstripe.model.Puntuacio;
    import org.example.springbootstripe.repository.CompeticioRepository;
    import org.example.springbootstripe.repository.PuntuacioRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class CompeticioService {

        @Autowired
        private CompeticioRepository competicioRepository;

        @Autowired
        private PuntuacioRepository puntuacioRepository;

        public Competicio saveCompeticio(Competicio competicio) {
            return competicioRepository.save(competicio);
        }

        public List<Competicio> getAllCompeticions() {
            return competicioRepository.findAll();
        }

        public Competicio getCompeticioById(Long id) {
            Optional<Competicio> competicio = competicioRepository.findById(id);
            return competicio.orElse(null);
        }

        public Competicio updateCompeticio(Long id, Competicio updatedCompeticio) {
            if (competicioRepository.existsById(id)) {
                updatedCompeticio.setId(id);
                return competicioRepository.save(updatedCompeticio);
            }
            return null;
        }

        public void deleteCompeticio(Long id) {
            if (competicioRepository.existsById(id)) {
                competicioRepository.deleteById(id);
            }
        }

        public List<Competicio> findActiveCompeticions() {
            return competicioRepository.findByDataFiAfterOrderByDataIniciAsc(LocalDate.now());
        }

        public List<Competicio> getLatestCompeticions() {
            return competicioRepository.findTop8ByDataIniciAfterOrderByDataInici(LocalDate.now());
        }

        public List<Competicio> findPastCompeticions() {
            return competicioRepository.findByDataFiBeforeOrderByDataFiDesc(LocalDate.now());
        }

        public List<Puntuacio> getPuntuacionsByCompeticioId(Long competicioId) {
            return puntuacioRepository.findByCompeticioId(competicioId);
        }

        public List<Competicio> getCompeticionsByCreatorId(Long userId) {
            return competicioRepository.findByIdUsuari(userId);
        }
        public List<Competicio> getCompeticionsByUserId(Long userId) {
            return competicioRepository.getCompeticionsByUserId(userId);
        }

    }