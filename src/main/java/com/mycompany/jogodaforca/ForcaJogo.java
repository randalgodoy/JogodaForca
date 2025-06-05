/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jogodaforca;

/**
 *
 * @author luanalves
 */
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter; 
import java.io.PrintWriter; 
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 


public class ForcaJogo extends JFrame {
    private Palavra palavraSecreta;
    private Set<Character> letrasDigitadas = new HashSet<>();
    private int erros = 0;
    private final int maxErros = 6;

    private JLabel palavraLabel;
    private JTextField letraField;
    private JButton tentarButton;
    private JLabel mensagemLabel;
    private JLabel dicaLabel;
    private PainelBoneco painelBoneco;
    private JComboBox<String> dificuldadeCombo;
    private JButton iniciarButton;
    private static final String ARQUIVO_HISTORICO = "historico_progresso_forca.txt";

   private final List<Palavra> palavras = Arrays.asList(
    // Fácil
    new PalavraFacil("gato", "Animal doméstico que mia"),
    new PalavraFacil("sol", "Estrela que ilumina o dia"),
    new PalavraFacil("bola", "Objeto redondo usado em esportes"),
    new PalavraFacil("rio", "Curso de água natural"),
    new PalavraFacil("pao", "Alimento feito de farinha e fermento"),

    // Médio
    new PalavraMedia("cachorro", "Melhor amigo do homem"),
    new PalavraMedia("janela", "Permite a entrada de luz em casa"),
    new PalavraMedia("cadeira", "Usada para sentar"),
    new PalavraMedia("escola", "Lugar para aprender"),
    new PalavraMedia("viagem", "Ato de ir para outro lugar"),

    // Difícil
    new PalavraDificil("girassol", "Planta que segue o sol"),
    new PalavraDificil("biblioteca", "Lugar cheio de livros"),
    new PalavraDificil("paralelepipedo", "Pedra usada para pavimentar ruas"),
    new PalavraDificil("orquestra", "Grupo que toca música clássica"),
    new PalavraDificil("astronomia", "Ciência que estuda o universo")
);



    public ForcaJogo() {
        super("Jogo da Forca");
        setLayout(new BorderLayout(10, 10)); // espaço entre regiões

        // **Painel superior com seleção de dificuldade (na parte de cima, ocupando toda largura)**
        JPanel topoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dificuldadeCombo = new JComboBox<>(new String[]{"Fácil", "Médio", "Difícil"});
        iniciarButton = new JButton("Iniciar Jogo");
        topoPanel.add(new JLabel("Dificuldade:"));
        topoPanel.add(dificuldadeCombo);
        topoPanel.add(iniciarButton);
        add(topoPanel, BorderLayout.NORTH);

        // **Painel do boneco à esquerda**
        painelBoneco = new PainelBoneco();
        painelBoneco.setPreferredSize(new Dimension(200, 300));
        add(painelBoneco, BorderLayout.WEST);

        // **Painel direito que conterá palavra, dica, mensagens e input**
        JPanel painelDireito = new JPanel(new BorderLayout(10,10));
        painelDireito.setPreferredSize(new Dimension(400, 300));

        // Palavra + dica + mensagem no topo do painel direito
        JPanel topoDireito = new JPanel(new BorderLayout(5,5));

        palavraLabel = new JLabel("Palavra:");
        palavraLabel.setFont(new Font("Arial", Font.BOLD, 32));
        palavraLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topoDireito.add(palavraLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        mensagemLabel = new JLabel("Escolha a dificuldade e clique em Iniciar Jogo");
        mensagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dicaLabel = new JLabel("");
        dicaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(mensagemLabel);
        infoPanel.add(dicaLabel);

        topoDireito.add(infoPanel, BorderLayout.CENTER);

        painelDireito.add(topoDireito, BorderLayout.CENTER);

        // Input + botão no rodapé do painel direito
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        letraField = new JTextField(2);
        letraField.setFont(new Font("Arial", Font.PLAIN, 24));
        tentarButton = new JButton("Tentar");
        inputPanel.add(new JLabel("Letra: "));
        inputPanel.add(letraField);
        inputPanel.add(tentarButton);

        painelDireito.add(inputPanel, BorderLayout.SOUTH);

        add(painelDireito, BorderLayout.EAST);

        // Eventos
        tentarButton.addActionListener(e -> verificarLetra());
        letraField.addActionListener(e -> verificarLetra());
        iniciarButton.addActionListener(e -> iniciarNovoJogo());

        setSize(650, 400);
        setLocationRelativeTo(null); // centraliza janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void iniciarNovoJogo() {
        String dificuldade = (String) dificuldadeCombo.getSelectedItem();
        List<Palavra> filtradas = new ArrayList<>();

        for (Palavra p : palavras) {
            if (dificuldade.equals("Fácil") && p instanceof PalavraFacil
                    || dificuldade.equals("Médio") && p instanceof PalavraMedia
                    || dificuldade.equals("Difícil") && p instanceof PalavraDificil) {
                filtradas.add(p);
            }
        }

        palavraSecreta = filtradas.get(new Random().nextInt(filtradas.size()));
        letrasDigitadas.clear();
        erros = 0;
        atualizarPalavraExibida();
        mensagemLabel.setText("Digite uma letra e clique em Tentar");
        dicaLabel.setText("Dica: " + palavraSecreta.getDica());
        painelBoneco.setErros(erros);
        letraField.setEnabled(true);
        tentarButton.setEnabled(true);
        letraField.requestFocus();
    }

    private void atualizarPalavraExibida() {
        StringBuilder exibida = new StringBuilder();
        for (char c : palavraSecreta.getTexto().toCharArray()) {
            exibida.append(letrasDigitadas.contains(c) ? c : "_").append(" ");
        }
        palavraLabel.setText(exibida.toString());
    }

    private void verificarLetra() {
        String entrada = letraField.getText().toLowerCase();
        letraField.setText("");

        if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
            mensagemLabel.setText("Digite apenas uma letra válida.");
            return;
        }

        char letra = entrada.charAt(0);
        if (letrasDigitadas.contains(letra)) {
            mensagemLabel.setText("Você já tentou essa letra.");
            return;
        }

        letrasDigitadas.add(letra);

        if (palavraSecreta.getTexto().indexOf(letra) >= 0) {
            mensagemLabel.setText("Boa! A letra está na palavra.");
        } else {
            erros++;
            mensagemLabel.setText("Letra incorreta! Erros: " + erros);
        }

        atualizarPalavraExibida();
        painelBoneco.setErros(erros);

        if (erros >= maxErros) {
        mensagemLabel.setText("Você perdeu! A palavra era: " + palavraSecreta.getTexto());
        tentarButton.setEnabled(false);
        letraField.setEnabled(false);
     
    } else if (palavraCompletada()) {
        mensagemLabel.setText("Parabéns! Você acertou a palavra.");
        tentarButton.setEnabled(false);
        letraField.setEnabled(false);
     
        salvarProgresso(); //
    }
}

