
import java.util.Scanner;

//Allows player to choose game, displays game
public class games
{
    private Scanner scan;
    
    public games()
    {
        scan = new Scanner(System.in);
        int game = scan.nextInt();
        if(game == 1)
            new Blackjack();

    }

    public static void main(String[] args)
    {
        games myGames = new games();
        //myGames.blackjack();

    }


}

