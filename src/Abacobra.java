import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.random.*;
import java.util.Random;
import javax.swing.*;
public class Abacobra extends JPanel implements ActionListener, KeyListener{




    private class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    Tile cabecacobra;
    ArrayList<Tile> corpodacobra;
    Tile comida;
    Tile comida2;
    Random random;

    Timer tempodejogo; //basicamente o jogo ficar se "redesenhando"
    int velocidadex;
    int velocidadey;
    boolean fimdejogo= false;
    Abacobra(int boardWidth,int boardHeight) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.green);
        addKeyListener(this);
        setFocusable(true);
        cabecacobra = new Tile(5,5);
        corpodacobra = new ArrayList<Tile>();
        comida = new Tile(18,20);
        comida2 = new Tile(18,20);
        random = new Random();
        placeComida();
        random = new Random();
        placeComida2();
        velocidadex=0;
        velocidadey=0;
        tempodejogo = new Timer(100,this);
        tempodejogo.start();

    }
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        // tamanho do píxel, mudar ali em cima pra ver diferença
        /*for(int x=0; x<boardWidth/tileSize;x++){
            g.drawLine(x*tileSize,0,x*tileSize, boardHeight);
            g.drawLine(0,x*tileSize,boardWidth, x*tileSize);
        }*/
        //comida
        g.setColor(Color.red);
        g.fillRect(comida.x*tileSize, comida.y*tileSize,tileSize, tileSize);
        //megacomida
        g.setColor(Color.MAGENTA);
        g.fillRect(comida2.x*tileSize, comida2.y*tileSize,tileSize, tileSize);
        //cobrita
        g.setColor(Color.black);
        g.fillRect(cabecacobra.x *tileSize,cabecacobra.y*tileSize, tileSize, tileSize);
        //corpo
        for(int i=0; i<corpodacobra.size();i++){
            Tile partedacobra = corpodacobra.get(i);
            g.fillRect(partedacobra.x*tileSize, partedacobra.y*tileSize,tileSize,tileSize);
        }
        //pontuação
        g.setFont(new Font("Arial", Font.PLAIN,16));
        if(fimdejogo){
            g.setFont(new Font("Arial", Font.PLAIN,40));
            g.setColor(Color.red);
            g.drawString("MORREU KKKKKK, Pontuação: " + String.valueOf(corpodacobra.size()),tileSize+40,tileSize+100);
        }
        else{
            g.setColor(Color.blue);
            g.drawString("Pontuação: " + String.valueOf(corpodacobra.size()),tileSize,tileSize);
        }
    }
    public void placeComida(){
        comida.x = random.nextInt(boardWidth/tileSize); //900/25 = 36, 1 ao 36
        comida.y = random.nextInt(boardHeight/tileSize);
    }
    public void placeComida2(){
        comida2.x = random.nextInt(boardWidth/tileSize); //900/25 = 36, 1 ao 36
        comida2.y = random.nextInt(boardHeight/tileSize);
    }
    public boolean colisao(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    private void movimento() {
        //comer
        if(colisao(cabecacobra,comida)){
            corpodacobra.add(new Tile(comida.x,comida.y));
            placeComida();
        }
        if(colisao(cabecacobra,comida2)){
            corpodacobra.add(new Tile(comida2.x,comida2.y));
            corpodacobra.add(new Tile(comida2.x,comida2.y));
            placeComida2();
        }
        //corpo
        for(int i= corpodacobra.size()-1;i>=0;i--){
            Tile partedacobra = corpodacobra.get(i);
            if(i==0){
               partedacobra.x=cabecacobra.x;
               partedacobra.y=cabecacobra.y;
            }
            else{
                Tile Cobraprevia = corpodacobra.get(i-1);
                partedacobra.x=Cobraprevia.x;
                partedacobra.y=Cobraprevia.y;
            }
        }
        //cabeça do bixo
        cabecacobra.x += velocidadex;
        cabecacobra.y += velocidadey;
        //fimdejogo
        for(int i=0; i<corpodacobra.size();i++){
            Tile partedacobra = corpodacobra.get(i);
            if(colisao(cabecacobra,partedacobra)){
                fimdejogo=true;
            }
        }
    if(cabecacobra.x*tileSize<0 || cabecacobra.x*tileSize>boardWidth ||cabecacobra.y*tileSize<0||cabecacobra.y*tileSize>boardHeight) {
     fimdejogo=true;
    }
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        movimento();
        repaint();
        if(fimdejogo){
            tempodejogo.stop();
        }
    }
    @java.lang.Override
    public void keyTyped(java.awt.event.KeyEvent e) { //-> n precisa

    }

    @java.lang.Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocidadey!=1){
            velocidadex=0;
            velocidadey=-1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocidadey!=-1){
            velocidadex=0;
            velocidadey=1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT&& velocidadex!=1){
            velocidadex=-1;
            velocidadey=0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT&& velocidadex!=-1){
            velocidadex=1;
            velocidadey=0;
        }
    }

   @java.lang.Override
    public void keyReleased(java.awt.event.KeyEvent e) {//-> n precisa

    }


}
