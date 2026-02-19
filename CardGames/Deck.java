
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck
{
    private List<Card> cards;
        public Deck()
        {
            this.cards = new ArrayList<>();
            for(Card.Suit suit : Card.Suit.values())
            {
                for(Card.Rank rank : Card.Rank.values())
                {
                    int value = 0;
                    value = switch (rank) {
                        case TWO -> 2;
                        case THREE -> 3;
                        case FOUR -> 4;
                        case FIVE -> 5;
                        case SIX -> 6;
                        case SEVEN -> 7;
                        case EIGHT -> 8;
                        case NINE -> 9;
                        case TEN -> 10;
                        case JACK -> 10;
                        case QUEEN -> 10;
                        case KING -> 10;
                        default -> 11;
                    };
                    Card newCard = new Card(suit,rank,value);
                    this.cards.add(newCard);
                    
                }
            }
        }

        public void shuffle()
        {
            Collections.shuffle(this.cards);
        }

        public Card draw()
        {
            Card drawnCard = this.cards.get(0);
            this.cards.remove(0);
            return drawnCard;
        }

        public List<Card> getDeck()
        {
            return this.cards;
        }

        public int getSize()
        {
            return cards.size();
        }
    
}