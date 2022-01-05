package me.weekbelt;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertThat(matches).isTrue();
    }
}