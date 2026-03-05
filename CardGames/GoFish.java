
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class GoFish
{
    private final Scanner scan;
    private List<Card> user, dealer;
    private Deck deck;
    private Map<Card.Rank,List<Card>> userBooks, dealerBooks;
    private final Random ran;
    public GoFish()
    {
        ran = new Random();
        scan = new Scanner(System.in);
        deck = new Deck();
        deck.shuffle();

        user = new ArrayList<>();
        dealer = new ArrayList<>();

        for(int initial = 0; initial < 7; initial++)
        {
            user.add(deck.draw());
            dealer.add(deck.draw());
            //System.out.println(deck.getSize());
        }


        userBooks = new HashMap<>();
        dealerBooks = new HashMap<>();

        while((user.size() > 0 && dealer.size() > 0) || deck.getSize() > 0)
        {
            //add updatedUser and updatedDealer to display(), rename display() to game()
            
            display();
            //scan.nextLine();
        }
        System.out.print("\033[H\033[2J");
        System.out.println("Dealer's Books:" + dealerBooks.keySet());
        System.out.println("User's Books:" + userBooks.keySet());

        
        if(userBooks.size() > dealerBooks.size())
            System.out.println("USER WINS!");
        else if(userBooks.size() < dealerBooks.size())
            System.out.println("DEALER WINS!");
        else
            System.out.println("STALEMATE");
    }

    public BooksResult hasBook(List<Card> hand)
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

        BooksResult updatedHand = new BooksResult();
        updatedHand.book = myBooks;
        updatedHand.remaining = dupe;
        return updatedHand;
    }

    //displays books and your hand, asks user what card to ask for
    public void display()
    {
        System.out.print("\033[H\033[2J");
        System.out.println("\n-----------------");

        List<Card.Rank> options = new ArrayList<>();
        List<Card.Rank> optionsDealer = new ArrayList<>();

        Map<Card.Rank, Integer> numbOfRanks = new HashMap<>();
        System.out.println("Dealer's Cards: " + dealer.size());
        System.out.println("User's Cards: " + user.size());
        System.out.println("\nDealer's Books:\t" + dealerBooks.keySet());
        System.out.println("Your Books:\t" + userBooks.keySet());
        System.out.println("Cards remaining in deck: " + deck.getSize());
        if(user.isEmpty() && !deck.getDeck().isEmpty())
        {
            if(deck.getSize() >=4)
            {
                System.out.println("You have no more cards in your hand. Drawing 4 cards from deck...");
                user.addAll(deck.drawAmount(4));
            }
            else
            {
                System.out.println("You have no more cards in your hand. Drawing " + deck.getSize() + " cards from deck...");
                user.addAll(deck.drawAmount(deck.getSize()));
            }

        }
        else if(user.isEmpty() && deck.getDeck().isEmpty())
        {
            return;
        }
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

        for(Card card : dealer)
        {
            if(!optionsDealer.contains(card.getRank()))
            {
                optionsDealer.add(card.getRank());
            }

        }
        System.out.println();

        for(int index = 0; index < options.size() ; index++)
        {
            System.out.print("|" + (index + 1) + ": " + options.get(index) + "(x" + numbOfRanks.get(options.get(index)) + ")|");
        }

        System.out.println();

        int input;
        Card.Rank inputRank;
        while (true) { 
            try 
            {
                input = scan.nextInt() - 1;
                inputRank = options.get(input);
                break;
            } catch (Exception e) 
            {
                System.out.println("Invalid Card");
            }
            
        }
        

        System.out.print("\033[H\033[2J");
        for(int index = 0; index < options.size() ; index++)
        {
            System.out.print("|" + (index + 1) + ": " + options.get(index) + "(x" + numbOfRanks.get(options.get(index)) + ")|");
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

        if(giveToUser.isEmpty())
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

        if(!dealer.isEmpty() && !deck.getDeck().isEmpty())
        {
            int ranCard = ran.nextInt(0,optionsDealer.size());

            System.out.print("Dealer asks for");
            for(int i = 0 ; i <= 2 ; i++)
            {
                System.out.print(".");
                if(i == 2)
                    System.out.print(optionsDealer.get(ranCard) + "\'s\n");
                
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
                if(rank == optionsDealer.get(ranCard))
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
                System.out.println("You give away " + giveToDealer.size() + "x" + optionsDealer.get(ranCard) + "\n");

            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        else if(dealer.isEmpty() && deck.getDeck().isEmpty())
        {
            return;
        }
        else
        {
            if(deck.getSize() >=4)
                dealer.addAll(deck.drawAmount(4));
            else
                dealer.addAll(deck.drawAmount(deck.getSize()));
        }
        BooksResult updatedUser = hasBook(user);
        BooksResult updatedDealer = hasBook(dealer);
        user = updatedUser.remaining;
        dealer = updatedDealer.remaining;
        userBooks.putAll(updatedUser.book);
        dealerBooks.putAll(updatedDealer.book);
    }



    public Card drawDisplay()
    {
        Card card = deck.draw();
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

class BooksResult {
    Map<Card.Rank, List<Card>> book;
    List<Card> remaining;
}