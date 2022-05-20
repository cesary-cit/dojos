package unit

import com.tdd.gestaopedido.casouso.AlteraEnderecoCasoUso
import com.tdd.gestaopedido.dominio.excecao.OperacaoNaoPermitidaException
import com.tdd.gestaopedido.dominio.excecao.PedidoNaoEncontradoExcecao
import com.tdd.gestaopedido.dominio.pedido.Endereco
import com.tdd.gestaopedido.dominio.pedido.NotificacaoPedido
import com.tdd.gestaopedido.dominio.pedido.Pedido
import com.tdd.gestaopedido.dominio.pedido.PedidoRepositorio
import com.tdd.gestaopedido.dominio.pedido.Status
import spock.lang.Specification

class AlteraEnderecoCasoUseTest extends Specification {
    private AlteraEnderecoCasoUso alteraEnderecoCasoUso
    private PedidoRepositorio pedidoRepositorioMock = Mock(PedidoRepositorio)
    private NotificacaoPedido notificacaoPedido = Mock(NotificacaoPedido)
    def setup() {
        alteraEnderecoCasoUso = new AlteraEnderecoCasoUso(pedidoRepositorioMock,notificacaoPedido)
    }

    def "Deve lancar erro por pedido nao encontrado quando nao existe nenhum pedido para o id solicitado"() {
        given: "um id invalido"
        def id = "INVALID_ID"
        and: "um novo endereco"
        def novoEndereco = new Endereco("Campinas", "13087500", "501")

        when: "solicitar alteracao de endereco"
        alteraEnderecoCasoUso.alterar(id, novoEndereco)

        then: "um erro de pedido nao encontrado eh lancado"
        def error = thrown(PedidoNaoEncontradoExcecao)
        and: "a mensagem de erro contem o identificado solicitado"
        error.message.contains(id)
    }

    def "Deve lancar erro de operacao nao permitida quando status do pedido nao for pendente"() {
        given: "um identificador de pedido finalizado"
        def id = "ID_FINALIZADO"
        and: "um novo endereco"
        def novoEndereco = new Endereco("Campinas", "13087500", "501")

        and: "simula o retorno de um pedido finalizado"
        pedidoRepositorioMock.recuperarPorId(id) >> new Pedido(id, Status.FINALIZADO, novoEndereco)

        when: "solicitar alteracao do endereco"
        alteraEnderecoCasoUso.alterar(id, novoEndereco)

        then: "um erro de operacao nao permitida eh lancado"
        def error = thrown(OperacaoNaoPermitidaException)

        and: "a mensagem de erro contem o id"
        error.message.contains(id)
        and: "o status"
        error.message.contains("finalizado")
    }

    //O endereco do pedido deve ser atualizado com sucesso, quando seu status for pendente.
    // O registro deve ser atualizado em seu repositório, e o usuário ser notificado da alteracão.

    def "Deve atualizar o pedido com sucesso quando o status for pendente, o pedido e o registro no repositorio"() {
        given: "um identificador de pedido pendente"
        def id = "ID_PENDENTE"

        and: "um novo endereco"
        def novoEndereco = new Endereco("Sao Paulo", "12345600", "100")

        and: "simula o retorno de um pedido pendente"
        pedidoRepositorioMock.recuperarPorId(id) >> new Pedido(id, Status.PENDENTE, novoEndereco)

        when: "solicitar alteracao do endereco"
        def pedido = alteraEnderecoCasoUso.alterar(id, novoEndereco)

        then: "o endereço eh atualizado com sucesso"
        pedido.endereco == novoEndereco

        and: "o registro eh atualizado no repositório"
        1 * pedidoRepositorioMock.atualizar(_)

        and: "o usuario eh notificado da alteracao"
        1 * notificacaoPedido.enviaNotificacaoSMS(_)

    }
}
