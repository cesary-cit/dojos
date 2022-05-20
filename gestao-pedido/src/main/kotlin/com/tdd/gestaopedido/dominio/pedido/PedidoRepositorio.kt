package com.tdd.gestaopedido.dominio.pedido

interface PedidoRepositorio {
    fun recuperarPorId(id: String): Pedido?
}
