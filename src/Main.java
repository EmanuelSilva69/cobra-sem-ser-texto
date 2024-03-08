
import javax.swing.*;
public class Main {
    public static void main(String[] args) throws Throwable {
        int boardWidth = 900;
        int boardHeight = boardWidth;
        JFrame imagem = new JFrame("Cobra");
        imagem.setVisible(true);
        imagem.setSize(boardWidth,boardHeight);
        imagem.setLocationRelativeTo(null);
        imagem.setResizable(false);
        imagem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Abacobra jogo = new Abacobra(boardWidth,boardWidth);
        imagem.add(jogo);
        imagem.pack();
        jogo.requestFocus();
}
}