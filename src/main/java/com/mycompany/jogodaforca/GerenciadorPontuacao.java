 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.forca.game.java;

/**
 *
 * @author kakac
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorPontuacao {

    public void salvarPontuacao(long nivelAtual, String palavraAdivinhada, String nomeArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo, true))) { 
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            bw.write("Nível: " + nivelAtual + ", Palavra: " + palavraAdivinhada + ", Data/Hora: " + timestamp);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar pontuação: " + e.getMessage());
        }
    }

  
    public List<String> carregarHistorico(String nomeArquivo) {
        List<String> historico = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                historico.add(linha); 
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar histórico de pontuações: " + e.getMessage());
        }
        return historico;
    }
}