package br.com.yurigobatto.dto;

import java.math.BigDecimal;

public class ProdutoDto {
    private final String nome;
    private final Integer quantidade;
    private final BigDecimal valorUnitario;

    public ProdutoDto(String nome, Integer quantidade, BigDecimal valorUnitario) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public String getNome() {
        return nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }
}
