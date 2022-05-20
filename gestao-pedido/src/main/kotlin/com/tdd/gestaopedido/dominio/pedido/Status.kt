package com.tdd.gestaopedido.dominio.pedido

enum class Status(val descricao: String) {
    PENDENTE("pendente"),
    ENVIADO("enviado"),
    FINALIZADO("finalizado")
}
