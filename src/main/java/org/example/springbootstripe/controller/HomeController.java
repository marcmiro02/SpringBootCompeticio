package org.example.springbootstripe.controller;

import org.example.springbootstripe.dto.CompeticioDTO;
import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.services.CompeticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CompeticioService competicioService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Competicio> latestCompeticions = competicioService.getLatestCompeticions();

        List<CompeticioDTO> competicionsDTO = latestCompeticions.stream().map(competicio -> {
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

        model.addAttribute("latestCompeticions", competicionsDTO);
        return "homepage";
    }
}