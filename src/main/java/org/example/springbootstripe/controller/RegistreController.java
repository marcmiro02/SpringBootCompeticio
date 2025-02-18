package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Registre;
import org.example.springbootstripe.services.RegistreService;
import org.example.springbootstripe.services.CompeticioService;
import org.example.springbootstripe.services.PagamentService;
import org.example.springbootstripe.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inscripcio")
public class RegistreController {

    @Autowired
    private CompeticioService competicioService;

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private PagamentService pagamentService;

    @Autowired
    private RegistreService registreService;

    // Mostrar formulario de inscripción
    @GetMapping("/{idCompeticio}")
    public String showInscripcioForm(@PathVariable Long idCompeticio, Model model) {
        model.addAttribute("idCompeticio", idCompeticio); // Pasamos el ID de la competición al formulario
        return "inscripcio/form"; // Vista del formulario de inscripción
    }

    // Procesar la inscripción (POST)
    @PostMapping("/{idCompeticio}")
    public String processInscripcio(
            @PathVariable Long idCompeticio,
            @RequestParam(value = "idEquip", required = false) Long idEquip, // Opcional
            @RequestParam("idPagament") Long idPagament, // Relacionado con el pago
            @RequestParam("idUsuari") Long idUsuari, // Usuario que se inscribe
            Model model) {

        // Verificar que la competición, el usuario y el pago existen
        if (competicioService.getCompeticioById(idCompeticio) != null &&
                usuariService.getUsuariById(idUsuari) != null &&
                pagamentService.getPagamentById(idPagament) != null) {

            // Crear un nuevo registro de inscripción
            Registre registre = new Registre();
            registre.setIdCompeticio(idCompeticio);
            registre.setIdUsuari(idUsuari);
            registre.setIdEquip(idEquip);
            registre.setIdPagament(idPagament);

            // Guardar el registro en la base de datos
            registreService.saveRegistre(registre);

            // Redirigir al usuario a una vista de éxito o a la lista de competiciones
            model.addAttribute("successMessage", "Inscripción realizada con éxito.");
            return "redirect:/competicions/all"; // Redirigir a la lista de competiciones o cualquier otra vista
        } else {
            // Si alguno de los ID no es válido, mostrar un error
            model.addAttribute("errorMessage", "Hay un error con los datos proporcionados.");
            return "inscripcio/form"; // Volver a mostrar el formulario
        }
    }
}
