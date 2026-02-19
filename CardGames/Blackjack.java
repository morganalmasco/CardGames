import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Blackjack
{
    //private games myGame;
    //private CardAndDeck newCards;
    private Scanner scan;
    public Blackjack()
    {
        scan = new Scanner(System.in);
        Deck deck = new Deck();
        //Deck deck = new Deck();
        List<Card> dealer = new ArrayList<>();
        List<Card> player = new ArrayList<>();
        
        int playerPoints = 0;
        int dealerPoints = 0;

        deck.shuffle();
        
        //deal initial cards
        for(int counter = 0; counter < 2; counter++)
        {
            dealer.add(deck.draw());
            player.add(deck.draw());
        }
        
        /*for(Card card : deck.getDeck())
        {
            System.out.println(card.getRank() + " OF " + card.getSuit());
        }
        */
        //boolean gameEnd = false;
        boolean stand = false;
        while(true)
        {
            playerPoints = getPointsBJ(player);
            dealerPoints = getPointsBJ(dealer);
            this.displayBJ(dealer,player,stand);

            if(playerPoints == 21)
            {
                System.out.println();
                System.out.println("BLACKJACK! YOU WIN!");
                break;
            }
            if(playerPoints > 21)
            {
                System.out.println();
                System.out.println("OVER 21, YOU LOSE!");
                break;
            }

            if(stand)
            {

                if(playerPoints > dealerPoints)
                {
                    System.out.println();
                    System.out.println("PLAYER BEATS DEALER, YOU WIN!");
                }
                if(playerPoints < dealerPoints)
                {
                    System.out.println();
                    if(dealerPoints > 21)
                        System.out.println("DEALER GOES OVER 21! YOU WIN!");
                    else
                        System.out.println("DEALER BEATS PLAYER, YOU LOSE!");
                }
                if(playerPoints == dealerPoints)
                {
                    System.out.println();
                    System.out.println("STALEMATE!");
                }
                    
                
                break;
            }
            System.out.println("\n\nWould you like to:\n1 : Hit | 2 : Stand");
            int choice = scan.nextInt();

            if(choice == 1)
                player.add(deck.draw());
            if (choice == 2)
            {
                stand = true;
                while(dealerPoints <= 16)
                {
                    dealer.add(deck.draw());
                    dealerPoints = getPointsBJ(dealer);
                }
            }


        }

        
    }

    public void displayBJ(List<Card> dealer, List<Card> player, boolean stand)
    {
        System.out.print("\033[H\033[2J");
        //if player stands, dealer will draw more cards
        if(stand == true)
        {
            //
            for(Card card : dealer)
            {
                System.out.print("|"+card.getRank()+" OF "+card.getSuit()+"|");
                try {
                    Thread.sleep(2000);

                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }   
            }
            System.out.print("\033[H\033[2J");
            System.out.println("Dealer's Hand:"+this.getPointsBJ(dealer));
            for(Card card : dealer)
            {
                System.out.print("|"+card.getRank()+" OF "+card.getSuit()+"|");
            }
        }
        else
        {
            System.out.println("Dealer's Hand:" + dealer.get(0).getValue());
            System.out.print("|"+dealer.get(0).getRank()+" OF "+dealer.get(0).getSuit()+"|"+"|HIDDEN|");
        }
        System.out.println();
        System.out.println();
        System.out.println("Your Hand:"+this.getPointsBJ(player));

        for(Card card : player)
        {
            System.out.print("|"+card.getRank()+" OF "+card.getSuit()+"|");
        }
        //System.out.println("\n");
    }

    public int getPointsBJ(List<Card> deck)
    {
        int points = 0;
        for(Card card : deck)
        {
            if(card.getRank() == Card.Rank.ACE)
            {
                if(points + 11 > 21)
                    card.setValue(1);
            }
            points += card.getValue();
        }

        return points;
    }

    public static void main(String[] args)
    {
        new Blackjack();
    }
}