
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//only 2 players total
//start: deck is created and shuffled, each player is dealt 7 cards
// 1. user always starts, can choose between players to ask for cards.
//
//Agenda - Utilize TreeMaps, 
public class GoFish
{
    private Scanner scan;
    private List<Card> user, dealer;
    private Map<Card.Rank,List<Card>> userBooks, dealerBooks;
    public GoFish()
    {
        scan = new Scanner(System.in);
        Deck deck = new Deck();
        deck.shuffle();

        user = new ArrayList<>();
        dealer = new ArrayList<>();

        for(int initial = 0; initial < 7; initial++)
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

        while(totalBooks != 13)
        {
            Map<Map<Card.Rank,List<Card>>, List<Card>> updatedUser = hasBook(user);
            Map<Map<Card.Rank,List<Card>>, List<Card>> updatedDealer = hasBook(dealer);

            for(Map<Card.Rank,List<Card>> Books : updatedUser.keySet())
            {
                userBooks.putAll(Books);
                user = updatedUser.get(Books);
            }
            System.out.println("\t-----------------");

            for(Map<Card.Rank,List<Card>> Books : updatedDealer.keySet())
            {
                dealerBooks.putAll(Books);
                dealer = updatedDealer.get(Books);
            }
            userBooksSize += userBooks.size();
            dealerBooksSize += dealerBooks.size();
            totalBooks = userBooksSize + dealerBooksSize;
            display(user,userBooks,dealerBooks);
            //scan.nextLine();
            
        }


        

        
        
        /*for(Card.Rank rank : updatedCards.keySet())
        {
            for(Card card : updatedCards.get(rank))
            {
                System.out.println(card.getRank() + " OF " + card.getSuit());
            }
        }*/

    }

    public Map<Map<Card.Rank,List<Card>>, List<Card>> hasBook(List<Card> hand)
    {
        Map<Card.Rank, Integer> dict = new HashMap<>();
        for(Card card : hand)
        {
                //dict.merge(card.getRank(), 1, Integer::sum);
                if(dict.containsKey(card.getRank()))
                {
                    dict.compute(card.getRank(),(key,val) 
                                    ->  val + 1);
                    //System.out.println(dict.get(card.getRank()));
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

        //List<List<Card>> BooksCards = new ArrayList<>();
        //List<Card> books = new ArrayList<>();
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
                    //books.add(card);
                    dupe.remove(card);
                }
            }
        }

        //myBooks.put(Card.Rank.X,dupe);
        
        Map<Map<Card.Rank,List<Card>>, List<Card>> updatedCards = new HashMap<>();
        updatedCards.put(myBooks,dupe);
        return updatedCards;
    }

    //displays books and your hand, returns the type Rank that user wants to ask for
    public Card.Rank display(List<Card> user, Map<Card.Rank,List<Card>> userBooks, Map<Card.Rank,List<Card>> dealerBooks)
    {
        List<Card.Rank> options = new ArrayList<>();

        System.out.println("Dealer's Books:\t" + dealerBooks.keySet());
        System.out.println("Your Books:\t" + userBooks.keySet());
        System.out.println("\nYour Hand:");
        for(Card card : user)
        {
            System.out.print("|" + card.getRank() + " OF " + card.getSuit() + "|");
            if(!options.contains(card.getRank()))
            {
                options.add(card.getRank());
            }
        }
        //Card[] optionsArray = new Card[options.size()];
        System.out.println("\n\nChoose a card to ask for:");
        

        for(int index = 0; index < options.size() ; index++)
        {
            System.out.print("|" + (index + 1) + ": " + options.get(index) + "|");
        }
        System.out.println();
        int input = scan.nextInt() - 1;

        return options.get(input);


    }

    public List<Card> getUser()
    {
        return this.user;
    }

    public List<Card> getDealer()
    {
        return this.dealer;
    }

    public Map<Card.Rank,List<Card>> getUserBooks()
    {
        return this.userBooks;
    }

    public static void main(String[] args) {
        new GoFish();
    }
}