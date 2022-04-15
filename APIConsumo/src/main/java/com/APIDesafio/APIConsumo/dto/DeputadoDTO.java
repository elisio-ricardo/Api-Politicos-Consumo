package com.APIDesafio.APIConsumo.dto;

import java.util.List;

public class DeputadoDTO extends PoliticoDTO implements Comparable<DeputadoDTO> {
    private Boolean lider;

    public DeputadoDTO() {
    }

    public DeputadoDTO(String nome, Boolean lider, String sigla, String nomePartido, String foto, List<String> projetoDeLei) {
        super(nome, sigla, nomePartido, foto, projetoDeLei);
        this.lider = lider;
    }

    public Boolean getLider() {
        return lider;
    }

    public void setLider(Boolean lider) {
        this.lider = lider;
    }

    @Override
    public int compareTo(DeputadoDTO dto) {
        return this.getNome().compareToIgnoreCase(dto.getNome());
    }
}
