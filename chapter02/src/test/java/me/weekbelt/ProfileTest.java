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

public class ProfileTest {

    private Profile profile;
    private Criteria criteria;

    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Answer answerNoOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;

    @BeforeEach
    public void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    @BeforeEach
    public void createCriteria() {
        criteria = new Criteria();
    }

    @BeforeEach
    public void createQuestionAndAnswer() {
        Question questionIsThereRelocation = new BooleanQuestion("Relocation package?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNoRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);

        Question questionReimbursesTuition = new BooleanQuestion("Reimburses tuition?");
        answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);

        Question questionOnsiteDaycare = new BooleanQuestion("Onsite daycare?");
        answerHasOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.TRUE);
        answerNoOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.FALSE);
    }

    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // given
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        // given
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.DontCare));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertThat(matches).isTrue();
    }

    @Test
    public void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        // given
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertThat(matches).isTrue();
    }

    @Test
    public void matchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
        // given
        profile.add(answerThereIsNoRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    public void scoreIsZeroWhenThereAreNoMatches() {
        // given
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        // when
        profile.matches(criteria);

        // then
        assertThat(profile.score()).isEqualTo(0);
    }

    @Test
    public void scoreIsCriterionValueForSingleMatch() {
        // given
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        // when
        profile.matches(criteria);

        // then
        assertThat(profile.score()).isEqualTo(Weight.Important.getValue());
    }

    @Test
    public void scoreAccumulatesCriterionValuesForMatches() {
        // given
        profile.add(answerThereIsRelocation);
        profile.add(answerReimbursesTuition);
        profile.add(answerNoOnsiteDaycare);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.WouldPrefer));
        criteria.add(new Criterion(answerHasOnsiteDaycare, Weight.VeryImportant));

        // when
        profile.matches(criteria);

        // then
        int expectedScore = Weight.Important.getValue() + Weight.WouldPrefer.getValue();
        assertThat(profile.score()).isEqualTo(expectedScore);
    }

    // TODO: missing functionality--what if there is no matching profile answer for a criterion?
}