package br.com.yurigobatto.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PedidoDto {
    private final String cliente;
    private final List<ItemPedidoDto> itens;

    public PedidoDto(String cliente) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public String getCliente() {
        return cliente;
    }

    public List<ItemPedidoDto> getItens() {
        return itens;
    }

    public PedidoDto addItem(ItemPedidoDto item) {
        this.itens.add(item);
        return this;
    }
}
