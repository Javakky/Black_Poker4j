package com.github.javakky.blackpoker4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CardSheaf {
    /**
     * 渡されたカードオブジェクトがデータ構造でどの位置にあるかを返す。
     * データ構造はリストか配列であることをお願いする。
     * @param card 番号を知りたいカードのオブジェクト
     * @return 引数オブジェクトの構造内での番号
     */
    int indexOf(Card card);

    /**
     * カードの枚数を返す。
     * @return カードの枚数
     */
    int cardSize();

    /**
     * データ構造にカードを追加する。
     */
    void addCard(Card card);

    /**
     * データ構造からカードを削除する。
     */
    void removeCard(Card card);

    /**
     * データ構造からカードを削除する。
     */
    void removeCard(int index);

    /**
     * 数字とマークが一致するカードを返す。
     */
    Card find(int number, Card.Mark mark);

    /**
     * 数字とマークが一致するカードの番号を返す。
     */
    int indexOf(int number, Card.Mark mark);

    /**
     * 指定された番号にあるカードを返す。
     * 要素の削除はおこなわない。
     * @param index 欲しいカードの番号
     */
    Card getCard(int index);

    /**
     * 指定された番号にあるカードを取り出す。
     * カードはデータ構造から削除される。
     * @param index 欲しいカードの番号
     */
    Card takeCard(int index);

    static int indexOf(List<Card> list, Card card){
        int i;
        for (i = 0; i < list.size(); i++) {
            Card c = list.get(i);
            if(c == null) continue;
            if(c.equals(card)) break;
        }
        if(i == list.size()) return -1;
        return i;
    }

    static List<Card> addCard(List<Card> list, Card card){
        list.add(card);
        return list;
    }

    static List<Card> removeCard(List<Card> list, Card card){
        list.remove(card);
        return list;
    }

    static List<Card> removeCard(List<Card> list, int index){
        list.remove(index);
        return list;
    }

    static Card find(List<Card> list, int number, Card.Mark mark){
        for (Card card: list){
            if(card == null) continue;
            if(card.getMark() == mark && card.getNumber() == number) return card;
        }
        return null;
    }

    static int indexOf(List<Card> list, int number, Card.Mark mark){
        int i;
        for (i = 0; i < list.size(); i++){
            Card card = list.get(i);
            if(card == null) continue;
            if(card.getMark() == mark && card.getNumber() == number) break;
        }
        if(i == list.size()) return -1;
        return i;
    }

    static Card getCard(List<Card> list, int index){
        return list.get(index);
    }

    static Map.Entry<List<Card>, Card> takeCard(List<Card> list, int index){
        Card card = list.get(index);
        list.remove(card);
        return new HashMap.SimpleEntry<List<Card>, Card>(list, card);
    }
}