    private boolean palavraCompletada() {
        for (char c : palavraSecreta.getTexto().toCharArray()) {
            if (!letrasDigitadas.contains(c)) return false;
        }
        return true;
    }
    
    private void salvarProgresso() {
    // A data e hora da vitória
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    // A palavra que foi adivinhada
    String palavraAdivinhada = palavraSecreta.getTexto();
    // A dificuldade que estava sendo jogada
    String dificuldadeAtual = (String) dificuldadeCombo.getSelectedItem();

    try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_HISTORICO, true))) { 
        writer.println("Data/Hora: " + timestamp +
                       ", Dificuldade: " + dificuldadeAtual +
                       ", Palavra Adivinhada: " + palavraAdivinhada);
        System.out.println("Progresso salvo: " + palavraAdivinhada + " (" + dificuldadeAtual + ")"); 
    } catch (IOException e) {
        System.err.println("Erro ao salvar progresso no arquivo: " + e.getMessage());
        e.printStackTrace(); 
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ForcaJogo::new);
    }
}

class Palavra {
    private final String texto;
    private final String dica;

    public Palavra(String texto, String dica) {
        this.texto = texto.toLowerCase();
        this.dica = dica;
    }

    public String getTexto() {
        return texto;
    }

    public String getDica() {
        return dica;
    }
}

class PalavraFacil extends Palavra {
    public PalavraFacil(String texto, String dica) {
        super(texto, dica);
    }
}

class PalavraMedia extends Palavra {
    public PalavraMedia(String texto, String dica) {
        super(texto, dica);
    }
}

class PalavraDificil extends Palavra {
    public PalavraDificil(String texto, String dica) {
        super(texto, dica);
    }
}

class PainelBoneco extends JPanel {
    private int erros = 0;

    public void setErros(int erros) {
        this.erros = erros;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharBoneco(g);
    }

    private void desenharBoneco(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(20, 280, 180, 280);
        g.drawLine(100, 280, 100, 50);
        g.drawLine(100, 50, 160, 50);
        g.drawLine(160, 50, 160, 80);

        if (erros >= 1) g.drawOval(140, 80, 40, 40);
        if (erros >= 2) g.drawLine(160, 120, 160, 180);
        if (erros >= 3) g.drawLine(160, 140, 130, 160);
        if (erros >= 4) g.drawLine(160, 140, 190, 160);
        if (erros >= 5) g.drawLine(160, 180, 130, 210);
        if (erros >= 6) g.drawLine(160, 180, 190, 210);
    }
}

