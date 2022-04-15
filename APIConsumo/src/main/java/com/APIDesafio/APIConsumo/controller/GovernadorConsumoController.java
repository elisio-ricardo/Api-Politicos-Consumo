package com.APIDesafio.APIConsumo.controller;

import com.APIDesafio.APIConsumo.dto.GovernadorDTO;
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
@RequestMapping("v1/governadoresConsumo")
public class GovernadorConsumoController {

    @GetMapping("{id}")
    public ResponseEntity<GovernadorDTO> buscarGovernador(@PathVariable Long id) {
        String url = "http://localhost:8080/v1/governadores/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            GovernadorDTO governadorDTO = restTemplate.getForObject(url, GovernadorDTO.class);
            return ResponseEntity.ok(governadorDTO);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<List<GovernadorDTO>> buscarTodosGovernadores() {
        try {
            List<GovernadorDTO> governadorList = this.buscarGovernadoresDaPrincipal();

            List governadoresOrganizados = new ArrayList(governadorList);

            Collections.sort(governadoresOrganizados);

            return ResponseEntity.ok(governadoresOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/asc")
    public ResponseEntity<List<GovernadorDTO>> buscarTodosGovernadoresASC() {
        try {
            List<GovernadorDTO> governadores = this.buscarGovernadoresDaPrincipal();

            List governadoresOrganizados = new ArrayList(governadores);

            Collections.sort(governadoresOrganizados);

            return ResponseEntity.ok(governadoresOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/desc")
    public ResponseEntity<List<GovernadorDTO>> buscarTodosGovernadoresDES() {
        try {
            List<GovernadorDTO> governadores = this.buscarGovernadoresDaPrincipal();

            List governadoresOrganizados = new ArrayList(governadores);

            Collections.sort(governadoresOrganizados, Collections.reverseOrder());

            return ResponseEntity.ok(governadoresOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/leis/{id}")
    public ResponseEntity<List<GovernadorDTO>> buscarProjetosDeLeiPelaQuantidade(@PathVariable Long id) {
        try {
            List<GovernadorDTO> governadores = this.buscarGovernadoresDaPrincipal();

            List<GovernadorDTO> governadoresProjetosDeLeis = governadores.stream()
                    .filter(g -> g.getProjetoDeLei().size() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(governadoresProjetosDeLeis);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    //Metodo que faz o consumo e busca todos os Governadores da API principal
    // e altera uma pageable em uma lista usando a classe RestResponsePage
    public List<GovernadorDTO> buscarGovernadoresDaPrincipal() {

        String url = "http://localhost:8080/v1/governadores";
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<RestResponsePage<GovernadorDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<GovernadorDTO>>() {
        };

        ResponseEntity<RestResponsePage<GovernadorDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null/*httpEntity*/, responseType);

        List<GovernadorDTO> governadoresResult = result.getBody().getContent();

        return governadoresResult;
    }


}
