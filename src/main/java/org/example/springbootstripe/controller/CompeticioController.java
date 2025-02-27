package org.example.springbootstripe.controller;

    import java.io.IOException;
    import java.security.Principal;
    import java.time.LocalDate;
    import java.util.Base64;
    import java.util.List;
    import java.util.stream.Collectors;

    import org.example.springbootstripe.dto.CompeticioDTO;
    import org.example.springbootstripe.model.*;
    import org.example.springbootstripe.repository.UsuariRepository;
    import org.example.springbootstripe.services.CompeticioService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.multipart.MultipartFile;

    @Controller
    @RequestMapping("/competicions")
    public class CompeticioController {

        @Autowired
        private CompeticioService competicioService;
        @Autowired
        private UsuariRepository usuariRepository;

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

            // Buscar al usuario por su ID
            Usuari usuari = usuariRepository.findById(idUsuari)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Asignar la entidad Usuari a la competición
            competicio.setUsuari(usuari);

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
        @GetMapping("/{id}")
        public String getCompeticioById(@PathVariable Long id,
                                        @RequestParam(value = "order", defaultValue = "asc") String order,
                                        Principal principal,
                                        Model model) {
            // Obtener la competencia por su ID
            Competicio competicio = competicioService.getCompeticioById(id);

            // Si no se encuentra la competencia, redirigir a la lista de competiciones
            if (competicio == null) {
                return "redirect:/competicions/all";
            }

            // Crear el DTO para la competencia
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
            competicioDTO.setId_usuari(competicio.getUsuari().getId());  // Corregido aquí

            if (competicio.getFotoPortada() != null) {
                String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
                competicioDTO.setFotoPortada("data:image/jpeg;base64," + base64Image);
            }

            if (principal != null) {
                model.addAttribute("currentUser", principal.getName());
            }

            String fotoPortadaUrl = competicio.getFotoPortada() != null
                    ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(competicio.getFotoPortada())
                    : "/images/logos/DAM.png";  // Imagen predeterminada
            model.addAttribute("fotoPortadaUrl", fotoPortadaUrl);

            LocalDate now = LocalDate.now();
            boolean isBeforeStart = competicio.getDataInici().isAfter(now);
            boolean isOngoing = competicio.getDataInici().isBefore(now) && competicio.getDataFi().isAfter(now);
            boolean isFinished = competicio.getDataFi().isBefore(now);

            model.addAttribute("isBeforeStart", isBeforeStart);
            model.addAttribute("isOngoing", isOngoing);
            model.addAttribute("isFinished", isFinished);
            model.addAttribute("idUsuariCompeticio", competicio.getUsuari().getId());  // Aquí también es id del usuario

            if (isFinished) {
                List<Puntuacio> puntuacions = competicioService.getPuntuacionsByCompeticioId(id);
                if ("asc".equals(order)) {
                    puntuacions.sort((p1, p2) -> p1.getPuntuacio().compareTo(p2.getPuntuacio()));  // Ordenar ascendente
                } else {
                    puntuacions.sort((p1, p2) -> p2.getPuntuacio().compareTo(p1.getPuntuacio()));  // Ordenar descendente
                }
                model.addAttribute("puntuacions", puntuacions);
            }

            // Añadir el DTO de la competencia al modelo para la vista
            model.addAttribute("competicio", competicioDTO);

            // Retornar la vista de la competencia
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
    }