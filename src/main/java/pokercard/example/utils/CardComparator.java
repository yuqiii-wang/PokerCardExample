package pokercard.example.utils;

import pokercard.example.Card;
import pokercard.example.CardRules;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        return Integer.compare(card1.getSymbol().ordinal(), card2.getSymbol().ordinal());
    }
}
