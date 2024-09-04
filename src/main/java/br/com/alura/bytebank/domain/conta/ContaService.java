package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.Cliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaService {

    public final String SET_FUNCAO_BANCO = "posstegre";
//    public final String SET_FUNCAO_BANCO = "mysql";

    private ConnectionFactory connection;

    public ContaService() {
        this.connection = new ConnectionFactory();
    }

    private Set<Conta> contas = new HashSet<>();

    public Set<Conta> listarContasAbertas() {

        Connection conn = (SET_FUNCAO_BANCO == "mysql") ? connection.recuperarConexao() : connection.recuperarConexaoPostgree() ;
        contas = new ContaDAO(conn).listar();
        return contas;
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
//        var cliente = new Cliente(dadosDaConta.dadosCliente());
//        var conta = new Conta(dadosDaConta.numero(), cliente);
//        if (contas.contains(conta)) {
//            throw new RegraDeNegocioException("Já existe outra conta aberta com o mesmo número!");
//        }
        Connection conn = connection.recuperarConexaoPostgree();

//        ContaDAO contaDAO = new ContaDAO(conn);
//        contaDAO.salvar(dadosDaConta);

        new ContaDAO(conn).salvar(dadosDaConta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }
        conta.sacar(valor);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }
        conta.depositar(valor);
    }

    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }

        String sql = "DELETE FROM conta WHERE numero = " + numeroDaConta;
        System.out.println(sql);

        Connection conn = connection.recuperarConexaoPostgree();

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
            System.out.println("preparedStatement.execute(); --- Executado");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    private Conta buscarContaPorNumero(Integer numero) {
        return contas
                .stream()
                .filter(c -> c.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Não existe conta cadastrada com esse número!"));
    }
}
