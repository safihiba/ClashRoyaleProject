package org.example;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Deck implements Writable {

    private String date;
    private String round;
    private int win;
    private String cards;
    private String cards2;
    private int clanTr;

    private int clanTr2;

    // Constructeur par défaut requis pour la désérialisation
    public Deck() {
    }

    // Constructeur pour initialiser les champs de la classe Deck
    public Deck(String date, String round, int win, String cards,String cards2, int clanTr,int clanTr2) {
        this.date = date;
        this.round = round;
        this.win = win;
        this.cards = cards;
        this.cards2 = cards2;
        this.clanTr = clanTr;
        this.clanTr2 = clanTr2;
    }

    // Méthode d'écriture dans le fichier de sortie SequenceFile
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(date);
        out.writeUTF(round);
        out.writeInt(win);
        out.writeUTF(cards);
        out.writeUTF(cards2);
        out.writeInt(clanTr);
        out.writeInt(clanTr2);
    }

    // Méthode de lecture depuis le fichier de sortie SequenceFile
    @Override
    public void readFields(DataInput in) throws IOException {
        date = in.readUTF();
        round = in.readUTF();
        win = in.readInt();
        cards = in.readUTF();
        cards2 = in.readUTF();
        clanTr = in.readInt();
        clanTr2 = in.readInt();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getCards2() {
        return cards2;
    }

    public void setCards2(String cards2) {
        this.cards2 = cards2;
    }

    public int getClanTr2() {
        return clanTr2;
    }

    public void setClanTr2(int clanTr2) {
        this.clanTr2 = clanTr2;
    }

    public int getClanTr() {
        return clanTr;
    }

    public void setClanTr(int clanTr) {
        this.clanTr = clanTr;
    }


}

