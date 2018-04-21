package tw.core;

import org.junit.Before;
import org.junit.Test;
import tw.validator.InputValidator;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * 在InputValidatorTest文件中完成InputValidator中对应的单元测试
 */
public class InputValidatorTest {
    private InputValidator inputValidator;

    @Before
    public final void before() {
        inputValidator = new InputValidator();
    }

    @Test
    public void should_return_false_when_input_less_than_4_digits() {
        assertThat(inputValidator.validate("1 2 3")).isFalse();
    }

    @Test
    public void should_return_false_when_input_more_than_4_digits() {
        assertThat(inputValidator.validate("1 2 3 4 5")).isFalse();
    }


    @Test
    public void should_return_false_when_input_number_more_than_10() {
        assertThat(inputValidator.validate("10 2 3 4")).isFalse();
    }

    @Test
    public void should_return_true_when_input_correct_numbers() {
        assertThat(inputValidator.validate("1 2 3 4")).isTrue();
    }
}
