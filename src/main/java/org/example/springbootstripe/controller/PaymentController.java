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

    @GetMapping("/")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/charge")
    public String chargePage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "charge";
    }

    /*========== REST APIs for Handling Payments ===================*/


    @PostMapping("/create-charge/{id}")
    public String createCharge(
            @PathVariable Long id,
            @RequestParam String email,
            @RequestParam String token,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellidos,
            @RequestParam(required = false) String nombreEquipo,
            @RequestParam(required = false) String participantes,
            @RequestParam(required = false) String capitan,
            @RequestParam(required = false) Integer capacitatEquip,
            Model model
    ) {
        Competicio competicio = competicioService.getCompeticioById(id);
        if (competicio == null) {
            return "redirect:/charge?id=" + id + "&error=invalidCompetition&message=Competición+no+encontrada";
        }

        if (token == null) {
            return "redirect:/charge?id=" + id + "&error=missingToken&message=El+token+de+Stripe+es+requerido";
        }

        String chargeId;
        if (nombre != null && apellidos != null) {
            // Inscripción individual
            chargeId = stripeService.createCharge(email, token, competicio.getPreu(), competicio.getNom(), competicio.getDataInici().toString(), competicio.getDataFi().toString());
        } else if (nombreEquipo != null && participantes != null && capitan != null && capacitatEquip != null) {
            // Inscripción por equipo
            chargeId = stripeService.createCharge(email, token, competicio.getPreu(), competicio.getNom(), competicio.getDataInici().toString(), competicio.getDataFi().toString());
        } else {
            return "redirect:/charge?id=" + id + "&error=invalidData&message=Faltan+datos+para+la+inscripción";
        }

        if (chargeId == null) {
            return "redirect:/charge?id=" + id + "&error=paymentError&message=Hubo+un+error+al+procesar+el+pago";
        }

        return "redirect:/charge?id=" + id + "&success=true&message=Pago+realizado+con+éxito";
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

