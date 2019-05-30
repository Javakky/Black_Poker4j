package com.github.javakky.blackpoker4j;

import static com.github.javakky.blackpoker4j.Card.Job.*;

public class Card implements Cloneable {

    private int number;
    private int attack;
    private boolean canAttack;
    private Mark mark;
    private boolean isCharge;
    private boolean isFront;
    private Job job;

    public Card(int number, Mark mark) {
        this.setNumber(number);
        this.setAttack(this.getNumber());
        this.setCanAttack(false);
        this.setMark(mark);
        this.setCharge(true);
        this.setFront(false);
        this.setJob(null);
        if (this.getNumber() >= 2 && this.getNumber() <= 10) {
            this.setJob(SOLDIER);
        } else if (this.getNumber() >= 11 && this.getNumber() <= 13) {
            this.setJob(HERO);
        } else if (this.getNumber() == 1) {
            this.setJob(ACE);
            this.setCanAttack(true);
        } else if (this.getNumber() == 0) {
            this.setJob(MAGICIAN);
            this.setCanAttack(true);
        }
    }

    private Card(int number, int attack, boolean canAttack, Mark mark, boolean isCharge, boolean isFront, Job job) {
        this.setNumber(number);
        this.setAttack(attack);
        this.setCanAttack(canAttack);
        this.setMark(mark);
        this.setCharge(isCharge);
        this.setFront(isFront);
        this.setJob(job);
    }

    public int getNumber() {
        return number;
    }

    public int getAttack() {
        return attack;
    }

    public Mark getMark() {
        return mark;
    }

    public boolean isCharge() {
        return isCharge;
    }

    public boolean isFront() {
        return isFront;
    }

    public Job getJob() {
        return job;
    }

    private void setNumber(int number) {
        this.number = number;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    private void setMark(Mark mark) {
        this.mark = mark;
    }

    public void setCharge(boolean charge) {
        isCharge = charge;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    private void setJob(Job job) {
        this.job = job;
    }

    public boolean canAttack() {
        return this.canAttack;
    }

    @Override
    public Card clone(){
        return new Card(number, attack, canAttack, mark, isCharge, isFront, job);
    }

    public static boolean isNotSoldier(Job job){
        switch (job) {
            case ACE:
            case HERO:
            case MAGICIAN:
                return true;
        }
        return false;
    }

    enum Mark {
        SPADE, DIAMOND, HEART, CLOVER, JOKER;
    }

    enum Job {
        SOLDIER, HERO, ACE, MAGICIAN;
    }
}

