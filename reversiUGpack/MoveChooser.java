import java.util.ArrayList;  

public class MoveChooser {
  
    public static Move chooseMove(BoardState boardState){

	   int searchDepth= Othello.searchDepth;
        //an array containing legal moves
        ArrayList<Move> moves= boardState.getLegalMoves();
        Move finalmove = null;
        //minimum value
        int minvalue = Integer.MIN_VALUE;
        if(moves.isEmpty()){
            return finalmove;
	    }
        else{
            //loop through legal moves
            for(Move move : moves){
                //make a copy
                BoardState copy1 = boardState.deepCopy();
                //update the board 
                copy1.makeLegalMove(move.x, move.y);
                //get the maximum value
                int value = minimax_val(copy1, searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                //minvalue should be equal to the maximum value;we get the best move which leads to the best value
                if(value >= minvalue){
                    minvalue = value;
                    finalmove = move;
                }
            }
            return finalmove;
        }
        
    }


    public static int value(BoardState boardState){
        //array of values
        Integer[][] chessvalue = {
            {120,-20,20,5,5,20,-20,120},
            {-20,-40,-5,-5,-5,-5,-40,-20},
            {20,-5,15,3,3,15,-5,20},
            {5,-5,3,3,3,3,-5,5},
            {5,-5,3,3,3,3,-5,5},
            {20,-5,15,3,3,15,-5,20},
            {-20,-40,-5,-5,-5,-5,-40,-20},
            {120,-20,20,5,5,20,-20,120}
        };

        int values = 0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                //mutiply boardState.getContents(i,j) so that we don't need to judge whether it's black or white
                values += boardState.getContents(i,j) * chessvalue[i][j];
            }
        }
        return values;
    }

    public static int minimax_val(BoardState node, int depth, int alpha, int beta){

        if(depth == 0){
            return value(node);
        }
        //black turn
        else if(node.colour == -1){
            //set beta to finite
            beta = Integer.MAX_VALUE;
            //loop through the legal moves
            for(Move moves : node.getLegalMoves()){
                //when alpha>beta or there's no legal move we jump out of the loop
                if((alpha > beta) || (node.getLegalMoves().size() == 0)){
                    break;
                }
                //make a copy
                BoardState copy = node.deepCopy();
                //update the board
                copy.makeLegalMove(moves.x,moves.y);
                //set beta to the minimum between original beta and it's children value;here we use recurrence
                beta = Math.min(beta, minimax_val(copy,depth - 1,alpha,beta));
                
            }
            
            return beta;

        }
        //white turn
        else{
            //set alpha to minus finite
            alpha = Integer.MIN_VALUE;
            //loop through the legal moves
            for(Move moves : node.getLegalMoves()){
                //when alpha>beta or there's no legal move we jump out of the loop
                if((alpha > beta) || (node.getLegalMoves().size() == 0)){
                    break;
                }
                //make a copy
                BoardState copy = node.deepCopy();
                //update the board
                copy.makeLegalMove(moves.x, moves.y);
                //set alpha to the maximum between original alpha and it's children value;here we use recurrence
                alpha = Math.max(alpha, minimax_val(copy,depth - 1, alpha,beta)); 
                
            }
            

            return alpha;
        }
    
    }


}

