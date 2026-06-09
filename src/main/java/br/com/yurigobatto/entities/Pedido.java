package br.com.yurigobatto.entities;

import br.com.yurigobatto.orm.Entidade;
import br.com.yurigobatto.orm.annotation.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Entidade {
    @Id
    private String id;
    private int numeroPedido;
    private String cliente;
    private BigDecimal valorTotal;
    private List<ItemPedido> itens;

    public Pedido() {
        itens = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void addItem(ItemPedido item) {
        this.itens.add(item);
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        this.valorTotal = this.itens.stream()
                .map(ItemPedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
