package com.tdd.gestaopedido.dominio.excecao

class OperacaoNaoPermitidaException(id: String, status: String)
    : RuntimeException("Operacao nao permitida. Id: $id e status $status") {
}
