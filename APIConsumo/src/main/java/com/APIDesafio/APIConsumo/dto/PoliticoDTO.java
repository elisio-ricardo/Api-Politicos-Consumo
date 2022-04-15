package com.APIDesafio.APIConsumo.dto;

import java.util.List;

public abstract class PoliticoDTO {

    protected String nome;
    protected String sigla;
    protected String nomePartido;
    protected String foto;
    protected List<String> projetoDeLei;

    public PoliticoDTO() {
    }

    public PoliticoDTO(String nome, String sigla, String nomePartido, String foto, List<String> projetoDeLei) {
        this.nome = nome;
        this.sigla = sigla;
        this.nomePartido = nomePartido;
        this.foto = foto;
        this.projetoDeLei = projetoDeLei;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNomePartido() {
        return nomePartido;
    }

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<String> getProjetoDeLei() {
        return projetoDeLei;
    }

    public void setProjetoDeLei(List<String> projetoDeLei) {
        this.projetoDeLei = projetoDeLei;
    }

}
