package tw.core;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.generator.RandomIntGenerator;

import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * 在RandomIntGeneratorTest文件中完成RandomIntGenerator中对应的单元测试
 */
public class RandomIntGeneratorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private RandomIntGenerator randomIntGenerator;

    @Before
    public final void before() {
        randomIntGenerator = new RandomIntGenerator();
    }

    @Test
    public void should_throw_exception_when_digitmax_less_than_numbersOfNeed(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't ask for more numbers than are available");
        randomIntGenerator.generateNums(3, 4);
    }

    @Test
    public void should_return_size_of_generate_numbers(){
        assertThat(randomIntGenerator.generateNums(6, 4).split(" ").length).isEqualTo(4);
    }

    @Test
    public void should_return_generate_numbers_when_format_is_correct() {
        String randomNumber=randomIntGenerator.generateNums(9, 4);
        assertThat(Arrays.stream(randomNumber.split(" ")).map(num -> parseInt(num))
                .distinct()
                .filter(num -> num < 10).count()).isEqualTo(4);
    }
}