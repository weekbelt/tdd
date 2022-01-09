package me.weekbelt.domain;

import java.util.HashMap;
import java.util.Map;

public class Profile {

    private final Map<String, Answer> answers = new HashMap<>();

    private int score;

    private final String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;

        boolean kill = false;
        boolean anyMatches = false;
        for (Criterion criterion : criteria) {
            // question에 대한 Answer를 호출
            Answer answer = answers.get(criterion.getAnswer().getQuestionText());
            // Profile의 Answer와 Criteria의 Answer이 match이거나 Don't Care인 경우 match = true
            boolean match = criterion.getWeight() == Weight.DontCare || answer.match(criterion.getAnswer());

            // Profile의 Answer와 Criteria의 Answer가 맞지 않고 기준이 무조건 맞아야 한다면 kill = true
            if (!match && criterion.getWeight() == Weight.MustMatch) {
                kill = true;
            }
            if (match) {
                score += criterion.getWeight().getValue();
            }
            anyMatches |= match;
        }
        if (kill) {
            return false;
        }
        return anyMatches;
    }

    public int score() {
        return score;
    }
}
