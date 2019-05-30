package com.github.javakky.blackpoker4j;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.github.javakky.blackpoker4j.Card.Job.*;

public class Player {

    private final Hand hand;
    private final Field field;
    private final Cemetery cemetery;
    private final Deck deck;
    private boolean canAttack;

    public Player(Card[] deck_list) {
        this.hand = new Hand();
        this.field = new Field();
        this.cemetery = new Cemetery();
        this.deck = new Deck(deck_list);
        this.setCanAttack(true);
    }

    public boolean canAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    /**
     * カードを1枚引く関数.
     */
    public void draw() {
        this.hand.addCard(this.deck.draw());
    }

    /**
     * デッキをシャッフルする関数.
     */
    public void shuffle() {
        this.deck.shuffle();
    }

    /**
     * カードを複数枚ドローして手札に加える.
     *
     * @param number ドローする枚数。
     */
    public void draws(int number) {
        for (int i = 0; i < number; i++) {
            this.shuffle();
        }
    }

    /**
     * ゲーム開始時に行う, カードをめくる処理.
     * デッキの一番上を一枚墓地に置く(先攻・後攻決め).
     *
     * @return 捲ったカードのスートと番号
     */
    public Pair<Integer, Card.Mark> first_step() {
        Card card = this.deck.first_step();
        this.cemetery.addCard(card);
        return new Pair<>(card.getNumber(), card.getMark());
    }

    /**
     * 自分のフィールドの兵士、防壁を全てチャージ状態にする.
     */
    public void charge() {
        this.field.chargeAll();
        this.field.recoveryAll();
    }

    /**
     * 手札を捨てなければならないかを調べる.
     */
    public boolean mustCleanUp() {
        return this.hand.cardSize() > 7;
    }

    /**
     * 手札を選んで墓地に置く.
     * {@link #mustCleanUp()}がtrueでない場合, falseを返す。
     *
     * @param index 捨てる手札の番号
     * @return 実行でき(カードを捨てられ)たかどうか
     */
    public boolean clean_up(int index) {
        if (!mustCleanUp()) return false;
        this.cemetery.addCard(this.hand.getCard(index));
        return true;
    }

    /**
     * ダメージを1点受ける(デッキから1枚カードを墓地へ置く).
     */
    public void damage() {
        this.cemetery.addCard(this.deck.damage());
    }

    /**
     * ダメージを点数分受ける(デッキから指定された枚数カードを墓地へ置く).
     *
     * @param number 受けるダメージの点数.
     */
    public void damages(int number) {
        for (int i = 0; i < number; i++)
            this.damage();
    }

    /**
     * 防壁の召喚.
     * ダメージを1点受け, 手札から選択したカードを1枚セットする.
     *
     * @param index セットするカードの座標.
     */
    public void setBarrier(int index) {
        this.damage();
        this.field.setBarrier(this.hand.getCard(index));
    }

    /**
     * 兵士の召喚.
     * 防壁を1枚ドライブし, 1点のダメージを受け, 手札からカードを1枚表向きで出す.
     * 出せるカードは2～10まで.
     * 選んだカードが条件を満たしていない・選んだ防壁がドライブ状態ならfalseを返す.
     *
     * @param handIndex    出したいカードが手札の何番目にあるか
     * @param barrierIndex ドライブする防壁が何番目にあるか
     * @return 召喚に成功したか
     */
    public boolean summonSoldier(int handIndex, int barrierIndex) {
        if (this.field.isDrive(barrierIndex) && this.hand.getJob(handIndex) != SOLDIER) {
            return false;
        }
        this.damage();
        this.field.drive(barrierIndex, false);
        Card card = this.hand.getCard(handIndex);
        this.field.summon(card);
        return true;
    }

    /**
     * エースの召喚.
     * 1点のダメージを受け, 手札からカードを1枚表向きで出す.
     * 出せるカードは1.
     * 選んだカードが条件を満たしていないならfalseを返す.
     *
     * @param handIndex 出したいカードが手札の何番目にあるか
     * @return 召喚に成功したか
     */
    public boolean summonAce(int handIndex) {
        if (this.hand.getJob(handIndex) != ACE) {
            return false;
        }
        this.damage();
        Card card = this.hand.getCard(handIndex);
        this.field.summon(card);
        return true;
    }

