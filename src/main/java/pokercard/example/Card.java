package pokercard.example;

import java.util.NoSuchElementException;

public class Card {
    public static enum CardSymbol{A, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
        NINE, TEN, J, Q, K, BLACK_JOKER, RED_JOKER;

        public CardSymbol next() {
            if (ordinal() == values().length - 1)
                throw new NoSuchElementException();
            return values()[ordinal() + 1];
        }};
    public static enum CardShape{HEART, DIAMOND, CLOVERS, TITLES, NULL};

    private CardSymbol symbol;
    private CardShape shape;

    public CardSymbol getSymbol() { return symbol; }
    public CardShape getShape() { return shape; }


    public boolean equals (Card _card) {
        return this.symbol == _card.symbol;
    };
    public boolean lessThan (Card _card) {
        return this.symbol.ordinal() < _card.symbol.ordinal();
    };
    public boolean greaterThan (Card _card) {
        return this.symbol.ordinal() > _card.symbol.ordinal();
    };
    public boolean lessThanOrEquals (Card _card) {
        return this.symbol.ordinal() <= _card.symbol.ordinal();
    };

    public void setSymbol(CardSymbol _symbol) { this.symbol = _symbol; }
    public void setShape(CardShape _shape) { this.shape = _shape; }

    @Override
    public String toString(){
        return this.symbol + " " + this.shape;
    }


    Card(CardSymbol _symbol, CardShape _shape) {
        this.symbol = _symbol;
        this.shape = _shape;

    }
}
