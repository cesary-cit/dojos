package com.tdd.gestaopedido.dominio.excecao

class PedidoNaoEncontradoExcecao(id: String) : RuntimeException("Pedido nao encontrado para o id $id")
