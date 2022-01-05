package me.weekbelt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProfileTest {

    @Test
    public void test() throws Exception {
        // given
        Profile profile = new Profile("Bull Hockey, Inc.");
        Question question = new BooleanQuestion(1, "Got bonuses?");
        Answer profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);

        Criteria criteria = new Criteria();
        Answer criteriaAnswer = new Answer(question, Bool.TRUE);
        Criterion criterion = new Criterion(criteriaAnswer, Weight.MustMatch);
        criteria.add(criterion);

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertThat(matches).isFalse();
    }

}