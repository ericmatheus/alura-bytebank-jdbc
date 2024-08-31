package br.com.alura.bytebank;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection recuperarConexao(){
        try{
//            String url = "jdbc:mysql://localhost:3306/byte_bank";
//            String user = "root";
//            String password = "Eric0762";
//            DriverManager.getConnection() = estabelece uma conexão com um banco de dados
//            return  DriverManager.getConnection(url,user, password);
//            return DriverManager.getConnection("jdbc:mysql://localhost:3306/byte_bank?user=root&password=Eric0762");
            return createDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/byte_bank");
        config.setUsername("root");
        config.setPassword("Eric0762");
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }

//    public static void main(String[] args) {
//        try{
//            String url = "jdbc:mysql://localhost:3306/byte_bank";
//            String user = "root";
//            String password = "Eric0762";
////            DriverManager.getConnection() = estabelece uma conexão com um banco de dados
//            Connection conn =  DriverManager.getConnection(url,user, password);
//            System.out.println("Conexao recuperada");
//            conn.close();
//            System.out.println("Conexao encerrada");
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//    }

}
