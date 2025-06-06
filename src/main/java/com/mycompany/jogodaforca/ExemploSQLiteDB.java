/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author kakac
 */
package com.mycompany.jogodaforca; // Ajuste para o seu pacote

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExemploSQLiteDB {

    // Nome do arquivo do banco de dados SQLite que será criado na pasta do projeto
    private static final String URL_DB = "jdbc:sqlite:exemplo_banco_dados.db";

    public ExemploSQLiteDB() {
        // O construtor pode ser vazio ou iniciar a criação da tabela
    }

    // Método para estabelecer a conexão com o banco de dados
    private Connection connect() {
        Connection conn = null;
        try {
            // Carrega o driver JDBC para SQLite
            Class.forName("org.sqlite.JDBC"); // Necessário para algumas versões do JDBC
            conn = DriverManager.getConnection(URL_DB);
            System.out.println("Conexão com o banco de dados estabelecida.");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados SQLite: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do SQLite não encontrado: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    // Método para criar uma tabela de exemplo
    public void criarTabelaExemplo() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n"
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                   + " nome TEXT NOT NULL,\n"
                   + " idade INTEGER\n"
                   + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'usuarios' verificada/criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela 'usuarios': " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para inserir dados na tabela
    public void inserirUsuario(String nome, int idade) {
        String sql = "INSERT INTO usuarios(nome, idade) VALUES(?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.executeUpdate();
            System.out.println("Usuário '" + nome + "' inserido com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para ler dados da tabela
    public void lerUsuarios() {
        String sql = "SELECT id, nome, idade FROM usuarios";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Usuários Cadastrados ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + "\t" +
                                   "Nome: " + rs.getString("nome") + "\t" +
                                   "Idade: " + rs.getInt("idade"));
            }
            System.out.println("--------------------------");
        } catch (SQLException e) {
            System.err.println("Erro ao ler usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método principal para demonstrar o uso (você pode executar este main separadamente)
    public static void main(String[] args) {
        ExemploSQLiteDB db = new ExemploSQLiteDB();

        // 1. Criar a tabela
        db.criarTabelaExemplo();

        // 2. Inserir alguns dados
        db.inserirUsuario("Alice", 30);
        db.inserirUsuario("Bob", 25);
        db.inserirUsuario("Charlie", 35);

        // 3. Ler os dados
        db.lerUsuarios();

        // Tente inserir novamente para ver o comportamento se a tabela já existe
        db.inserirUsuario("Alice", 31); // Ele será inserido novamente a menos que você adicione uma constraint UNIQUE
    }
}