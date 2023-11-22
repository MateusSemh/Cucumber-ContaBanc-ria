import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Classe Conta que representa uma conta bancária.
 */
public class Conta {
	
    // Variáveis de instância para saldo e exceção
    private double saldo;
    private RuntimeException exception;

    /**
     * Inicializa um cliente especial com um saldo específico.
     * 
     * @param saldoInicial O saldo inicial do cliente especial.
     */
    @Given("^um cliente especial com saldo atual de -(\\d+) reais$")
    public void um_cliente_especial_com_saldo_atual_de_reais(int saldoInicial) {
        criarClienteEspecialComSaldo(saldoInicial);
    }

    /**
     * Inicializa um cliente comum com um saldo específico.
     * 
     * @param saldoInicial O saldo inicial do cliente comum.
     */
    @Given("^um cliente comum com saldo atual de -(\\d+) reais$")
    public void um_cliente_comum_com_saldo_atual_de_reais(int saldoInicial) {
        criarClienteComumComSaldo(saldoInicial);
    }

    /**
     * Simula um saque na conta do cliente.
     * 
     * @param valorSaque O valor a ser sacado.
     */
    @When("^for solicitado um saque no valor de (\\d+) reais$")
    public void for_solicitado_um_saque_no_valor_de_reais(int valorSaque) {
        try {
            // Verifica se o valor do saque é válido e se há saldo suficiente
            if (valorSaque > 0 && saldo + valorSaque >= 0) {
                saldo -= valorSaque; // Efetua o saque
            } else {
                throw new RuntimeException("Saldo insuficiente para o saque.");
            }
        } catch (RuntimeException e) {
            exception = e; // Captura a exceção para verificação posterior
        }
    }

    /**
     * Verifica se o saque foi efetuado corretamente e atualiza o saldo.
     * 
     * @param novoSaldo O novo saldo esperado após o saque.
     */
    @Then("^deve efetuar o saque e atualizar o saldo da conta para -(\\d+) reais$")
    public void deve_efetuar_o_saque_e_atualizar_o_saldo_da_conta_para_reais(int novoSaldo) {
        if (saldo == -novoSaldo) {
            System.out.println("Saque efetuado com sucesso. Novo saldo: " + saldo);
        } else {
            throw new RuntimeException("Falha ao efetuar o saque.");
        }
    }

    /**
     * Verifica se o saque não foi efetuado e a mensagem de saldo insuficiente foi gerada.
     */
    @Then("^não deve efetuar o saque e deve retornar a mensagem Saldo Insuficiente$")
    public void nao_deve_efetuar_o_saque_e_deve_retornar_a_mensagem_Saldo_Insuficiente() {
        if (exception != null && exception.getMessage().equals("Saldo insuficiente para o saque.")) {
            System.out.println("Saque não efetuado. Saldo insuficiente.");
        } else {
            throw new RuntimeException("Falha ao verificar o saldo insuficiente.");
        }
    }

    /**
     * Cria um cliente especial com o saldo inicial especificado.
     * 
     * @param saldoInicial O saldo inicial do cliente especial.
     */
    private void criarClienteEspecialComSaldo(double saldoInicial) {
        saldo = -saldoInicial;
    }

    /**
     * Cria um cliente comum com o saldo inicial especificado.
     * 
     * @param saldoInicial O saldo inicial do cliente comum.
     */
    private void criarClienteComumComSaldo(double saldoInicial) {
        saldo = -saldoInicial;
    }
}
