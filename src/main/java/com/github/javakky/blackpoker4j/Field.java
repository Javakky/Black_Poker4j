package com.github.javakky.blackpoker4j;

import java.util.ArrayList;
import java.util.List;

public class Field implements CardSheaf {

    private List<Card> front = new ArrayList<>();
    private List<Card> back = new ArrayList<>();
    private List<Card> fields = new ArrayList<>();

    @Override
    public int indexOf(Card card) {
        if (card.isFront()) {
            return CardSheaf.indexOf(front, card);
        }
        return CardSheaf.indexOf(back, card);
    }

    @Override
    public int cardSize() {
        return front.size() + back.size();
    }

    /**
     * カードの枚数を返す。
     * @param isFront 表側のカードについてかどうか
     * @return カードの枚数
     */
    public int cardSize(boolean isFront) {
        return isFront ? front.size() : back.size();
    }

    @Override
    public void addCard(Card card) {
        CardSheaf.addCard(fields, card);
        if (card.isFront()) {
            CardSheaf.addCard(front, card);
            return;
        }
        CardSheaf.addCard(back, card);
    }

    /**
     * データ構造にカードを追加する。
     * @param isFront 削除したいカードが表側かどうか。
     */
    public void addCard(Card card, boolean isFront) {
        CardSheaf.addCard(fields, card);
        if (isFront) {
            CardSheaf.addCard(front, card);
            return;
        }
        CardSheaf.addCard(back, card);
    }

    @Override
    public void removeCard(Card card) {
        CardSheaf.addCard(fields, card);
        if (card.isFront()) {
            CardSheaf.removeCard(front, card);
            return;
        }
        CardSheaf.removeCard(back, card);
    }

    @Override
    public void removeCard(int index) {
        Card rm = CardSheaf.getCard(fields, index);
        CardSheaf.removeCard(fields, rm);
        if (rm.isFront()) {
            CardSheaf.removeCard(front, rm);
            return;
        }
        CardSheaf.removeCard(back, rm);
    }

    /**
     * データ構造からカードを削除する。
     * @param isFront 削除したいカードが表側かどうか。
     */
    public void removeCard(int index, boolean isFront) {
        if (isFront) {
            CardSheaf.removeCard(front, index);
            return;
        }
        CardSheaf.removeCard(back, index);
    }

    @Override
    public Card find(int number, Card.Mark mark) {
        Card tmp = CardSheaf.find(front, number, mark);
        return tmp != null ? tmp : CardSheaf.find(back, number, mark);
    }

    @Override
    public int indexOf(int number, Card.Mark mark) {
        int tmp = CardSheaf.indexOf(front, number, mark);
        return tmp >= 0 ? tmp : CardSheaf.indexOf(back, number, mark);
    }

    @Override
    public Card getCard(int index) {
        return CardSheaf.getCard(fields, index);
    }

    public Card getCard(int index, boolean isFront) {
        if (isFront) {
            return CardSheaf.getCard(front, index);
        }
        return CardSheaf.getCard(back, index);
    }

    @Override
    public Card takeCard(int index) {
        return CardSheaf.takeCard(fields, index).getValue();
    }

    /**
     * 指定された番号にあるカードを取り出す。
     * カードはデータ構造から削除される。
     * @param index 欲しいカードのインデックス
     * @param isFront カードが表(防壁でない)かどうか。表ならtrue
     */
    public Card takeCard(int index, boolean isFront) {
        if (isFront) {
            return CardSheaf.takeCard(front, index).getValue();
        }
        return CardSheaf.takeCard(back, index).getValue();
    }

    /**
     * 防壁をセットする。
     */
    public void setBarrier(Card card) {
        if (card.isFront()) card.setFront(false);
        if (card.isCharge()) card.setCharge(false);
        if (card.canAttack()) card.setFront(false);
        addCard(card, false);
    }

    /**
     * カードを表向きで出す。(兵士など)
     */
    public void summon(Card card) {
        if (!card.isFront()) card.setFront(true);
        if (card.isCharge()) card.setCharge(false);
        addCard(card, true);
        switch (card.getJob()) {
            case ACE:
            case MAGICIAN:
                card.setCanAttack(true);
                break;
            default:
                card.setCanAttack(false);
                break;
        }
    }

