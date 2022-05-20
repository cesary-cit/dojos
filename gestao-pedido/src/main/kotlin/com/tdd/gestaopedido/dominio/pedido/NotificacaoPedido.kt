package com.tdd.gestaopedido.dominio.pedido

interface NotificacaoPedido {
    fun enviaNotificacaoSMS(pedido: Pedido)
}