
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
//only 2 players total
//start: deck is created and shuffled, each player is dealt 7 cards
// 1. user always starts, can choose between players to ask for cards.
//
//Agenda - Scenarios when player/dealer has no cards left and/or no more cards left in deck
// If deck.size() > 0 ->
//      If user.size() == 0 || dealer.size() == 0, then draw 4 or remaining cards from deck
// else ->
//      a
public class GoFish
{
    private Scanner scan;
    private List<Card> user, dealer;
    private Deck deck;
    private Map<Card.Rank,List<Card>> userBooks, dealerBooks;
    private Map<Map<Card.Rank,List<Card>>, List<Card>> updatedUser, updatedDealer;
    private Random ran;
    public GoFish()
    {
        ran = new Random();
        scan = new Scanner(System.in);
        deck = new Deck();
        deck.shuffle();

        user = new ArrayList<>();
        dealer = new ArrayList<>();

        for(int initial = 0; initial < 0; initial++)
        {
            user.add(deck.draw());
            dealer.add(deck.draw());
            //System.out.println(deck.getSize());
        }

        
        //Map<Map<Card.Rank,List<Card>>, List<Card>> updatedUser = hasBook(user);
        //updatedUser = hasBook(user);
        //Map<Card.Rank,List<Card>> books = new HashMap<>();
        int totalBooks = 0;
        int dealerBooksSize = 0;
        int userBooksSize = 0;

        userBooks = new HashMap<>();
        dealerBooks = new HashMap<>();

        while(user.size() > 0 && dealer.size() > 0 && deck.getDeck().size() > 0)
        {

            booksResult updatedUser = hasBook(user);
            booksResult updatedDealer = hasBook(dealer);
            user = updatedUser.remaining;
            dealer = updatedDealer.remaining;

            userBooks = updatedUser.book;
            dealerBooks = updatedDealer.book;
            
            for(Card card : dealer)
            {
                System.out.print("|" + card.getRank() + " OF " + card.getSuit() + "|");
            }
            System.out.println("\t-----------------");
            userBooksSize += userBooks.size();
            dealerBooksSize += dealerBooks.size();
            totalBooks = userBooksSize + dealerBooksSize;
            display();
            //scan.nextLine();
        }
    }

    public booksResult hasBook(List<Card> hand)
    {
        Map<Card.Rank, Integer> dict = new HashMap<>();
        for(Card card : hand)
        {
                if(dict.containsKey(card.getRank()))
                {
                    dict.compute(card.getRank(),(key,val) 
                                    ->  val + 1);
                }
                else
                {
                    dict.put(card.getRank(),1);
                }
        }
        List<Card> dupe = new ArrayList<>();
        for(Card card : hand)
        {
            dupe.add(card);
        }
        Map<Card.Rank, List<Card>> myBooks = new HashMap<>();

        for(Card card : hand)
        {
            if(dict.containsKey(card.getRank()))
            {
                if(dict.get(card.getRank()) == 4)
                {
                    if(myBooks.containsKey(card.getRank()))
                    {
                        myBooks.get(card.getRank()).add(card);
                    }
                    else
                    {
                        List<Card> list = new ArrayList<>();
                        list.add(card);
                        myBooks.put(card.getRank(),list);
                    }
                    dupe.remove(card);
                }
            }
        }

        booksResult updatedHand = new booksResult();
        updatedHand.book = myBooks;
        updatedHand.remaining = dupe;
        return updatedHand;
    }

    //displays books and your hand, asks user what card to ask for
    public void display()
    {
        List<Card.Rank> options = new ArrayList<>();

        Map<Card.Rank, Integer> numbOfRanks = new HashMap<>();

        System.out.println("Dealer's Books:\t" + dealerBooks.keySet());
        System.out.println("Your Books:\t" + userBooks.keySet());
        System.out.println("\nYour Hand:");

        for(Card card : user)
        {
            if(!options.contains(card.getRank()))
            {
                options.add(card.getRank());
            }

            if(numbOfRanks.containsKey(card.getRank()))
            {
                numbOfRanks.compute(card.getRank(), (key,val) -> val+1);
            }
            else
            {
                numbOfRanks.put(card.getRank(),1);
            }
        }
        System.out.println();

        for(Card.Rank rank : numbOfRanks.keySet())
        {
            System.out.print("|" + numbOfRanks.get(rank) + "x" + rank + "|");
        }
        System.out.println("\n\nChoose a card to ask for:");
        

        for(int index = 0; index < options.size() ; index++)
        {
            System.out.print("|" + (index + 1) + ": " + options.get(index) + "|");
        }

        System.out.println();
        int input = scan.nextInt() - 1;
        Card.Rank inputRank = options.get(input);

        System.out.print("\033[H\033[2J");
        for(Card.Rank rank : numbOfRanks.keySet())
        {
            System.out.print("|" + numbOfRanks.get(rank) + "x" + rank + "|");
        }
        System.out.print("\nYou are asking for " + inputRank + "\'s");
        for(int count = 0; count <= 5; count++)
        {
            try 
            {
                Thread.sleep(500);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            System.out.print(".");
        }

        //List that contains the cards that the user asks for.
        List<Card> giveToUser = new ArrayList<>();
        

        for(int i = dealer.size() - 1; i >= 0; i--)
        {
            Card.Rank rank = dealer.get(i).getRank();
            if(rank == inputRank)
            {
                giveToUser.add(dealer.get(i));
                dealer.remove(i);
            }
        }
        //if there are no cards, user will go fish

        if(giveToUser.size() == 0)
        {
            System.out.println("\nGo Fish!");

            user.add(drawDisplay());

        }
        else
        {
            if(giveToUser.size() == 1)
            {
                System.out.println("You get " + giveToUser.size() + " " + inputRank);
            }
            else{
                System.out.println("You get " + giveToUser.size() + " " + inputRank + "s");
            }

            for(Card card : giveToUser)
            {
                user.add(card);
            }

            
        }

        try 
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        System.out.println();   

        
        int ranCard = ran.nextInt(0,dealer.size());

        System.out.print("Dealer asks for");
        for(int i = 0 ; i <= 2 ; i++)
        {
            System.out.print(".");
            if(i == 2)
                System.out.print(dealer.get(ranCard).getRank() + "\'s\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Card> giveToDealer = new ArrayList<>();
        for(int i = user.size() - 1; i >= 0; i--)
        {
            Card.Rank rank = user.get(i).getRank();
            if(rank == dealer.get(ranCard).getRank())
            {
                giveToDealer.add(user.get(i));
                dealer.add(user.get(i));
                user.remove(i);
            }
        }

        if(giveToDealer.size() == 0)
        {
            System.out.println("You give away no cards.");
            dealer.add(deck.draw());
        }

        else
            System.out.println("You give away " + giveToDealer.size() + "x" + dealer.get(ranCard).getRank() + "\n");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //return options.get(input);


    }



    public Card drawDisplay()
    {
        Card card = deck.draw();
        //System.out.print("\033[H\033[2J");
        System.out.print("You get");
        for(int a = 0; a <= 5; a++)
        {
            System.out.print(".");
            try{
                Thread.sleep(500);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            if(a == 5)
                System.out.println("1x" + card.getRank());
            
        }
        return card;
    }

    public static void main(String[] args) {
        new GoFish();
    }
}

class booksResult {
    Map<Card.Rank, List<Card>> book;
    List<Card> remaining;
}