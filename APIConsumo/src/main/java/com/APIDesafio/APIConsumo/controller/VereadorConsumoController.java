package com.APIDesafio.APIConsumo.controller;


import com.APIDesafio.APIConsumo.dto.RestResponsePage;
import com.APIDesafio.APIConsumo.dto.VereadorDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/vereadoresConsumo")
public class VereadorConsumoController {

    @GetMapping
    public ResponseEntity<List<VereadorDTO>> buscarTodosVereadores() {
        try {
            List<VereadorDTO> vereadoresList = this.buscarVereadoresDaPrincipal();

            return ResponseEntity.ok(vereadoresList);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<VereadorDTO> buscarVereadores(@PathVariable Long id) {
        String url = "http://localhost:8080/v1/vereadores/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            VereadorDTO vereadorDTO = restTemplate.getForObject(url, VereadorDTO.class);
            return ResponseEntity.ok(vereadorDTO);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/asc")
    public ResponseEntity<List<VereadorDTO>> buscarTodosVereadoresASC() {
        try {
            List<VereadorDTO> vereadoresList = this.buscarVereadoresDaPrincipal();

            List vereadoresOrganizados = new ArrayList(vereadoresList);

            Collections.sort(vereadoresOrganizados);

            return ResponseEntity.ok(vereadoresOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/desc")
    public ResponseEntity<List<VereadorDTO>> buscarTodosVereadoresDES() {
        try {
            List<VereadorDTO> vereadores = this.buscarVereadoresDaPrincipal();

            List vereadoresOrganizados = new ArrayList(vereadores);

            Collections.sort(vereadoresOrganizados, Collections.reverseOrder());

            return ResponseEntity.ok(vereadoresOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/leis/{id}")
    public ResponseEntity<List<VereadorDTO>> buscarProjetosDeLeiPelaQuantidade(@PathVariable Long id) {
        try {
            List<VereadorDTO> vereadores = this.buscarVereadoresDaPrincipal();

            List<VereadorDTO> vereadoresProjetosDeLeis = vereadores.stream()
                    .filter(v -> v.getProjetoDeLei().size() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(vereadoresProjetosDeLeis);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    //Classe que faz o consumo e busca todos os vereadores da principal
    // e altera uma pageable em uma lista usando a classe RestResponsePage
    public List<VereadorDTO> buscarVereadoresDaPrincipal() {

        String url = "http://localhost:8080/v1/vereadores";
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<RestResponsePage<VereadorDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<VereadorDTO>>() {
        };

        ResponseEntity<RestResponsePage<VereadorDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null/*httpEntity*/, responseType);

        List<VereadorDTO> vereadoresResult = result.getBody().getContent();

        return vereadoresResult;
    }
}
