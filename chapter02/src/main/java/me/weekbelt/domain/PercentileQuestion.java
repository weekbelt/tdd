package me.weekbelt.domain;

import java.util.Arrays;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
@DiscriminatorValue(value = "percentile")
public class PercentileQuestion extends Question {

    private static final long serialVersionUID = 1L;

    @ElementCollection
    @CollectionTable(name = "AnswerChoice", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> answerChoices;

    public PercentileQuestion() {
    }

    public PercentileQuestion(String text, String[] answerChoices) {
        super(text);
        this.answerChoices = Arrays.asList(answerChoices);
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    @Override
    public boolean match(int expected, int actual) {
        return expected <= actual;
    }
}
