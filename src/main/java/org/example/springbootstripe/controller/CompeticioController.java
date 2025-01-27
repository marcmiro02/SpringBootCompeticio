package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.services.CompeticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/competicions")
public class CompeticioController {

    @Autowired
    private CompeticioService competicioService;

    @PostMapping("/api")
    public ResponseEntity<Competicio> createCompeticio(@RequestBody Competicio competicio) {
        return ResponseEntity.ok(competicioService.saveCompeticio(competicio));
    }

    @GetMapping("/api")
    public ResponseEntity<List<Competicio>> getAllCompeticions() {
        return ResponseEntity.ok(competicioService.getAllCompeticions());
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Competicio> getCompeticioById(@PathVariable Long id) {
        return ResponseEntity.ok(competicioService.getCompeticioById(id));
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<Competicio> updateCompeticio(@PathVariable Long id, @RequestBody Competicio competicio) {
        return ResponseEntity.ok(competicioService.updateCompeticio(id, competicio));
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteCompeticio(@PathVariable Long id) {
        competicioService.deleteCompeticio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("competicio", new Competicio());
        return "competicio/crearCompeticio";
    }

    @PostMapping("/create")
    public String createCompeticio(
            @RequestParam("name") String name,
            @RequestParam("descripcio") String descripcio,
            @RequestParam("capacitat") Integer capacitat,
            @RequestParam("categoria") String categoria,
            @RequestParam("dataInici") String dataInici,
            @RequestParam("dataFi") String dataFi,

            // Los nuevos parámetros para edad mínima y máxima
            @RequestParam(value = "edat_min", required = false) Integer edatMin,
            @RequestParam(value = "edat_max", required = false) Integer edatMax) {

        Competicio competicio = new Competicio();
        competicio.setNom(name);
        competicio.setDescripcio(descripcio);
        competicio.setCapacitat(capacitat);
        competicio.setCategoria(categoria);
        competicio.setDataInici(LocalDate.parse(dataInici));
        competicio.setDataFi(LocalDate.parse(dataFi));

        // Asignar los valores de edat_min y edat_max, si están presentes
        if (edatMin != null) {
            competicio.setEdatMin(edatMin);
        }

        if (edatMax != null) {
            competicio.setEdatMax(edatMax);
        }

        // Guardar la competición
        competicioService.saveCompeticio(competicio);

        return "redirect:/competicions";
    }

}