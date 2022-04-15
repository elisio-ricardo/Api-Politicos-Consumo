package com.APIDesafio.APIConsumo.controller;


import com.APIDesafio.APIConsumo.dto.PresidenteDTO;
import com.APIDesafio.APIConsumo.dto.RestResponsePage;
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
@RequestMapping("v1/presidentesConsumo")
public class PresidenteConsumoController {

    @GetMapping("{id}")
    public ResponseEntity<PresidenteDTO> buscarPresidente(@PathVariable Long id) {
        String url = "http://localhost:8080/v1/presidentes/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            PresidenteDTO presidenteDTO = restTemplate.getForObject(url, PresidenteDTO.class);
            return ResponseEntity.ok(presidenteDTO);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<List<PresidenteDTO>> buscarTodosPresidentes() {
        try {
            List<PresidenteDTO> presidentesList = this.buscarPresidentesDaPrincipal();

            return ResponseEntity.ok(presidentesList);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/asc")
    public ResponseEntity<List<PresidenteDTO>> buscarTodosPresidentesASC() {
        try {
            List<PresidenteDTO> presidentes = this.buscarPresidentesDaPrincipal();

            List presidentesOrganizados = new ArrayList(presidentes);

            Collections.sort(presidentesOrganizados);

            return ResponseEntity.ok(presidentesOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    @GetMapping("/desc")
    public ResponseEntity<List<PresidenteDTO>> buscarTodosPresidentesDES() {
        try {
            List<PresidenteDTO> presidentes = this.buscarPresidentesDaPrincipal();

            List presidentesOrganizados = new ArrayList(presidentes);

            Collections.sort(presidentesOrganizados, Collections.reverseOrder());

            return ResponseEntity.ok(presidentesOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/leis/{id}")
    public ResponseEntity<List<PresidenteDTO>> buscarProjetosDeLeiPelaQuantidade(@PathVariable Long id) {
        try {
            List<PresidenteDTO> presidentes = this.buscarPresidentesDaPrincipal();

            List<PresidenteDTO> presidentesProjetosDeLeis = presidentes.stream()
                    .filter(p -> p.getProjetoDeLei().size() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(presidentesProjetosDeLeis);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    //Classe que faz o consumo e busca todos os presidentes da principal
    // e altera uma pageable em uma lista usando a classe RestResponsePage
    public List<PresidenteDTO> buscarPresidentesDaPrincipal() {

        String url = "http://localhost:8080/v1/presidentes";
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<RestResponsePage<PresidenteDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<PresidenteDTO>>() {
        };

        ResponseEntity<RestResponsePage<PresidenteDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null/*httpEntity*/, responseType);

        List<PresidenteDTO> presidentesResult = result.getBody().getContent();

        return presidentesResult;

    }

}
