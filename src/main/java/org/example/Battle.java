package org.example;

import org.apache.htrace.fasterxml.jackson.annotation.JsonCreator;
import org.apache.htrace.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Battle {
        private LocalDateTime date;
        private int round;
        private String cards;
        private String clan;
        private String player;
        private int level;
        private int clanTr;
        private double deck;

        @JsonCreator
        public Battle(@JsonProperty("date") LocalDateTime date,
                    @JsonProperty("round") int round,
                      @JsonProperty("cards") String cards,
                      @JsonProperty("clan") String clan,
                      @JsonProperty("player") String player,
                      @JsonProperty("level") int level,
                      @JsonProperty("clanTr") int clanTr,
                      @JsonProperty("deck") double deck) {
            this.date = date;
            this.round = round;
            this.cards = cards;
            this.clan = clan;
            this.player = player;
            this.level = level;
            this.clanTr = clanTr;
            this.deck = deck;
        }

        public LocalDateTime getDate() {
                return date;
        }

        public void setDate(LocalDateTime date) {
                this.date = date;
        }

        public int getRound() {
                return round;
        }

        public void setRound(int round) {
                this.round = round;
        }

        public String getCards() {
                return cards;
        }

        public void setCards(String cards) {
                this.cards = cards;
        }

        public String getClan() {
                return clan;
        }

        public void setClan(String clan) {
                this.clan = clan;
        }

        public String getPlayer() {
                return player;
        }

        public void setPlayer(String player) {
                this.player = player;
        }

        public int getLevel() {
                return level;
        }

        public void setLevel(int level) {
                this.level = level;
        }

        public int getClanTr() {
                return clanTr;
        }

        public void setClanTr(int clanTr) {
                this.clanTr = clanTr;
        }

        public double getDeck() {
                return deck;
        }

        public void setDeck(double deck) {
                this.deck = deck;
        }
}
