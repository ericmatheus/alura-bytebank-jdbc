package br.com.alura.bytebank;

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
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/byte_bank?user=root&password=Eric0762");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

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
