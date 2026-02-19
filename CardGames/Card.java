public class Card
{
    public enum Rank 
    {
        TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING,ACE
    }
    public enum Suit 
    
    {
        HEARTS,SPADES,CLUBS,DIAMONDS
    }


    private final Suit suit;
    private final Rank rank;
    private int value;

        public Card(Suit suit, Rank rank, int value)
        {
            this.suit = suit;
            this.rank = rank;
            this.value = value;
        }
            
        public Suit getSuit() 
        {    
            return this.suit;
        }
    
        public Rank getRank()
        
        {
            return this.rank;
        }

        public int getValue()
        {
            return this.value;
        }

        
        public void setValue(int a)
        {
            this.value = a;
        }
    
}