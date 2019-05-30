package com.github.javakky.blackpoker4j;

import java.util.ArrayList;
import java.util.List;

public class Hand implements CardSheaf {

    private List<Card> hand = new ArrayList<>();

    @Override
    public int indexOf(Card card) {
        return CardSheaf.indexOf(hand, card);
    }

    @Override
    public int cardSize() {
        return hand.size();
    }

    @Override
    public void addCard(Card card) {
        CardSheaf.addCard(hand, card);
    }

    @Override
    public void removeCard(Card card) {
        CardSheaf.removeCard(hand, card);
    }

    @Override
    public void removeCard(int index) {
        CardSheaf.removeCard(hand, index);
    }

    @Override
    public Card find(int number, Card.Mark mark) {
        return CardSheaf.find(hand, number, mark);
    }

    @Override
    public int indexOf(int number, Card.Mark mark) {
        return CardSheaf.indexOf(hand, number, mark);
    }

    @Override
    public Card getCard(int index) {
        return CardSheaf.getCard(hand, index);
    }

    @Override
    public Card takeCard(int index) {
        return CardSheaf.takeCard(hand, index).getValue();
    }

    /**
     * 指定された手札のカードの職業を返す.
     */
    public Card.Job getJob(int handIndex) {
        return this.hand.get(handIndex).getJob();
    }
}