    /**
     * 英雄の召喚.
     * 防壁を2枚ドライブし, 1点のダメージを受け, 手札からカードを1枚表向きで出す.
     * 出せるカードは11～13.
     * 選んだカードが条件を満たしていない・選んだ防壁がドライブ状態ならfalseを返す.
     *
     * @param handIndex     出したいカードが手札の何番目にあるか
     * @param barrierIndex1 ドライブする防壁が何番目にあるか(1枚目)
     * @param barrierIndex2 ドライブする防壁が何番目にあるか(2枚目)
     * @return 召喚に成功したか
     */
    public boolean summonHero(int handIndex, int barrierIndex1, int barrierIndex2) {
        if (this.field.isDrive(barrierIndex1) && this.field.isDrive(barrierIndex2) && this.hand.getJob(handIndex) != HERO) {
            return false;
        }
        this.damage();
        this.field.drive(barrierIndex1, false);
        this.field.drive(barrierIndex2, false);
        Card card = this.hand.getCard(handIndex);
        this.field.summon(card);
        return true;
    }

    /**
     * 魔術師の召喚.
     * 防壁を1枚ドライブし, 手札を1枚捨て, 手札からカードを1枚表向きで出す.
     * 出せるカードはJoker.
     * 選んだカードが条件を満たしていない・選んだ防壁がドライブ状態ならfalseを返す.
     *
     * @param handIndex     出したいカードが手札の何番目にあるか
     * @param barrierIndex  ドライブする防壁が何番目にあるか
     * @param costHandIndex 捨てる手札が何番目にあるか
     * @return 召喚に成功したか
     */
    public boolean summonMagician(int handIndex, int costHandIndex, int barrierIndex) {
        if (this.field.isDrive(barrierIndex) && this.hand.getJob(handIndex) != MAGICIAN) {
            return false;
        }
        this.field.drive(barrierIndex, false);
        if (handIndex > costHandIndex) {
            Card card = this.hand.getCard(handIndex);
            this.field.summon(card);
            this.cemetery.addCard(this.hand.getCard(costHandIndex));
        } else {
            this.cemetery.addCard(this.hand.getCard(costHandIndex));
            Card card = this.hand.getCard(handIndex);
            this.field.summon(card);
        }
        return true;
    }

    /**
     * 攻撃する兵士のカード情報を返す.
     * 選択された座標に存在するカードの内, 攻撃可能なカードのオブジェクト(コピー)を返す.
     * 返す兵士をカードはドライブする.
     *
     * @param soldierIndex 攻撃したい兵士の番号のリスト
     * @return 実際に攻撃可能だった兵士のカードリスト(コピー)
     */
    public Card[] declaration_attack(int[] soldierIndex) {
        List<Card> attackers = new ArrayList<>();
        for (int index : soldierIndex) {
            if (this.field.canAttack(index)) {
                attackers.add(this.field.getSoldierData(index));
                this.field.drive(index, false);
            }
        }
        return attackers.toArray(new Card[attackers.size()]);
    }

    /**
     * 世代交代.
     * 兵士以外の表向きのカードが破壊されたときに呼ばれる.
     * デッキから兵士以外がめくれるまで墓地へ送り, めくれたら手札に加える.
     */
    public void alternation() {
        Card card;
        while (true) {
            card = this.deck.takeTop();
            if(Card.isNotSoldier(card.getJob())) break;
            this.cemetery.addCard(card);
        }
        this.hand.addCard(card);
    }

    /**
     * フィールドのカードが破壊されるときに呼ばれる.
     * @param isFront そのカードが表向きかどうか.
     * @param index 破壊されるカードがその向きで何番目か
     */
    public void destruction(boolean isFront, int index) {
        Card card = this.field.destruction(index, isFront);
        if(Card.isNotSoldier(card.getJob())) {
            this.alternation();
        }
        this.cemetery.addCard(card);
    }

    /**
     * 兵士でブロック(複数可)
     * @param soldierIndex ブロックしたい兵士の順番のリスト
     * @return ブロックするときの合計パワー
     */
    public int declarationDefense(int[] soldierIndex) {
        int number = 0;
        for (int index: soldierIndex) {
            this.field.drive(index, true);
            number += this.field.getAttack(index);
        }
        return number;
    }

    /**
     * 防壁でブロック(複数可)
     * @param index ブロックしたい防壁の順番
     * @return ブロックする防壁の番号
     */
    public int declarationDefenseBarrier(int index) {
        this.field.drive(index, false);
        this.field.open(index);
        return this.field.getNumber(index, false);
    }

}
