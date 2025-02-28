package org.example.springbootstripe.controller;

import java.util.Base64;
import java.util.Map;

import org.example.springbootstripe.dto.CompeticioDTO;
import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.model.Equip;
import org.example.springbootstripe.model.Registre;
import org.example.springbootstripe.model.Response;
import org.example.springbootstripe.services.CompeticioService;
import org.example.springbootstripe.services.EquipService;
import org.example.springbootstripe.services.RegistreService;
import org.example.springbootstripe.services.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {
    @Autowired
    private CompeticioService competicioService;

    @Autowired
    private EquipService equipService;

    @Autowired
    private RegistreService registreService;

    @Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;

    private StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/charge")
    public String chargePage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "charge";
    }

    @PostMapping("/handle-inscription/{id}")
    public String handleInscription(@PathVariable Long id, @RequestParam Map<String, String> formData, Model model) {
        // Save the form data to the model to pass it to the charge page
        model.addAttribute("formData", formData);
        model.addAttribute("competicioId", id);
        System.out.println("User Role: " + formData);
        return "redirect:/checkout/" + id + "?email=" + formData.get("email1");
    }

    @GetMapping("/checkout/{id}")
    public String checkout(@PathVariable Long id, Model model, @RequestParam String email) {
        Competicio competicio = competicioService.getCompeticioById(id);
        if (competicio == null) {
            return "redirect:/error?message=Competition+not+found";
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

        if (competicio.getFotoPortada() != null) {
            String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
            competicioDTO.setFotoPortada("data:image/jpeg;base64," + base64Image);
        }

        model.addAttribute("competicio", competicioDTO);
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        model.addAttribute("email", email);

        return "charge"; // Cargar la vista de pago
    }

    @PostMapping("/create-charge/{id}")
    public @ResponseBody Response createCharge(@RequestParam String email, @RequestParam String stripeToken, @PathVariable Long id, @RequestParam Map<String, String> formData) {
        Competicio competicio = competicioService.getCompeticioById(id);
        if (competicio == null) {
            return new Response(false, "Competition not found.");
        }
    
        String chargeId = stripeService.createCharge(email, stripeToken, competicio.getPreu(), competicio.getNom(), competicio.getDataInici().toString(), competicio.getDataFi().toString());
        if (chargeId == null) {
            return new Response(false, "An error occurred while trying to create a charge.");
        }


        System.out.println("cagondeu: " + formData);


        // Verificar si hi ha més d'un participant
        boolean isTeam = competicio.getCapacitatEquip() > 1;
        Equip equip = new Equip();
        if (isTeam) {
            equip.setNomEquip(formData.get("teamName"));
            equip.setNomParticipant(formData.get("participant1"));
            equip.setIdUsuari(parseLongOrDefault(formData.get("idUsuari"), -1L));
            equip.setEmail(email);
            equipService.saveEquip(equip);
        } else {
            equip.setNomEquip(null);
            equip.setNomParticipant(formData.get("participant1"));
            equip.setIdUsuari(parseLongOrDefault(formData.get("idUsuari"), -1L));
            equip.setEmail(email);
            equipService.saveEquip(equip);
        }
    
        // Crear un nou registre
        Registre registre = new Registre();
        registre.setIdUsuari(parseLongOrDefault(formData.get("idUsuari"), -1L));
        registre.setIdCompeticio(id);
        registre.setIdEquip(equip.getId());
        registre.setIdPagament(parseLongOrDefault(chargeId, -1L));
        registreService.saveRegistre(registre);
    
        return new Response(true, "Success! Your charge id is " + chargeId);
    }
    
    private Long parseLongOrDefault(String value, Long defaultValue) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}