package com.APIDesafio.APIConsumo.dto;

import java.util.List;

public class MinistroDTO extends PoliticoDTO implements Comparable<MinistroDTO>  {
    public MinistroDTO() {
    }

    public MinistroDTO(String nome, String sigla, String nomePartido, String foto, List<String> projetoDeLei) {
        super(nome, sigla, nomePartido, foto, projetoDeLei);
    }


    @Override
    public int compareTo(MinistroDTO dto) {
        return this.getNome().compareToIgnoreCase(dto.getNome());
    }

}
