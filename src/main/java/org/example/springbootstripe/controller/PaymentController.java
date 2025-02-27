package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.model.Response;
import org.example.springbootstripe.services.CompeticioService;
import org.example.springbootstripe.services.StripeService;
import com.stripe.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.springbootstripe.dto.CompeticioDTO;

import java.util.Base64;
import java.util.Map;


@Controller
public class PaymentController {
    @Autowired
    private CompeticioService competicioService;

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
        return "redirect:/charge/" + id;
    }

    /*========== REST APIs for Handling Payments ===================*/
    @PostMapping("/create-charge/{id}")
    public @ResponseBody
    Response createCharge(String email, String token, @PathVariable Long id, Model model) {
        Competicio competicio = competicioService.getCompeticioById(id);
        CompeticioDTO competicioDTO = new CompeticioDTO();
        competicioDTO.setNom(competicio.getNom());
        competicioDTO.setPreu(competicio.getPreu());
        competicioDTO.setDataInici(competicio.getDataInici().toString());
        competicioDTO.setDataFi(competicio.getDataFi().toString());

        if (competicio.getFotoPortada() != null) {
            String base64Image = Base64.getEncoder().encodeToString(competicio.getFotoPortada());
            competicioDTO.setFotoPortada("data:image/jpeg;base64," + base64Image);
        }
        model.addAttribute("competicio", competicioDTO);
        if (token == null) {
            return new Response(false, "Stripe payment token is missing. Please, try again later.");
        }
        String chargeId = stripeService.createCharge(email, token, competicio.getPreu(), competicio.getNom(), competicio.getDataInici().toString(), competicio.getDataFi().toString());
        if (chargeId == null) {
            return new Response(false, "An error occurred while trying to create a charge.");
        }
        return new Response(true, "Success! Your charge id is " + chargeId);
    }


    @GetMapping("/checkout/{id}")
    public String checkout(@PathVariable Long id, Model model) {
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
        model.addAttribute("competicioId", id);
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);

        return "charge"; // Cargar la vista de pago
    }
}

