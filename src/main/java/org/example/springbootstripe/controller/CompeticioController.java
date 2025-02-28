package org.example.springbootstripe.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.springbootstripe.dto.CompeticioDTO;
import org.example.springbootstripe.model.Categoria;
import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.model.Equip;
import org.example.springbootstripe.model.Puntuacio;
import org.example.springbootstripe.model.Tipus;
import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.services.CompeticioService;
import org.example.springbootstripe.services.EquipService;
import org.example.springbootstripe.services.PuntuacioService;
import org.example.springbootstripe.services.RegistreService;
import org.example.springbootstripe.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/competicions")
public class CompeticioController {

    @Autowired
    private CompeticioService competicioService;

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private EquipService equipService;

    @Autowired
    private PuntuacioService puntuacioService;

    @Autowired
    private RegistreService registreService;


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



    // Mostrar formulario para crear una nueva competicion
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("competicio", new Competicio());
        model.addAttribute("categories", Categoria.values()); // Añadir valores del enum Categoria
        model.addAttribute("tipusList", Tipus.values()); // Añadir valores del enum Tipus
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
            @RequestParam("capacitat_equip") Integer capacitatEquip,
            @RequestParam("tipus") String tipus, // Tipo de competición
            @RequestParam("id_usuari") Long idUsuari // Recibimos el id del usuario
    ) {
        // Crear nueva instancia de Competicio
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
        competicio.setTipus(Tipus.valueOf(tipus)); // Tipo de competición

        // Asignar la entidad Usuari a la competición
        competicio.setIdUsuari(idUsuari);

        // Asignar los valores opcionales si se han proporcionado
        if (edatMin != null) {
            competicio.setEdatMin(edatMin);
        }

        if (edatMax != null) {
            competicio.setEdatMax(edatMax);
        }

        // Si no se ha proporcionado un valor para capacitatEquip, asignar 1 por defecto
        if (capacitatEquip != null) {
            competicio.setCapacitatEquip(capacitatEquip);
        } else {
            competicio.setCapacitatEquip(1); // Valor por defecto
        }

        // Manejo del archivo fotoPortada
        if (!fotoPortada.isEmpty()) {
            try {
                competicio.setFotoPortada(fotoPortada.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return "Error al cargar la foto de portada";
            }
        }

        // Guardamos la competición en la base de datos
        competicioService.saveCompeticio(competicio);

        // Redirigimos a la lista de competiciones después de guardar
        return "redirect:/competicions/all";
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
        model.addAttribute("isPastEvents", false);
        return "competicio/competicions";
    }


    // Mostrar detalles de una competicion por ID
    @GetMapping("/{id}")
    public String getCompeticioById(@PathVariable Long id, @RequestParam(value = "order", defaultValue = "asc") String order, HttpSession session, Model model) {
        Competicio competicio = competicioService.getCompeticioById(id);
        
        if (competicio == null) {
            return "redirect:/competicions/all";
        }
    
        CompeticioDTO competicioDTO = new CompeticioDTO();
        competicioDTO.setId(competicio.getId());
        competicioDTO.setNom(competicio.getNom());
        competicioDTO.setCategoria(competicio.getCategoria().toString());
        competicioDTO.setDataInici(competicio.getDataInici().toString());
        competicioDTO.setDataFi(competicio.getDataFi().toString());
        competicioDTO.setDescripcio(competicio.getDescripcio());
        competicioDTO.setCapacitat(competicio.getCapacitat());
        competicioDTO.setPreu(competicio.getPreu());
        competicioDTO.setUbicacio(competicio.getUbicacio());
        competicioDTO.setPoblacio(competicio.getPoblacio());
        competicioDTO.setProvincia(competicio.getProvincia());
        competicioDTO.setTipus(competicio.getTipus().name());
        competicioDTO.setCapacitatEquip(competicio.getCapacitatEquip());
    
        if (competicio.getFotoPortada() != null) {
            String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
            competicioDTO.setFotoPortada("data:image/jpeg;base64," + base64Image);
        }
    
        // Obtenir l'usuari actual des de la sessió
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("currentUser", username);
            System.out.println("currentUser: " + username);
    
            // Verificar si l'usuari està registrat a la competició
            Usuari usuari = usuariService.findByNomUsuari(username);
            boolean isRegistered = registreService.isUsuariRegistrat(usuari.getId(), competicio.getId());
            model.addAttribute("isRegistered", isRegistered);

            // Verificar si l'usuari ja ha afegit una puntuació
            Optional<Puntuacio> userPuntuacio = puntuacioService.findByCompeticioIdAndEquipIdUsuari(competicio.getId(), usuari.getId());
            model.addAttribute("userPuntuacio", userPuntuacio.orElse(null));
        }
    
        String fotoPortadaUrl = competicio.getFotoPortada() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(competicio.getFotoPortada())
                : "/images/logos/DAM.png";
        model.addAttribute("fotoPortadaUrl", fotoPortadaUrl);
    
        LocalDate now = LocalDate.now();
        boolean isBeforeStart = competicio.getDataInici().isAfter(now);
        boolean isOngoing = competicio.getDataInici().isBefore(now) && competicio.getDataFi().isAfter(now);
        boolean isFinished = competicio.getDataFi().isBefore(now);
    
        model.addAttribute("isBeforeStart", isBeforeStart);
        model.addAttribute("isOngoing", isOngoing);
        model.addAttribute("isFinished", isFinished);
    
        System.out.println("isBeforeStart: " + isBeforeStart);
        System.out.println("isOngoing: " + isOngoing);
        System.out.println("isFinished: " + isFinished);
    
        if (isFinished) {
            List<Puntuacio> puntuacions = competicioService.getPuntuacionsByCompeticioId(id);
            if ("asc".equals(order)) {
                puntuacions.sort((p1, p2) -> p1.getPuntuacio().compareTo(p2.getPuntuacio())); // Ordenar por tiempo ascendente
            } else {
                puntuacions.sort((p1, p2) -> p2.getPuntuacio().compareTo(p1.getPuntuacio())); // Ordenar por tiempo descendente
            }
            model.addAttribute("puntuacions", puntuacions);
        }
    
        model.addAttribute("competicio", competicioDTO);
    
        return "competicio/competicio";
    }

    @GetMapping("/past")
    public String getPastCompeticions(Model model) {
        List<Competicio> pastCompeticions = competicioService.findPastCompeticions();

        List<CompeticioDTO> competicionsDTO = pastCompeticions.stream().map(competicio -> {
            CompeticioDTO dto = new CompeticioDTO();
            dto.setId(competicio.getId());
            dto.setNom(competicio.getNom());
            dto.setCategoria(competicio.getCategoria().toString());
            dto.setDataInici(competicio.getDataInici().toString());
            dto.setDataFi(competicio.getDataFi().toString());

            if (competicio.getFotoPortada() != null) {
                String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
                dto.setFotoPortada("data:image/jpeg;base64," + base64Image);
            }

            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("competicions", competicionsDTO);
        model.addAttribute("isPastEvents", true);
        return "competicio/competicions";
    }



    
                @PostMapping("/addPuntuacio/{id}")
        public String addPuntuacio(@PathVariable Long id, @RequestParam String puntuacio, HttpSession session, Model model) {
            Competicio competicio = competicioService.getCompeticioById(id);
            if (competicio == null) {
                return "redirect:/competicions/all";
            }
        
            // Obtenir l'usuari actual des de la sessió
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return "redirect:/login";
            }
        
            Usuari usuari = usuariService.findByNomUsuari(username);
            if (usuari == null) {
                return "redirect:/login";
            }
        
            // Verificar si l'usuari està registrat a la competició
            if (!registreService.isUsuariRegistrat(usuari.getId(), competicio.getId())) {
                model.addAttribute("error", "not-registered");
                return "redirect:/competicions/" + id + "?error=not-registered";
            }
        
            // Verificar si l'usuari ja ha afegit una puntuació
            Optional<Puntuacio> existingPuntuacio = puntuacioService.findByCompeticioIdAndEquipIdUsuari(competicio.getId(), usuari.getId());
            if (existingPuntuacio.isPresent()) {
                model.addAttribute("error", "already-added");
                return "redirect:/competicions/" + id + "?error=already-added";
            }
        
            // Crear una nova puntuació
            Puntuacio novaPuntuacio = new Puntuacio();
            novaPuntuacio.setPuntuacio(puntuacio);
            novaPuntuacio.setCompeticio(competicio);
        
            // Obtenir l'equip de l'usuari
            Equip equip = equipService.getEquipByUsuariId(usuari.getId());
            novaPuntuacio.setEquip(equip);
        
            // Guardar la nova puntuació
            puntuacioService.savePuntuacio(novaPuntuacio);
        
            // Actualitzar el model amb la nova puntuació
            model.addAttribute("userPuntuacio", novaPuntuacio);
        
            return "redirect:/competicions/" + id;
        }




        @PostMapping("/editPuntuacio/{id}")
    public String editPuntuacio(@PathVariable Long id, @RequestParam String puntuacio, HttpSession session, Model model) {
        Competicio competicio = competicioService.getCompeticioById(id);
        if (competicio == null) {
            return "redirect:/competicions/all";
        }
    
        // Obtenir l'usuari actual des de la sessió
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
    
        Usuari usuari = usuariService.findByNomUsuari(username);
        if (usuari == null) {
            return "redirect:/login";
        }
    
        // Verificar si l'usuari ja ha afegit una puntuació
        Optional<Puntuacio> existingPuntuacio = puntuacioService.findByCompeticioIdAndEquipIdUsuari(competicio.getId(), usuari.getId());
        if (existingPuntuacio.isPresent()) {
            Puntuacio puntuacioToUpdate = existingPuntuacio.get();
            puntuacioToUpdate.setPuntuacio(puntuacio);
            puntuacioService.savePuntuacio(puntuacioToUpdate);
        }
    
        return "redirect:/competicions/" + id;
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUsuari(@PathVariable Long id) {
        competicioService.deleteCompeticio(id);
        return "redirect:/usuaris/competicionsGestio";
    }

    @PostMapping("/editCompeticio/{id}")
    public String editCompeticio(@PathVariable Long id,
                                @RequestParam("nom") String nom,
                                @RequestParam("descripcio") String descripcio,
                                @RequestParam("dataInici") String dataInici,
                                @RequestParam("dataFi") String dataFi,
                                @RequestParam("preu") Double preu) {
        Competicio competicio = competicioService.getCompeticioById(id);
        if (competicio == null) {
            return "redirect:/competicions/all";
        }

        competicio.setNom(nom);
        competicio.setDescripcio(descripcio);
        competicio.setDataInici(LocalDate.parse(dataInici));
        competicio.setDataFi(LocalDate.parse(dataFi));
        competicio.setPreu(preu);

        competicioService.saveCompeticio(competicio);

        return "redirect:/competicions/" + id;
    }
}
