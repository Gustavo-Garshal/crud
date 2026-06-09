package br.com.yurigobatto.entities;

import br.com.yurigobatto.orm.Entidade;
import br.com.yurigobatto.orm.annotation.Id;

import java.math.BigDecimal;

public class Produto extends Entidade {
    @Id
    private String id;
    private String nome;
    private Integer quantidade;
    private BigDecimal valorUnitario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
