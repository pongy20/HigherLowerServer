package de.coerdevelopment.higherlower.api;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonDeserialize
public class RankingEntry implements Serializable {

    public String name;
    public int score;
    public long time;

    public RankingEntry() {
    }

    public RankingEntry(String name, int score, long time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }
}