    /**
     * 攻撃可能な(表側表示の)カードをリストで返す。
     */
    public List<Card> lookAttackable(){
        List<Card> attackable = new ArrayList<>();
        for (Card tmp: front){
            if(tmp.canAttack()) attackable.add(tmp);
        }
        return attackable;
    }

    /**
     * 指定された(表側表示の)カードを攻撃可能にする。
     * @param index 攻撃可能にしたいカードのインデックス。
     */
    public void recovery(int index){
        front.get(index).setCanAttack(true);
    }

    /**
     * すべての(表側表示の)カードを攻撃可能にする。
     */
    public void recoveryAll(){
        for(Card tmp: front){
            if(tmp != null) tmp.setCanAttack(true);
        }
    }

    /**
     * 指定されたカードをチャージする。
     * @param index チャージしたいカードのインデックス。
     * @param isFront カードが表側かどうか。
     */
    public void charge (int index, boolean isFront){
        if(isFront){
            front.get(index).setCharge(true);
        }else{
            back.get(index).setCharge(true);
        }
    }

    /**
     * 表側表示のカードをすべてチャージする。
     */
    public void chargeAllFront(){
        for(Card tmp: front){
            tmp.setCharge(true);
        }
    }

    /**
     * 裏側表示のカードをすべてチャージする。
     */
    public void chargeAllBack(){
        for(Card tmp: back){
            tmp.setCharge(true);
        }
    }

    /**
     * カードをすべてチャージする。
     */
    public void chargeAll(){
        chargeAllFront();
        chargeAllBack();
    }

    /**
     * 指定されたカードをドライブする。
     * @param index ドライブしたいカードのインデックス。
     * @param isFront カードが表側かどうか。
     */
    public void drive (int index, boolean isFront){
        if(isFront){
            front.get(index).setCharge(false);
        }else{
            back.get(index).setCharge(false);
        }
    }

    /**
     * 表側表示のカードをすべてドライブする。
     */
    public void driveAllFront(){
        for(Card tmp: front){
            tmp.setCharge(false);
        }
    }

    /**
     * 裏側表示のカードをすべてドライブする。
     */
    public void driveAllBack(){
        for(Card tmp: back){
            tmp.setCharge(false);
        }
    }

    /**
     * カードをすべてドライブする。
     */
    public void driveAll(){
        driveAllFront();
        driveAllBack();
    }

    /**
     * カードを破壊する。
     * @param index 破壊したいカードのインデックス。
     * @param isFront 破壊したいカードが表側かどうか。
     * @return 破壊されたカードのオブジェクト。
     */
    public Card destruction (int index, boolean isFront){
        return takeCard(index, isFront);
    }

    /**
     * 裏側のカードを表側にする。
     * @param index 表側にしたいカードのインデックス。
     */
    public void open(int index){
        Card tmp = takeCard(index, false);
        tmp.setFront(true);
        addCard(tmp, true);
    }

    /**
     * 指定されたカードがドライブ状態かどうか.
     */
    public boolean isDrive(int barrierIndex) {
        return !back.get(barrierIndex).isCharge();
    }

    /**
     * 選択された兵士が攻撃可能かを調べる.
     * @param index 調べたい兵士の順番(表向きのカードの中で)
     */
    public boolean canAttack(int index) {
        return front.get(index).canAttack();
    }

    /**
     * 選ばれた兵士のオブジェクトのコピーを返す.
     * @param index 調べたい兵士の順番(表向きのカードの中で)
     * @return 兵士カードのオブジェクトのコピー
     */
    public Card getSoldierData(int index) {
        return front.get(index).clone();
    }

    /**
     * 選ばれた兵士の番号を返す.
     * @param index 調べたい兵士の順番(同じ向きのカードの中で)
     * @param isFront カードは表か
     * @return カード番号
     */
    public int getNumber(int index, boolean isFront) {
        List<Card> list = isFront ? front: back;
        return list.get(index).getNumber();
    }

    /**
     * 選ばれた兵士の攻撃力を返す.
     * @param index 調べたい兵士の順番(表向きのカードの中で)
     */
    public int getAttack(int index) {
        return front.get(index).getAttack();
    }
}
