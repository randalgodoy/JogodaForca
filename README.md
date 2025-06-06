<p align="center">
  <img src="https://github.com/randalgodoy/JogodaForca/blob/main/jogo-da-forca.jpeg" />
</p>

## História
Este jogo é um clássico de toda geração. Embora não tenha registros documentados sobre sua origem, acredita-se que sua primeira versão tenha aparecido em 1894, no livro The Traditional Games of England, Scotland, and Ireland. Nessa edição, mencionavaum jogo denominado Birds, Beasts, and Fishes, no qual os jogadores adivinhavam palavras relacionadas a animais.
Ao longo dos anos, o jogo foi se desenvolvendo, criando várias temáticas de palavras e, além disso, tornou-se uma excelente ferramenta educativa utilizada por professores, com a função de melhorar a ortografia e a gramática dos seus alunos.

# 🎮 Jogo da Forca

Bem-vindo ao Jogo da Forca em Java Swing. Este jogo é composto por grau de dificuldade,uma imagem representada por um boneco e um  histórico de vitórias salvar em arquivo txt. Além disso, foi desenvolvido como projeto de aprendizado em Programação Orientada a Objetos.

## Objetivo do Jogo:

O Objetivo é tentar adivinhar palavras ocultas para que evitar o enforcamento do boneco.

## 🧠 Especificações
-  Interface gráfica utilizando Swing e layouts(BorderLayout, FlowLayout, GridLayout)
-  Grau de dificuldade do jogo é composto por três niveis: Fácil, Médio e Dificil.
-  Imagem representada de um boneco desenhado progressivamente a cada tentativa
-  Salvamento de vitórias em arquivo txt.
-  Presente o uso de herança e poliformismo no jogo intuito de classificar as palavras por dificuldade

## 🚀Como Jogar

## Requisitos

- Java JDK 8 ou superior
- IDE como NetBeans, IntelliJ ou Eclipse (ou terminal)

### Passo a passo:

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/jogo-da-forca.git
   ```
2. Compile os arquivos:
   ```bash
   javac ForcaJogo.java
   ```
3. Execute o jogo:
   ```bash
   java ForcaJogo
   ```

## Estrutura do Código  

- `ForcaJogo.java`: classe principal com toda a lógica do jogo e interface
- `Palavra`: classe base com `texto` e `dica`
- `PalavraFacil`, `PalavraMedia`, `PalavraDificil`: classes que estendem `Palavra` com herança
- `PainelBoneco`: classe que desenha o boneco da forca no painel

## 🧰 Tecnologias utilizadas

- Java
- Swing (JLabel, JButton, JTextField, JPanel, JComboBox)
- Layouts: `BorderLayout`, `FlowLayout`, `GridLayout`
- Princípios de POO: herança, polimorfismo, encapsulamento

## 👥 Autor
Este projeto foi desenvolvido em grupo como parte de um trabalho acadêmico:

-Caio Rafael da Encarnação Freitas - RA 12724239863

-Felipe Barbosa da Silva - RA 824226505

-Israel Ruan Malta Rosas - RA 12724215274

-Leonardo Rodrigo Arouca - RA 12725172861

-Luan Alves - RA 824219029

-Lucas Fernando silva De Oliveira -RA825115120

-Mateus Cavalcante Oliveira - RA 13525121079

-Murilo Serra Lira RA 825152502

-Rafael de Figueiredo Mattos Almeida - RA 12724227451

-Randal Godoy Fernandes -  RA 12625117571

-Samuel Kaone Rodrigues Argolo - RA 722423006




