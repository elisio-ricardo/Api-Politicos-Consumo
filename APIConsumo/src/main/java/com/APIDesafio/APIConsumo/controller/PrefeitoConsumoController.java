package com.APIDesafio.APIConsumo.controller;

import com.APIDesafio.APIConsumo.dto.PrefeitoDTO;
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
@RequestMapping("v1/prefeitosConsumo")
public class PrefeitoConsumoController {

    @GetMapping("{id}")
    public ResponseEntity<PrefeitoDTO> buscarPrefeitos(@PathVariable Long id) {
        String url = "http://localhost:8080/v1/prefeitos/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            PrefeitoDTO prefeitoDTO = restTemplate.getForObject(url, PrefeitoDTO.class);
            return ResponseEntity.ok(prefeitoDTO);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<List<PrefeitoDTO>> buscarTodosPrefeitos() {
        try {
            List<PrefeitoDTO> prefeitosList = this.buscarPrefeitosDaPrincipal();

            return ResponseEntity.ok(prefeitosList);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/asc")
    public ResponseEntity<List<PrefeitoDTO>> buscarTodosPrefeitosASC() {
        try {
            List<PrefeitoDTO> prefeitos = this.buscarPrefeitosDaPrincipal();

            List prefeitosOrganizados = new ArrayList(prefeitos);

            Collections.sort(prefeitosOrganizados);

            return ResponseEntity.ok(prefeitosOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/desc")
    public ResponseEntity<List<PrefeitoDTO>> buscarTodosPrefeitosDES() {
        try {
            List<PrefeitoDTO> prefeitos = this.buscarPrefeitosDaPrincipal();

            List prefeitosOrganizados = new ArrayList(prefeitos);

            Collections.sort(prefeitosOrganizados, Collections.reverseOrder());

            return ResponseEntity.ok(prefeitosOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/leis/{id}")
    public ResponseEntity<List<PrefeitoDTO>> buscarProjetosDeLeiPelaQuantidade(@PathVariable Long id) {
        try {
            List<PrefeitoDTO> prefeitos = this.buscarPrefeitosDaPrincipal();

            List<PrefeitoDTO> prefeitosProjetosDeLeis = prefeitos.stream()
                    .filter(p -> p.getProjetoDeLei().size() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(prefeitosProjetosDeLeis);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    //Classe que faz o consumo e busca todos os prefeitos da principal
    // e altera uma pageable em uma lista usando a classe RestResponsePage
    public List<PrefeitoDTO> buscarPrefeitosDaPrincipal() {

        String url = "http://localhost:8080/v1/prefeitos";
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<RestResponsePage<PrefeitoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<PrefeitoDTO>>() {
        };

        ResponseEntity<RestResponsePage<PrefeitoDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null/*httpEntity*/, responseType);

        List<PrefeitoDTO> prefeitosResult = result.getBody().getContent();

        return prefeitosResult;
    }
}
