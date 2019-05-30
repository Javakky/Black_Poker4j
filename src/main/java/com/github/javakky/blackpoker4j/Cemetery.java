package com.github.javakky.blackpoker4j;

import java.util.Stack;

public class Cemetery implements CardSheaf {

    Stack<Card> cemetery = new Stack<>();


    @Override
    public int indexOf(Card card) {
        return CardSheaf.indexOf(cemetery, card);
    }

    @Override
    public int cardSize() {
        return cemetery.size();
    }

    @Override
    public void addCard(Card card) {
        CardSheaf.addCard(cemetery, card);
    }

    @Override
    public void removeCard(Card card) {
        CardSheaf.removeCard(cemetery, card);
    }

    @Override
    public void removeCard(int index) {
        CardSheaf.removeCard(cemetery, index);
    }

    @Override
    public Card find(int number, Card.Mark mark) {
        return CardSheaf.find(cemetery, number, mark);
    }

    @Override
    public int indexOf(int number, Card.Mark mark) {
        return CardSheaf.indexOf(cemetery, number, mark);
    }

    @Override
    public Card getCard(int index) {
        return CardSheaf.getCard(cemetery, index);
    }

    @Override
    public Card takeCard(int index) {
        return CardSheaf.takeCard(cemetery, index).getValue();
    }
}

