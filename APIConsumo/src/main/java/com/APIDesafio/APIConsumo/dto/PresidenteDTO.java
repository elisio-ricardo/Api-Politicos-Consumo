package com.APIDesafio.APIConsumo.dto;

import java.util.List;

public class PresidenteDTO extends PoliticoDTO implements Comparable<PresidenteDTO> {

    public PresidenteDTO() {
    }

    public PresidenteDTO(String nome, String sigla, String nomePartido, String foto, List<String> projetoDeLei) {
        super(nome, sigla, nomePartido, foto, projetoDeLei);
    }


    @Override
    public int compareTo(PresidenteDTO dto) {
        return this.getNome().compareToIgnoreCase(dto.getNome());
    }
}
