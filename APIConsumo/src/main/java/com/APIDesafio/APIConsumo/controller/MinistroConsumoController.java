package com.APIDesafio.APIConsumo.controller;


import com.APIDesafio.APIConsumo.dto.MinistroDTO;
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
@RequestMapping("v1/ministrosConsumo")
public class MinistroConsumoController {

    @GetMapping("{id}")
    public ResponseEntity<MinistroDTO> buscarMinistro(@PathVariable Long id) {
        String url = "http://localhost:8080/v1/ministros/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            MinistroDTO ministroDTO = restTemplate.getForObject(url, MinistroDTO.class);
            return ResponseEntity.ok(ministroDTO);
        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<List<MinistroDTO>> buscarTodosMinistros() {
        try {
            List<MinistroDTO> ministroList = this.buscarMinistrosDaPrincipal();

            return ResponseEntity.ok(ministroList);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/asc")
    public ResponseEntity<List<MinistroDTO>> buscarTodosMinitrosASC() {
        try {
            List<MinistroDTO> ministros = this.buscarMinistrosDaPrincipal();

            List ministrosOrganizados = new ArrayList(ministros);

            Collections.sort(ministrosOrganizados);

            return ResponseEntity.ok(ministrosOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/desc")
    public ResponseEntity<List<MinistroDTO>> buscarTodosDeputadosDES() {
        try {
            List<MinistroDTO> ministros = this.buscarMinistrosDaPrincipal();

            List ministrosOrganizados = new ArrayList(ministros);

            Collections.sort(ministrosOrganizados, Collections.reverseOrder());

            return ResponseEntity.ok(ministrosOrganizados);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }

    @GetMapping("/leis/{id}")
    public ResponseEntity<List<MinistroDTO>> buscarProjetosDeLeiPelaQuantidade(@PathVariable Long id) {
        try {
            List<MinistroDTO> ministros = this.buscarMinistrosDaPrincipal();

            List<MinistroDTO> ministrosProjetosDeLeis = ministros.stream()
                    .filter(m -> m.getProjetoDeLei().size() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ministrosProjetosDeLeis);

        } catch (HttpClientErrorException he) {
            return new ResponseEntity(he.getStatusCode());
        }
    }


    //Metodo que faz o consumo e busca todos os Ministro da API principal
    // e altera uma pageable em uma lista usando a classe RestResponsePage
    public List<MinistroDTO> buscarMinistrosDaPrincipal() {

        String url = "http://localhost:8080/v1/ministros";
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<RestResponsePage<MinistroDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<MinistroDTO>>() {
        };

        ResponseEntity<RestResponsePage<MinistroDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null/*httpEntity*/, responseType);

        List<MinistroDTO> ministrosResult = result.getBody().getContent();

        return ministrosResult;
    }
}
