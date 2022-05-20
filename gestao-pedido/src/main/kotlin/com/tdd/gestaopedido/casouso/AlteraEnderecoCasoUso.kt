package com.tdd.gestaopedido.casouso

import com.tdd.gestaopedido.dominio.excecao.OperacaoNaoPermitidaException
import com.tdd.gestaopedido.dominio.excecao.PedidoNaoEncontradoExcecao
import com.tdd.gestaopedido.dominio.pedido.*
import org.springframework.stereotype.Service

@Service
class AlteraEnderecoCasoUso(
    private val pedidoRepositorio: PedidoRepositorio,
    private val notificacaoPedido: NotificacaoPedido,
) {
    fun alterar(id: String, endereco: Endereco): Pedido {
        val pedido = pedidoRepositorio.recuperarPorId(id) ?: throw PedidoNaoEncontradoExcecao(id)

        if (!pedido.ehPermitidoAlterarEndereco())
            throw OperacaoNaoPermitidaException(pedido.id, pedido.recuperarDescricaoStatus())

        pedidoRepositorio.atualizar(pedido)
        notificacaoPedido.enviaNotificacaoSMS(pedido)

        return  pedido
    }
}
