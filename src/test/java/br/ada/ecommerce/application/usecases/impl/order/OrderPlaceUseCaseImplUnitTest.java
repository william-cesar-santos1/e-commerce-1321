package br.ada.ecommerce.application.usecases.impl.order;

public class OrderPlaceUseCaseImplUnitTest {

    /*
    - pedido esta com estado de aguardando pagamento, fechar esse pedido deve resultar em exceção.
        -- Pedido com estado aguardando pagamento
        -- Chamar a ação de fechar
        -- Deve lançar a exceção
     */

    /*
    - pedido com estado de open e sem itens, não deve ser possível fechar.
        -- Pedido com estado open, porém sem itens.
        -- Chamar a ação de fechar
        -- Deve lançar a exceção
     */

    /*
    - pedido com estado de open e cem itens, deve ser alterado para aguardando pagamento.
        -- Pedido com esta de open, contendo itens
        -- Chamar a ação de fechar
        -- Deve alterar o estado para aguardando pagamento
     */
}
