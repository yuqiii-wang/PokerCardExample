package pokercard.example;

import pokercard.example.utils.SingleSymbolComparator;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    public ArrayList<Card> cardList;

    public CardRules cardRules;

    public Deck(){
        this.cardList = new ArrayList<Card>();
        this.prepareCards();
        this.shuffleCards();
        this.cardRules = new CardRules (new ArrayList<Card>(this.cardList.subList(0, (int)(this.cardList.size() / 2))),
                new ArrayList<Card>(this.cardList.subList((int)(this.cardList.size() / 2), this.cardList.size())));

    };

    public void prepareCards() {
        for (Card.CardSymbol symbol : Card.CardSymbol.values()) {
            for (Card.CardShape shape : Card.CardShape.values()) {
                if (shape != Card.CardShape.NULL && (
                        symbol == Card.CardSymbol.BLACK_JOKER ||
                        symbol == Card.CardSymbol.RED_JOKER)
                )
                    continue;
                else if (shape != Card.CardShape.NULL) {
                    this.cardList.add(new Card(symbol, shape));

                }
            }
        }
    };

    public void shuffleCards() {
        int numCards = this.cardList.size();
        for (int i = 0; i < numCards; i++) {
            int shuffleIdx = (int)(Math.random()*(numCards - i - 1));
            Collections.swap(this.cardList, shuffleIdx, numCards - i - 1);
        }
    }

    public ArrayList<Card> sortCards(ArrayList<Card> cards) {
        cards.sort(new SingleSymbolComparator());
        return cards;
    }

    public boolean compareTwoCardSetsPlayer1Wins(ArrayList<Card> CardListPlayer1, ArrayList<Card> CardListPlayer2) {
        return true;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
    }
}
