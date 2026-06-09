package br.com.yurigobatto.entities;

import br.com.yurigobatto.orm.Entidade;

import java.math.BigDecimal;

public class ItemPedido extends Entidade {

    private final String pedidoId;
    private final String produtoId;
    private Integer quantidade;
    private final BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    public ItemPedido(Pedido pedido, Produto produto) {
        this.pedidoId = pedido.getId();
        this.produtoId = produto.getId();
        this.valorUnitario = produto.getValorUnitario();
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularValorTotal();
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    private void calcularValorTotal() {
        valorTotal = valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
