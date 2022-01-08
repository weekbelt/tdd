package me.weekbelt;

import static org.assertj.core.api.Assertions.assertThat;

import me.weekbelt.domain.Answer;
import me.weekbelt.domain.Bool;
import me.weekbelt.domain.BooleanQuestion;
import me.weekbelt.domain.Criteria;
import me.weekbelt.domain.Criterion;
import me.weekbelt.domain.Profile;
import me.weekbelt.domain.Question;
import me.weekbelt.domain.Weight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileTest {

    private Profile profile;

    private BooleanQuestion question;

    private Criteria criteria;

    @BeforeEach
    public void create() {
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }

    @Test
    public void matches() {
        Profile profile = new Profile("Bull Hockey, Inc.");
        Question question = new BooleanQuestion(1, "Got bonuses?");

        // must-match 항목이 맞지 않으면 false
        profile.add(new Answer(question, Bool.FALSE));
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

        assertThat(profile.matches(criteria)).isFalse();

        // don't care 항목에 대해서는 true
        profile.add(new Answer(question, Bool.FALSE));
        criteria = new Criteria();
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        assertThat(profile.matches(criteria)).isTrue();
    }
}