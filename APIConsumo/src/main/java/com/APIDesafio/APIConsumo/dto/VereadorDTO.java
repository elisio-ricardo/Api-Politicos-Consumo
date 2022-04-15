package com.APIDesafio.APIConsumo.dto;


import java.util.List;

public class VereadorDTO extends PoliticoDTO implements Comparable<VereadorDTO> {

    protected List<String> processos;

    public VereadorDTO() {
    }

    public VereadorDTO(String nome, String sigla, String nomePartido, String foto, List<String> projetoDeLei, List<String> processos) {
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
    public int compareTo(VereadorDTO dto) {
        return this.getNome().compareToIgnoreCase(dto.getNome());
    }
}
