package com.APIDesafio.APIConsumo.controller;


import com.APIDesafio.APIConsumo.dto.RestResponsePage;
import com.APIDesafio.APIConsumo.dto.SenadorDTO;
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
@RequestMapping("v1/senadoresConsumo")
public class SenadorConsumoController {

    @GetMapping
    public ResponseEntity<List<SenadorDTO>> buscarTodosSenadores() {
        try {
            List<SenadorDTO> senadoresList = this.buscarSenadoresDaPrincipal();

            return ResponseEntity.ok(senadoresList);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<SenadorDTO> buscarSenador(@PathVariable Long id) {
        String url = "http://localhost:8080/v1/senadores/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            SenadorDTO senadorDTO = restTemplate.getForObject(url, SenadorDTO.class);
            return ResponseEntity.ok(senadorDTO);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    @GetMapping("/asc")
    public ResponseEntity<List<SenadorDTO>> buscarTodosSenadoresASC() {
        try {
            List<SenadorDTO> senadores = this.buscarSenadoresDaPrincipal();

            List senadoresOrganizados = new ArrayList(senadores);

            Collections.sort(senadoresOrganizados);

            return ResponseEntity.ok(senadoresOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    @GetMapping("/desc")
    public ResponseEntity<List<SenadorDTO>> buscarTodosSenadoresDES() {
        try {
            List<SenadorDTO> senadores = this.buscarSenadoresDaPrincipal();

            List senadoresOrganizados = new ArrayList(senadores);

            Collections.sort(senadoresOrganizados, Collections.reverseOrder());

            return ResponseEntity.ok(senadoresOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/leis/{id}")
    public ResponseEntity<List<SenadorDTO>> buscarProjetosDeLeiPelaQuantidade(@PathVariable Long id) {
        try {
            List<SenadorDTO> senadores = this.buscarSenadoresDaPrincipal();

            List<SenadorDTO> senadoresProjetosDeLeis = senadores.stream()
                    .filter(s -> s.getProjetoDeLei().size() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(senadoresProjetosDeLeis);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    //Classe que faz o consumo e busca todos os senadores da principal
    // e altera uma pageable em uma lista usando a classe RestResponsePage
    public List<SenadorDTO> buscarSenadoresDaPrincipal() {

        String url = "http://localhost:8080/v1/senadores";
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<RestResponsePage<SenadorDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<SenadorDTO>>() {
        };

        ResponseEntity<RestResponsePage<SenadorDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null/*httpEntity*/, responseType);

        List<SenadorDTO> senadoresResult = result.getBody().getContent();

        return senadoresResult;

    }
}
