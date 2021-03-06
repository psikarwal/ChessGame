package ChessBoard;

import GAME.*;
import PIECES.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame {
    private JButton pieceToMoveButton = null;//variable that persists between actionPerformed calls
    private Piece tempObject ;
    private static int crrtRow = -1, crrtCol = -1;
    private Color boxColor = Color.BLACK;
    private JPanel pane;
    private JButton board[][] = new JButton[8][8];
    private Player black;
    private Player white;
    private int turn = 0 ;
    private int turn1 = 1;
    private JMenuBar menuBar = new JMenuBar();
    private JButton newGame = new JButton("New Game");//button to be used for starting a new game..
    public Board(Player b, Player w) {
        black = b;
        white = w;
        setJMenuBar(menuBar);
        newGame.setBackground(Color.WHITE);
        newGame.setForeground(Color.BLUE);
        newGame.setBorder(BorderFactory.createRaisedBevelBorder());
        Menu_Listener menuListener = new Menu_Listener();
        menuListener.setObject(this);
        newGame.addActionListener(menuListener);
        menuBar.add(newGame);
        setPieces('W', white);
        setPieces('B', black);
        createBoard();
    }

    public void createBoard() {

        GridLayout boardLayout = new GridLayout(8, 8);
        setTitle("Chess Game");
        pane = new JPanel();
        setBounds(340, 0, 700, 700);
        pane.setSize(640,640);
        pane.setLayout(boardLayout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setBoxColor((i + j) % 2);
                if (board[i][j] != null) {
                    board[i][j].setSize(new Dimension(80, 80));
                    board[i][j].addActionListener(new MoveListener());
                    board[i][j].setBackground(boxColor);
                    pane.add(board[i][j], boardLayout);
                } else {
                    board[i][j] = new JButton();
                    board[i][j].addActionListener(new MoveListener());
                    board[i][j].setSize(new Dimension(80, 80));
                    board[i][j].setBackground(boxColor);
                    pane.add(board[i][j], boardLayout);
                    validate();
                }
            }
        }
        this.getContentPane().add(pane);
        setVisible(true);
    }

    protected void setPieces(char ch, Player p) {
        int i;
        String s ;
        if (ch == 'W') {
            i = 0;
            s = "white";
        } else {
            i = 6;
            s = "black";
        }
        int count = 0;
        while (count < 2) {
            if(i==1||i==6) {
                for (int j = 0; j < 8; j++) {
                    if (i == 1) {
                        p.pawn[j] = new Pawn(white, black, board, "Photos//" + ch + "P.gif", s);
                        board[i][j] = p.pawn[j];
                        board[i][j].setSize(new Dimension(80, 80));
                    } else if (i == 6) {
                        p.pawn[j] = new Pawn(white, black, board, "Photos//" + ch + "P.gif", s);
                        board[i][j] = p.pawn[j];
                        board[i][j].setSize(new Dimension(80, 80));
                    }
                }
            }
            else{
                p.rook[0] = new Rook(white,black,board,"Photos//" + ch + "R.gif", s);
                p.rook[1] = new Rook(white,black,board,"Photos//" + ch + "R.gif", s);

                p.knight[0] = new Knight(white,black,board,"Photos//" + ch + "N.gif", s);
                p.knight[1] = new Knight(white,black,board,"Photos//" + ch + "N.gif", s);

                p.bishop[0] = new Bishop(white,black,board,"Photos//" + ch + "B.gif", s);
                p.bishop[1] = new Bishop(white,black,board,"Photos//" + ch + "B.gif", s);

                p.king = new King(white,black,board,"Photos//" + ch + "K.gif", s);
                p.queen = new Queen(white,black,board,"Photos//" + ch + "Q.gif", s);

                board[i][0] = p.rook[0];
                board[i][7] = p.rook[1];
                board[i][1] = p.knight[0];
                board[i][6] = p.knight[1];
                board[i][2] = p.bishop[0];
                board[i][5] = p.bishop[1];
                board[i][3] = p.king;
                board[i][4] = p.queen;

            }
            count++;
            i++;
        }
    }

    public void setBoxColor(int x) {

        if (x == 0)
            boxColor = Color.GRAY;
        else
            boxColor = Color.WHITE;

    }
    public Player getWhitePlayer(){
        return white;
    }

    public Player getBlackPlayer(){
        return black;
    }



    class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == (JButton)actionEvent.getSource()) {
                        if (pieceToMoveButton == null&&tempObject==null){
                            for(int i=0;i<8;i++){
                                if(board[row][col]==white.pawn[i]) {
                                    tempObject = white.pawn[i];
                                    break;
                                }
                                if(board[row][col]==black.pawn[i]) {
                                    tempObject = black.pawn[i];
                                    break;
                                }
                            }
                            for(int i = 0 ;i<2 ;i++){
                                if(board[row][col] == white.rook[i]) {
                                    tempObject = white.rook[i];
                                    break;
                                }
                                if(board[row][col] == black.rook[i]) {
                                    tempObject = black.rook[i];
                                    break;
                                }
                                if(board[row][col] == white.bishop[i]) {
                                    tempObject = white.bishop[i];
                                    break;
                                }
                                if(board[row][col] == black.bishop[i]) {
                                    tempObject = black.bishop[i];
                                    break;
                                }
                                if(board[row][col] == white.knight[i]) {
                                    tempObject = white.knight[i];
                                }
                                if(board[row][col] == black.knight[i]) {
                                    tempObject = black.knight[i];
                                    break;
                                }
                            }
                            if(board[row][col] == white.king)
                                tempObject = white.king;
                            if(board[row][col] == black.king)
                                tempObject = black.king;
                            if(board[row][col] == white.queen)
                                tempObject = white.queen;
                            if(board[row][col] == black.queen)
                                tempObject = black.queen;
                            // System.out.println("row" + row + "col" + col);

                            if(tempObject!=null){
                                pieceToMoveButton = tempObject;
                                board[row][col] = null;
                                crrtRow = row;
                                crrtCol = col;
                                turn = tempObject.getColor()=="black"?0:1;
                                //System.out.println(turn);
                            }
                            break;
                        }
                        //if this button press is selecting where to move
                        else {
                            if(turn==turn1) {

                                if (tempObject.validateMove(crrtRow, crrtCol, row, col) && turn == turn1) {
                                    board[row][col] = tempObject;
                                    turn1++;
                                    turn1 = turn1 % 2;
                                    getContentPane().removeAll();
                                    createBoard();
                                    pieceToMoveButton = null;    //makes the next button press a piece selection
                                    tempObject = null;
                                    crrtRow = -1;
                                    crrtCol = -1;
                                }
                                else{
                                    board[crrtRow][crrtCol] = pieceToMoveButton;
                                    getContentPane().removeAll();
                                    createBoard();
                                    pieceToMoveButton = null;    //makes the next button press a piece selection
                                    tempObject = null;
                                    crrtRow = -1;
                                    crrtCol = -1;
                                }
                            }
                            else{
                                board[crrtRow][crrtCol] = pieceToMoveButton;
                                getContentPane().removeAll();
                                createBoard();
                                pieceToMoveButton = null;    //makes the next button press a piece selection
                                tempObject = null;
                                crrtRow = -1;
                                crrtCol = -1;
                            }

                        }
                    }
                }
            }
        }
    }

    class Menu_Listener implements ActionListener {
        JFrame frame;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if ((JButton) actionEvent.getSource() == newGame) {
                frame.setVisible(false);
                Player p1 = new Player();
                Player p2 = new Player();
                Board board = new Board(p1, p2);

            }
        }

        public void setObject(JFrame f) {
            frame = f;
        }
    }

}

