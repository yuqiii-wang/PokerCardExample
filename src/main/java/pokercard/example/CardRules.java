package pokercard.example;

import pokercard.example.utils.SingleSymbolComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CardRules {

    public ArrayList<Card> player1Cards;
    public ArrayList<Card> player2Cards;

    private int playerIdx; // 0 for player 1, 1 for player 2
    private int thisRoundWinnerPlayerIdx; // 0 for player 1, 1 for player 2

    public MatchingTypes thisRoundMatchingType = MatchingTypes.NULL;

    CardRules(ArrayList<Card> _player1Cards, ArrayList<Card> _player2Cards) {
        this.player1Cards = _player1Cards;
        this.player2Cards = _player2Cards;
    }

    public static enum MatchingTypes {NOT_VALID, NULL, SINGLE, PAIR, TWO_PAIRS, THREE_PAIRS, THREE_OF_A_KIND,
        FULL_HOUSE, STRAIGHT, STRAIGHT_FLUSH, FOUR_OF_A_KIND, ROYAL_FLUSH, TWIN_JOKER};

    public static boolean isSingleType(ArrayList<Card> cards) {
        return cards.size() == 1;
    }

    public static boolean isPairType(ArrayList<Card> cards) {
        if (cards.size() == 2)
            return cards.get(0).equals( cards.get(1) );
        return false;
    }

    public static boolean isTwoPairType(ArrayList<Card> cards) {
        if (cards.size() == 4)
            return cards.get(0).equals( cards.get(1) )
                && cards.get(2).equals( cards.get(3) )
                && cards.get(1).getSymbol().next() == cards.get(2).getSymbol();
        return false;
    }

    public static boolean isThreePairType(ArrayList<Card> cards) {
        if (cards.size() == 6)
            return cards.get(0).equals( cards.get(1) )
                && cards.get(2).equals( cards.get(3) )
                && cards.get(1).getSymbol().next() == cards.get(2).getSymbol()
                && cards.get(4).equals( cards.get(5) )
                && cards.get(3).equals( cards.get(4) )
                && cards.get(3).getSymbol().next() ==  cards.get(4).getSymbol();
        return false;
    }

    public static boolean isThreeOfAKindType(ArrayList<Card> cards) {
        if (cards.size() == 3)
            return cards.get(0).equals( cards.get(1) )
                && cards.get(1).equals( cards.get(2) );
        return false;
    }

    public static boolean isFourOfAKindType(ArrayList<Card> cards) {
        if (cards.size() == 4)
            return cards.get(0).equals( cards.get(1) )
                    && cards.get(1).equals( cards.get(2) )
                    && cards.get(2).equals( cards.get(3) );
        return false;
    }

    public static boolean isStraightType(ArrayList<Card> cards) {
        boolean isStraight = true;
        if (cards.size() > 4) {
            for (int i = 1; i < cards.size(); i++) {
                isStraight &= cards.get(i-1).getSymbol().next() == cards.get(i).getSymbol();
            }
            return isStraight;
        }
        return false;
    }

    public static boolean isTwinJokerType(ArrayList<Card> cards) {
        if (cards.size() == 2)
            return ( cards.get(0).getSymbol() == Card.CardSymbol.BLACK_JOKER
                    && cards.get(1).getSymbol() == Card.CardSymbol.RED_JOKER ) ||
                    ( cards.get(1).getSymbol() == Card.CardSymbol.BLACK_JOKER
                            && cards.get(0).getSymbol() == Card.CardSymbol.RED_JOKER );
        return false;
    }

    // return -1 if not valid
    public static MatchingTypes findCardSetType(ArrayList<Card> cards) {
        if (cards.isEmpty()) {
            return MatchingTypes.NULL;
        }
        else if (isSingleType(cards)) {
            return MatchingTypes.SINGLE;
        }
        else if (isPairType(cards)) {
            return MatchingTypes.PAIR;
        }
        else if (isTwoPairType(cards)) {
            return MatchingTypes.TWO_PAIRS;
        }
        else if (isThreePairType(cards)) {
            return MatchingTypes.THREE_PAIRS;
        }
        else if (isThreeOfAKindType(cards)) {
            return MatchingTypes.THREE_OF_A_KIND;
        }
        else if (isFourOfAKindType(cards)) {
            return MatchingTypes.FOUR_OF_A_KIND;
        }
        else if (isStraightType(cards)) {
            return MatchingTypes.STRAIGHT;
        }
        else if (isTwinJokerType(cards)) {
            return MatchingTypes.TWIN_JOKER;
        }
        return MatchingTypes.NOT_VALID;
    }


    // used on the first round of bidding cards, or the counterparty does not counter bidding cards
    public ArrayList<Card> bidCards(ArrayList<Integer> biddingCardIdx) {
        biddingCardIdx.sort(Comparator.reverseOrder());

        ArrayList<Card> playerCards;
        playerCards = this.playerIdx == 0 ? this.player1Cards : this.player2Cards;

        ArrayList<Card> thisRoundBiddingCards = new ArrayList<Card>();

        for (Integer idx : biddingCardIdx) {
            if (idx < playerCards.size()) {
                thisRoundBiddingCards.add(playerCards.get(idx));
            }
        }


        MatchingTypes thisRoundMatchingTypeTmp = findCardSetType(thisRoundBiddingCards);
        if (thisRoundMatchingTypeTmp == MatchingTypes.NOT_VALID) {
            System.out.println("Not valid");
            return new ArrayList<Card>();
        }

        this.thisRoundMatchingType = thisRoundMatchingTypeTmp;
        this.playerIdx = (this.playerIdx + 1) % 2; // switch player

        for (Integer idx : biddingCardIdx) {
            if (idx < playerCards.size()) {
                playerCards.remove((int)idx);
            }
        }

        return thisRoundBiddingCards;
    }



    // return thisRoundWinnerPlayerIdx as the winner
    public int compareTwoCardSets(ArrayList<Card> thisRoundBidCards, ArrayList<Card> counterBidCards) {
        int thisRoundPlayerIdx = this.playerIdx;
        MatchingTypes thisRoundMatchingType = CardRules.findCardSetType(thisRoundBidCards);
        MatchingTypes counterRoundMatchingType = CardRules.findCardSetType(counterBidCards);
        if (thisRoundMatchingType == MatchingTypes.TWIN_JOKER) {
            return thisRoundPlayerIdx;
        }
        else  if (counterRoundMatchingType == MatchingTypes.TWIN_JOKER) {
            return (thisRoundPlayerIdx + 1) % 2;
        }
        else if (thisRoundMatchingType == MatchingTypes.FOUR_OF_A_KIND &&
                counterRoundMatchingType == MatchingTypes.FOUR_OF_A_KIND ) {
            return thisRoundBidCards.get(0).getSymbol().ordinal() > counterBidCards.get(0).getSymbol().ordinal() ?
                    thisRoundPlayerIdx : (thisRoundPlayerIdx + 1) % 2;
        }
        else if (thisRoundMatchingType == MatchingTypes.FOUR_OF_A_KIND) {
            return thisRoundPlayerIdx;
        }
        else if (counterRoundMatchingType == MatchingTypes.FOUR_OF_A_KIND) {
            return (thisRoundPlayerIdx + 1) % 2;
        }
        else if (thisRoundMatchingType == MatchingTypes.STRAIGHT &&
                counterRoundMatchingType == MatchingTypes.STRAIGHT ) {
            return thisRoundBidCards.get(0).getSymbol().ordinal() > counterBidCards.get(0).getSymbol().ordinal() ?
                    thisRoundPlayerIdx : (thisRoundPlayerIdx + 1) % 2;
        }
        else if (thisRoundMatchingType == MatchingTypes.THREE_OF_A_KIND &&
                counterRoundMatchingType == MatchingTypes.THREE_OF_A_KIND ) {
            return thisRoundBidCards.get(0).getSymbol().ordinal() > counterBidCards.get(0).getSymbol().ordinal() ?
                    thisRoundPlayerIdx : (thisRoundPlayerIdx + 1) % 2;
        }
        else if ( (thisRoundMatchingType == MatchingTypes.PAIR &&
                counterRoundMatchingType == MatchingTypes.PAIR) ||
                (thisRoundMatchingType == MatchingTypes.TWO_PAIRS &&
                        counterRoundMatchingType == MatchingTypes.TWO_PAIRS) ||
                (thisRoundMatchingType == MatchingTypes.THREE_PAIRS &&
                        counterRoundMatchingType == MatchingTypes.THREE_PAIRS)) {
            return thisRoundBidCards.get(0).getSymbol().ordinal() > counterBidCards.get(0).getSymbol().ordinal() ?
                    thisRoundPlayerIdx : (thisRoundPlayerIdx + 1) % 2;
        }
        else if (thisRoundMatchingType == MatchingTypes.SINGLE &&
                counterRoundMatchingType == MatchingTypes.SINGLE ) {
            return thisRoundBidCards.get(0).getSymbol().ordinal() > counterBidCards.get(0).getSymbol().ordinal() ?
                    thisRoundPlayerIdx : (thisRoundPlayerIdx + 1) % 2;
        }
        return thisRoundPlayerIdx;
    }

    public ArrayList<Integer> bidCardsBySillyRobot(ArrayList<Card> thisRoundBidCards, MatchingTypes cardSetType) {

        ArrayList<Card> oppositePlayerRemainingCards =  this.playerIdx == 1 ? this.player1Cards : this.player2Cards;

        ArrayList<Integer> counterBiddingCardIdx = new ArrayList<Integer>(20);
        if (cardSetType == MatchingTypes.NULL && !oppositePlayerRemainingCards.isEmpty()) {
            counterBiddingCardIdx.add(0);
            return counterBiddingCardIdx;
        }

        Card startCard = thisRoundBidCards.get(0);
        for (int i = 0; i < oppositePlayerRemainingCards.size(); i++) {
            if (oppositePlayerRemainingCards.get(i).lessThanOrEquals(startCard)) {
                continue;
            }
            if (i + thisRoundBidCards.size() > oppositePlayerRemainingCards.size()-1) {
                break;
            }
            else if (cardSetType != MatchingTypes.TWIN_JOKER &&
                    cardSetType != MatchingTypes.FOUR_OF_A_KIND ) {
                // four of a kind case
                if (i+4 <= oppositePlayerRemainingCards.size()) {
                    ArrayList<Card> cardTestFOUR_OF_A_KIND = new ArrayList<Card>(oppositePlayerRemainingCards.subList(i, i + 4));
                    if (isFourOfAKindType(cardTestFOUR_OF_A_KIND) &&
                            cardTestFOUR_OF_A_KIND.get(0).greaterThan(oppositePlayerRemainingCards.get(i)) )
                    {
                        for (int j = i; j < i + 4; j++) {
                            counterBiddingCardIdx.add(j);
                        }
                        return counterBiddingCardIdx;
                    }
                }

                // other cases
                ArrayList<Card> bidCards = new ArrayList<Card>(oppositePlayerRemainingCards.subList(i, i + thisRoundBidCards.size()));
                if ( findCardSetType(bidCards) == cardSetType &&
                        (i == 0 || bidCards.get(0).greaterThan(oppositePlayerRemainingCards.get(i-1)) )) {
                    for (int j = i; j < i + thisRoundBidCards.size(); j++) {
                        counterBiddingCardIdx.add(j);
                    }
                    return counterBiddingCardIdx;
                }
            }
        }
        if (oppositePlayerRemainingCards.size() > 1) {
            ArrayList<Card> cardTestTWIN_JOKER = new ArrayList<Card>(oppositePlayerRemainingCards.subList(
                    oppositePlayerRemainingCards.size()-2, oppositePlayerRemainingCards.size()-1));
            if (isTwinJokerType(cardTestTWIN_JOKER)) {
                counterBiddingCardIdx.add(oppositePlayerRemainingCards.size()-2);
                counterBiddingCardIdx.add(oppositePlayerRemainingCards.size()-1);
            }
            return counterBiddingCardIdx;
        }
        return counterBiddingCardIdx;
    }
}
