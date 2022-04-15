package com.APIDesafio.APIConsumo.dto;

import java.util.List;

public class GovernadorDTO extends PoliticoDTO implements Comparable<GovernadorDTO> {

    protected List<String> processos;

    public GovernadorDTO() {
    }

    public GovernadorDTO(String nome, String sigla, String nomePartido, String foto,
                         List<String> projetoDeLei, List<String> processos) {
        super(nome, sigla, nomePartido, foto, projetoDeLei);
        this.processos = processos;
    }

    public List<String> getProcessos() {
        return processos;
    }

    public void setProcessos(List<String> processos) {
        this.processos = processos;
    }


    @Override
    public int compareTo(GovernadorDTO dto) {
        return this.getNome().compareToIgnoreCase(dto.getNome());
    }
}
