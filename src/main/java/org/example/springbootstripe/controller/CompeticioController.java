package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.services.CompeticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.springbootstripe.dto.CompeticioDTO;




import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            @RequestParam(value = "edat_min", required = false) Integer edatMin,
            @RequestParam(value = "edat_max", required = false) Integer edatMax,
            @RequestParam("foto_portada") MultipartFile fotoPortada,
            @RequestParam("preu") Double preu,
            @RequestParam("ubicacio") String ubicacio,
            @RequestParam("provincia_nombre") String provinciaNombre, // Ahora recibe el nombre de la provincia
            @RequestParam("ciudad_nombre") String ciudadNombre // Ahora recibe el nombre de la ciudad
    ) {

        Competicio competicio = new Competicio();
        competicio.setNom(name);
        competicio.setDescripcio(descripcio);
        competicio.setCapacitat(capacitat);
        competicio.setCategoria(categoria);
        competicio.setDataInici(LocalDate.parse(dataInici));
        competicio.setDataFi(LocalDate.parse(dataFi));
        competicio.setPreu(preu);
        competicio.setUbicacio(ubicacio);
        competicio.setPoblacio(ciudadNombre);  // Se guarda el nombre de la ciudad
        competicio.setProvincia(provinciaNombre);  // Se guarda el nombre de la provincia

        if (edatMin != null) {
            competicio.setEdatMin(edatMin);
        }

        if (edatMax != null) {
            competicio.setEdatMax(edatMax);
        }

        if (!fotoPortada.isEmpty()) {
            try {
                competicio.setFotoPortada(fotoPortada.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        competicioService.saveCompeticio(competicio);

        return "redirect:/competicions/all";
    }

    @GetMapping("/all")
    public String getActiveCompeticions(Model model) {
        List<Competicio> activeCompeticions = competicioService.findActiveCompeticions();

        // Convertir la imagen a Base64 antes de enviarla a la vista
        List<CompeticioDTO> competicionsDTO = activeCompeticions.stream().map(competicio -> {
            CompeticioDTO dto = new CompeticioDTO();
            dto.setNom(competicio.getNom());
            dto.setCategoria(competicio.getCategoria());
            dto.setDataInici(competicio.getDataInici().toString());

            if (competicio.getFotoPortada() != null) {
                String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
                dto.setFotoPortada("data:image/jpeg;base64," + base64Image);
            }

            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("competicions", competicionsDTO);
        return "competicio/competicions";
    }

}