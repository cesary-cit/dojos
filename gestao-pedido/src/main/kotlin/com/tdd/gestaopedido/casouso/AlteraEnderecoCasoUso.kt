package com.tdd.gestaopedido.casouso

import com.tdd.gestaopedido.dominio.excecao.OperacaoNaoPermitidaException
import com.tdd.gestaopedido.dominio.excecao.PedidoNaoEncontradoExcecao
import com.tdd.gestaopedido.dominio.pedido.Endereco
import com.tdd.gestaopedido.dominio.pedido.PedidoRepositorio
import com.tdd.gestaopedido.dominio.pedido.Status
import org.springframework.stereotype.Service

@Service
class AlteraEnderecoCasoUso(
    private val pedidoRepositorio: PedidoRepositorio
) {
    fun alterar(id: String, endereco: Endereco) {
        val pedido = pedidoRepositorio.recuperarPorId(id) ?: throw PedidoNaoEncontradoExcecao(id)

        if (!pedido.ehPermitidoAlterarEndereco())
            throw OperacaoNaoPermitidaException(pedido.id, pedido.recuperarDescricaoStatus())
    }
}
