package com.github.javakky.blackpoker4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck implements CardSheaf {

    private Stack<Card> deck = new Stack<>();


    /**
     * リスト内のカードでデッキを初期化する。
     * @param deck 初めにデッキ内に置きたいカードすべてのリスト
     */
    public Deck(List<Card> deck) {
        this.deck.addAll(deck);
    }

    /**
     * 配列内のカードでデッキを初期化する。
     * @param deck 初めにデッキ内に置きたいカードすべての配列
     */
    public Deck(Card... deck) {
        this.deck.addAll(Arrays.asList(deck));
    }

    @Override
    public int indexOf(Card card) {
        return CardSheaf.indexOf(deck, card);
    }

    //CardSheafクラスではcardSizeの標準実装を提供していないため、実装した。
    //要素数を取り出すだけ。
    @Override
    public int cardSize() {
        return deck.size();
    }

    @Override
    public void addCard(Card card) {
        CardSheaf.addCard(deck, card);
    }

    @Override
    public void removeCard(Card card) {
        CardSheaf.removeCard(deck, card);
    }

    @Override
    public void removeCard(int index) {
        CardSheaf.removeCard(deck, index);
    }

    @Override
    public Card find(int number, Card.Mark mark) {
        return CardSheaf.find(deck, number, mark);
    }

    @Override
    public int indexOf(int number, Card.Mark mark) {
        return CardSheaf.indexOf(deck, number, mark);
    }

    @Override
    public Card getCard(int index) {
        return CardSheaf.getCard(deck, index);
    }

    @Override
    public Card takeCard(int index) {
        return CardSheaf.takeCard(deck, index).getValue();
    }

    /**
     * デッキの一番上のカードを取り出す。
     */
    public Card takeTop() {
        return deck.pop();
    }

    /**
     * ゲーム開始時の先攻判定に用いる。
     * @return デッキの一番上のカード
     */
    public Card firstStep() {
        return this.takeTop();
    }

    /**
     * カードをドローする。
     * @return デッキの一番上のカード
     */
    public Card draw() {
        return this.takeTop();
    }

    /**
     * ダメージを受けたときに呼ばれる。
     * @return デッキの一番上のカード
     */
    public Card damage() {
        return this.takeTop();
    }

    /**
     * デッキをシャッフルする。
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * デッキ内にカードが存在するかどうかを判定する。
     * @return デッキ内にカードがあるならtrue、ないならfalse
     */
    public boolean hasCard() {
        return cardSize() > 0;
    }
}
