package pokercard.example.utils;

import pokercard.example.Card;

import java.util.Comparator;


public class SingleSymbolComparator implements Comparator<Card>{
    @Override
    public int compare(Card card1, Card card2) {
        // -1 (less) , 0  (equal), 1 (greater)
        return Integer.compare(card1.getSymbol().ordinal(), card2.getSymbol().ordinal());
    }
}
