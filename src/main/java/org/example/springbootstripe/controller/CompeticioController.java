package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.model.Categoria;
import org.example.springbootstripe.services.CompeticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.springbootstripe.dto.CompeticioDTO;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/competicions")
public class CompeticioController {

    @Autowired
    private CompeticioService competicioService;

    // API Endpoint para crear competiciones
    @PostMapping("/api")
    public ResponseEntity<Competicio> createCompeticio(@RequestBody Competicio competicio) {
        return ResponseEntity.ok(competicioService.saveCompeticio(competicio));
    }

    // API Endpoint para obtener todas las competiciones
    @GetMapping("/api")
    public ResponseEntity<List<Competicio>> getAllCompeticions() {
        return ResponseEntity.ok(competicioService.getAllCompeticions());
    }

    // API Endpoint para obtener una competicion por ID
    @GetMapping("/api/{id}")
    public ResponseEntity<Competicio> getCompeticioById(@PathVariable Long id) {
        return ResponseEntity.ok(competicioService.getCompeticioById(id));
    }

    // API Endpoint para actualizar una competicion
    @PutMapping("/api/{id}")
    public ResponseEntity<Competicio> updateCompeticio(@PathVariable Long id, @RequestBody Competicio competicio) {
        return ResponseEntity.ok(competicioService.updateCompeticio(id, competicio));
    }

    // API Endpoint para eliminar una competicion
    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteCompeticio(@PathVariable Long id) {
        competicioService.deleteCompeticio(id);
        return ResponseEntity.noContent().build();
    }

    // Mostrar formulario para crear una nueva competicion
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("competicio", new Competicio());
        return "competicio/crearCompeticio";
    }

    // Método POST para crear la competicion desde el formulario
    @PostMapping("/create")
    public String createCompeticio(
            @RequestParam("name") String name,
            @RequestParam("descripcio") String descripcio,
            @RequestParam("capacitat") Integer capacitat,
            @RequestParam("categoria") String categoria, // Se recibe el nombre de la categoria como String
            @RequestParam("dataInici") String dataInici,
            @RequestParam("dataFi") String dataFi,
            @RequestParam(value = "edat_min", required = false) Integer edatMin,
            @RequestParam(value = "edat_max", required = false) Integer edatMax,
            @RequestParam("foto_portada") MultipartFile fotoPortada,
            @RequestParam("preu") Double preu,
            @RequestParam("ubicacio") String ubicacio,
            @RequestParam("provincia") String provinciaNombre, // Recibe el nombre de la provincia
            @RequestParam("poblacio") String ciudadNombre, // Recibe el nombre de la ciudad
            @RequestParam("capacitat_equip") Integer capacitatEquip
    ) {
        Competicio competicio = new Competicio();
        competicio.setNom(name);
        competicio.setDescripcio(descripcio);
        competicio.setCapacitat(capacitat);
        competicio.setCategoria(Categoria.valueOf(categoria)); // Convertimos el String a Categoria enum
        competicio.setDataInici(LocalDate.parse(dataInici));
        competicio.setDataFi(LocalDate.parse(dataFi));
        competicio.setPreu(preu);
        competicio.setUbicacio(ubicacio);
        competicio.setPoblacio(ciudadNombre);  // Guardamos el nombre de la ciudad
        competicio.setProvincia(provinciaNombre);  // Guardamos el nombre de la provincia


        if (edatMin != null) {
            competicio.setEdatMin(edatMin);
        }

        if (edatMax != null) {
            competicio.setEdatMax(edatMax);
        }
        if (capacitatEquip != null) {
            competicio.setCapacitatEquip(capacitatEquip);
        }else{
            competicio.setCapacitatEquip(1);
        }


        if (!fotoPortada.isEmpty()) {
            try {
                competicio.setFotoPortada(fotoPortada.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        competicioService.saveCompeticio(competicio);

        return "redirect:/competicions/all"; // Redirigimos a la lista de competiciones
    }

    // Mostrar todas las competiciones activas
    @GetMapping("/all")
    public String getActiveCompeticions(Model model) {
        List<Competicio> activeCompeticions = competicioService.findActiveCompeticions();

        List<CompeticioDTO> competicionsDTO = activeCompeticions.stream().map(competicio -> {
            CompeticioDTO dto = new CompeticioDTO();
            dto.setId(competicio.getId());
            dto.setNom(competicio.getNom());
            dto.setCategoria(competicio.getCategoria().toString()); // Convertimos el enum a String
            dto.setDataInici(competicio.getDataInici().toString());
            dto.setDataFi(competicio.getDataFi().toString());

            if (competicio.getFotoPortada() != null) {
                String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
                dto.setFotoPortada("data:image/jpeg;base64," + base64Image);
            }

            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("competicions", competicionsDTO);
        return "competicio/competicions";
    }

    // Mostrar detalles de una competicion por ID
    @GetMapping("/{id}")
    public String getCompeticioById(@PathVariable Long id, Principal principal, Model model) {
        Competicio competicio = competicioService.getCompeticioById(id);

        CompeticioDTO competicioDTO = new CompeticioDTO();
        competicioDTO.setId(competicio.getId());
        competicioDTO.setNom(competicio.getNom());
        competicioDTO.setCategoria(competicio.getCategoria().toString()); // Convertimos el enum a String
        competicioDTO.setDataInici(competicio.getDataInici().toString());
        competicioDTO.setDataFi(competicio.getDataFi().toString());
        competicioDTO.setDescripcio(competicio.getDescripcio());
        competicioDTO.setCapacitat(competicio.getCapacitat());
        competicioDTO.setPreu(competicio.getPreu());
        competicioDTO.setUbicacio(competicio.getUbicacio());
        competicioDTO.setPoblacio(competicio.getPoblacio());
        competicioDTO.setProvincia(competicio.getProvincia());
        competicioDTO.setTipus(competicio.getTipus());


        if (competicio.getFotoPortada() != null) {
            String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
            competicioDTO.setFotoPortada("data:image/jpeg;base64," + base64Image);
        }

        if (principal != null) {
            model.addAttribute("currentUser", principal.getName()); // Pasamos el nombre del usuario logueado
        }

        model.addAttribute("competicio", competicioDTO);

        return "competicio/competicio"; // La ruta de la vista Thymeleaf
    }

    // Página de inicio que muestra todas las competiciones
    @GetMapping("/")
    public String getHomePage(Model model) {
        // Obtener todas las competiciones
        List<Competicio> allCompeticions = competicioService.getAllCompeticions();

        // Convertir las competiciones a DTOs para pasarlas a la vista
        List<CompeticioDTO> competicionsDTO = allCompeticions.stream().map(competicio -> {
            CompeticioDTO dto = new CompeticioDTO();
            dto.setId(competicio.getId());
            dto.setNom(competicio.getNom());
            dto.setCategoria(competicio.getCategoria().toString()); // Convertimos el enum a String
            dto.setDataInici(competicio.getDataInici().toString());
            dto.setDataFi(competicio.getDataFi().toString());

            // Si hay foto de portada, convertirla a base64 para mostrarla en la vista
            if (competicio.getFotoPortada() != null) {
                String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
                dto.setFotoPortada("data:image/jpeg;base64," + base64Image);
            }

            return dto;
        }).collect(Collectors.toList());

        // Agregar las competiciones DTO al modelo para pasarlas a la vista
        model.addAttribute("competicions", competicionsDTO);

        // Retornar la vista de inicio (homepage)
        return "homepage";
    }
}
