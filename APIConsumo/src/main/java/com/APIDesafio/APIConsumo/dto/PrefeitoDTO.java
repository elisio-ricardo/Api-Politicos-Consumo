package com.APIDesafio.APIConsumo.dto;

import java.util.List;

public class PrefeitoDTO extends PoliticoDTO implements Comparable<PrefeitoDTO> {

    public PrefeitoDTO() {
    }

    public PrefeitoDTO(String nome, String sigla, String nomePartido, String foto, List<String> projetoDeLei) {
        super(nome, sigla, nomePartido, foto, projetoDeLei);
    }


    @Override
    public int compareTo(PrefeitoDTO dto) {
        return this.getNome().compareToIgnoreCase(dto.getNome());
    }
}
