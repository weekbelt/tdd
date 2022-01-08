package me.weekbelt.domain;

public class Criterion implements Scoreable {

    private final Answer answer;

    private final Weight weight;

    private int score;

    public Criterion(Answer answer, Weight weight) {
        this.answer = answer;
        this.weight = weight;
    }

    public Weight getWeight() {
        return weight;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return this.score;
    }
}
