package com.APIDesafio.APIConsumo.dto;

import java.util.List;

public class SenadorDTO extends PoliticoDTO  implements Comparable<MinistroDTO> {

    public SenadorDTO() {
    }

    public SenadorDTO(String nome, String sigla, String nomePartido, String foto, List<String> projetoDeLei) {
        super(nome, sigla, nomePartido, foto, projetoDeLei);
    }

    @Override
    public int compareTo(MinistroDTO dto) {
        return this.getNome().compareToIgnoreCase(dto.getNome());
    }
}
