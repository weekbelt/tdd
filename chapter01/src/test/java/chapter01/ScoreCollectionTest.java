package chapter01;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreCollectionTest {

    @Test
    public void answerArithmeticMeanOfTwoNumbers() {
        // given
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        // when
        int actualResult = collection.arithmeticMean();

        // then
        Assertions.assertThat(actualResult).isEqualTo(6);
    }

}