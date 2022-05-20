package com.tdd.gestaopedido.dominio.pedido

class Pedido(
    val id: String,
    val status: Status,
    val endereco: Endereco
) {
    fun ehPermitidoAlterarEndereco(): Boolean {
        return status == Status.PENDENTE
    }

    fun recuperarDescricaoStatus() = status.descricao
}
