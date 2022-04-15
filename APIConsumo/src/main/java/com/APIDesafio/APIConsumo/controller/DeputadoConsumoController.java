package com.APIDesafio.APIConsumo.controller;


import com.APIDesafio.APIConsumo.dto.DeputadoDTO;
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
@RequestMapping("v1/deputadosConsumo")
public class DeputadoConsumoController {

    @GetMapping("{id}")
    public ResponseEntity<DeputadoDTO> buscarDeputado(@PathVariable Long id) {
        String url = "qe/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            DeputadoDTO deputadoDTO = restTemplate.getForObject(url, DeputadoDTO.class);
            return ResponseEntity.ok(deputadoDTO);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<List<DeputadoDTO>> buscarTodosDeputados() {
        try {
            List<DeputadoDTO> deputados = this.buscarDeputadosDaPrincipal();

            return ResponseEntity.ok(deputados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/asc")
    public ResponseEntity<List<DeputadoDTO>> buscarTodosDeputadosASC() {
        try {
            List<DeputadoDTO> deputados = this.buscarDeputadosDaPrincipal();

            List deputadosOrganizados = new ArrayList(deputados);

            Collections.sort(deputadosOrganizados);

            return ResponseEntity.ok(deputadosOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    @GetMapping("/desc")
    public ResponseEntity<List<DeputadoDTO>> buscarTodosDeputadosDES() {
        try {
            List<DeputadoDTO> deputados = this.buscarDeputadosDaPrincipal();

            List deputadosOrganizados = new ArrayList(deputados);

            Collections.sort(deputadosOrganizados, Collections.reverseOrder());

            return ResponseEntity.ok(deputadosOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/leis/{id}")
    public ResponseEntity<List<DeputadoDTO>> buscarProjetosDeLeiPelaQuantidade(@PathVariable Long id) {
        try {
            List<DeputadoDTO> deputados = this.buscarDeputadosDaPrincipal();

            List<DeputadoDTO> deputadosProjetosDeLeis = deputados.stream()
                    .filter(d -> d.getProjetoDeLei().size() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(deputadosProjetosDeLeis);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    //Metodo que faz o consumo e busca todos os Deputados da API principal
    // e altera uma pageable em uma lista usando a classe RestResponsePage
    public List<DeputadoDTO> buscarDeputadosDaPrincipal() {

        String url = "http://localhost:8080/v1/deputados";
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<RestResponsePage<DeputadoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<DeputadoDTO>>() {
        };

        ResponseEntity<RestResponsePage<DeputadoDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null/*httpEntity*/, responseType);

        List<DeputadoDTO> deputadosResult = result.getBody().getContent();

        return deputadosResult;

    }
}
