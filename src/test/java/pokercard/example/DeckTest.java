package pokercard.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DeckTest {

    public Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    @DisplayName("testCardSetStraightType")
    void testCardSetStraightType() {
        ArrayList<Card> player1Cards = new  ArrayList<Card>();
        player1Cards.add(new Card(Card.CardSymbol.A, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.FOUR, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.FIVE, Card.CardShape.CLOVERS));

        ArrayList<Card> player2Cards = new  ArrayList<Card>();
        player2Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.FOUR, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.FIVE, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.SIX, Card.CardShape.CLOVERS));

        ArrayList<Card> player3Cards = new  ArrayList<Card>();
        player3Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player3Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player3Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player3Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player3Cards.add(new Card(Card.CardSymbol.SIX, Card.CardShape.CLOVERS));

        CardRules.MatchingTypes cardSetStraightType = CardRules.findCardSetType(player1Cards);
        assertEquals(CardRules.MatchingTypes.STRAIGHT, cardSetStraightType);

        CardRules.MatchingTypes cardSetInvalidType = CardRules.findCardSetType(player3Cards);
        assertEquals(CardRules.MatchingTypes.NOT_VALID, cardSetInvalidType);

        int winnerIdx = this.deck.cardRules.compareTwoCardSets(player1Cards, player2Cards);
        assertEquals(1, winnerIdx);
    }


    @Test
    @DisplayName("testCardSetTwinJokerType")
    void testCardSetTwinJokerType() {
        ArrayList<Card> player1Cards = new  ArrayList<Card>();
        player1Cards.add(new Card(Card.CardSymbol.BLACK_JOKER, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.RED_JOKER, Card.CardShape.CLOVERS));

        ArrayList<Card> player2Cards = new  ArrayList<Card>();
        player2Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.FOUR, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.FIVE, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.SIX, Card.CardShape.CLOVERS));

        ArrayList<Card> player3Cards = new  ArrayList<Card>();
        player3Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player3Cards.add(new Card(Card.CardSymbol.BLACK_JOKER, Card.CardShape.CLOVERS));

        CardRules.MatchingTypes cardSetTwinJokerType = CardRules.findCardSetType(player1Cards);
        assertEquals(CardRules.MatchingTypes.TWIN_JOKER, cardSetTwinJokerType);

        CardRules.MatchingTypes cardSetInvalidType = CardRules.findCardSetType(player3Cards);
        assertEquals(CardRules.MatchingTypes.NOT_VALID, cardSetInvalidType);

        int winnerIdx = this.deck.cardRules.compareTwoCardSets(player1Cards, player2Cards);
        assertEquals(0, winnerIdx);
    }

    @Test
    @DisplayName("testCardSetFourOfAKindType")
    void testCardSetFourOfAKindType() {
        ArrayList<Card> player1Cards = new  ArrayList<Card>();
        player1Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));
        player1Cards.add(new Card(Card.CardSymbol.TWO, Card.CardShape.CLOVERS));

        ArrayList<Card> player2Cards = new  ArrayList<Card>();
        player2Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));

        ArrayList<Card> player3Cards = new  ArrayList<Card>();
        player3Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player3Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        player3Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));

        CardRules.MatchingTypes cardSetFourOfAKindType = CardRules.findCardSetType(player1Cards);
        assertEquals(CardRules.MatchingTypes.FOUR_OF_A_KIND, cardSetFourOfAKindType);

        CardRules.MatchingTypes cardSetThreeOfAKindType = CardRules.findCardSetType(player3Cards);
        assertEquals(CardRules.MatchingTypes.THREE_OF_A_KIND, cardSetThreeOfAKindType);

        int winnerIdx = this.deck.cardRules.compareTwoCardSets(player1Cards, player2Cards);
        assertEquals(1, winnerIdx);
    }

    @Test
    @DisplayName("testSillyRobot")
    void testSillyRobot() {
        ArrayList<Card> player1Cards = new  ArrayList<Card>();
        player1Cards.add(new Card(Card.CardSymbol.THREE, Card.CardShape.CLOVERS));
        CardRules.MatchingTypes cardSetType= CardRules.findCardSetType(player1Cards);
        this.deck.sortCards(this.deck.cardRules.player1Cards);
        this.deck.sortCards(this.deck.cardRules.player2Cards);
        ArrayList<Integer> counterBidCardIdx = this.deck.cardRules.bidCardsBySillyRobot(player1Cards, cardSetType);
        assertFalse(counterBidCardIdx.isEmpty());

        ArrayList<Card> player2Cards = new  ArrayList<Card>();
        player2Cards.add(new Card(Card.CardSymbol.A, Card.CardShape.CLOVERS));
        player2Cards.add(new Card(Card.CardSymbol.A, Card.CardShape.TITLES));
        CardRules.MatchingTypes cardSet2Type= CardRules.findCardSetType(player2Cards);
        ArrayList<Integer> counterBidCardPairIdx = this.deck.cardRules.bidCardsBySillyRobot(player2Cards, cardSet2Type);
        assertFalse(counterBidCardPairIdx.isEmpty());

    }

    @Test
    @DisplayName("testTwoSillyRobotsPlayingAgainstEachOther")
    void testTwoSillyRobotsPlayingAgainstEachOther() {
        this.deck.sortCards(this.deck.cardRules.player1Cards);
        this.deck.sortCards(this.deck.cardRules.player2Cards);
        ArrayList<Integer> bidCardIdx = new ArrayList<Integer>(10);
        ArrayList<Integer> responseBidCardIdx = new ArrayList<Integer>(10);
        ArrayList<Card> bidCards = new ArrayList<Card>(10);
        ArrayList<Card> counterBidCards = new ArrayList<Card>(10);
        while ( !this.deck.cardRules.player1Cards.isEmpty() && !this.deck.cardRules.player2Cards.isEmpty() ){
            if (this.deck.cardRules.thisRoundMatchingType == CardRules.MatchingTypes.NULL) {
                bidCardIdx.add(0);
                bidCards = this.deck.cardRules.bidCards(bidCardIdx);
            }
            CardRules.MatchingTypes cardSetType = CardRules.findCardSetType(bidCards);
            ArrayList<Integer> counterBidCardPairIdx = this.deck.cardRules.bidCardsBySillyRobot(bidCards, cardSetType);
            counterBidCards = this.deck.cardRules.bidCards(counterBidCardPairIdx);
            this.deck.cardRules.compareTwoCardSets(bidCards, counterBidCards);

            System.out.println(bidCards);
            System.out.println(counterBidCards);
            System.out.println("=========");

            bidCardIdx.clear();
            bidCards = (ArrayList<Card>)counterBidCards.clone();

        }
        return;
    }

}
