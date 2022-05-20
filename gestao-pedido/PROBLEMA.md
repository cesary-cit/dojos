# Sistema de Gestão de Pedidos
Sistema de gestão fictício para fins educacionais.

---
## Técnologias
* Spring Boot
* Gradle
* Kotlin
* Spock
* Groovy
---

## Proposta
Exercitar práticas de TDD.
Para fins didáticos, detalhes de implementacão de infraestrutura, como banco de dados ou sistema de notificacão, devem ser abstraídos.
---

# Especificacão Funcional

### Alteracão de Endereco de Entrega
Criar caso de uso, onde seja possível a alteracão do endereco de entrega de determinado pedido.
O pedido possui três status possíveis: Pendente, Enviado, Finalizado. 

O endereco é composto por 3 campos:
* CEP
* Cidade
* Número

A operacão de alteracão de endereco é permitida apenas para pedidos pendentes.

#### Critérios de Aceite
1. Caso o pedido a ser alterado não exista, sistema deve lancar erro por pedido não encontrado. Uma mensagem contendo o identificador solicitado deve ser mostrada pelo sistema.
2. Caso o pedido a ser alterado não esteja no status permitido, sistema deve lancar erro por operacão não permitida. Uma mensagem contendo o identificador do pedido, status e operacão deve ser mostrada pelo sistema.
3. O endereco do pedido deve ser atualizado com sucesso, quando seu status for pendente. O registro deve ser atualizado em seu repositório, e o usuário ser notificado da alteracão.
---

### Parcelamento da Compra
Criar caso de uso, onde seja possível efetuar o parcelamento da compra.

#### Condicoes para o parcelamento
1. Pagamento em apenas uma parcela possui desconto de 5%, considerando o valor total do pedido. Neste caso, o arredondamento deve HALF UP, considerando duas casas de precisão.

| Valor Bruto R$ | Valor Arredondado R$ |
|----------------|----------------------|
| 100.0001       | 100.00               |
| 100.0195       | 100.02               |
| 100.1595       | 100.02               |

2. O parcelamento deve ser gerado em no máximo 12 vezes, considerando o valor total do pedido. Caso seja solicitado um número maior de parcelas, sistema deve apresentar erro de parcelamento não permitido, informando o máximo de parcelas permitidas.

#### Regra de Vencimento das parcelas
1. A data de vencimento da primeira parcela deve ser 3 dias após a data atual. Caso o dia seja não útil (sábado ou domingo), postergar para o próximo dia útil.
2. As demais parcelas devem vencer no mesmo dia da primeira parcela, porém nos meses consecutivos. Caso o dia seja não útil (sábado ou domingo), postergar para o próximo dia útil.

#### Arredondamento do Valor das Parcelas
Por se tratar de valor monetário, devemos considerar no máximo duas casa decimais, por exemplo: R$ 50,27.
Caso o valor somado das parcelas não seja exatamente igual ao valor total da compra, a diferenca deve ser colocada na última parcela.

#### Exemplo da Funcionalidade:
Valor total da compra: R$100,00
Quantidade de parcelas: 3

| Número Parcela   | Valor Parcela R$ | Vencimento |
|------------------|------------------|------------|
| 1                | 33.33            | 19/05/2022 |
| 2                | 33.33            | 20/06/2022 |
| 3                | 33.34            | 19/07/2022 |
| Total            | 100.00           | --         |
