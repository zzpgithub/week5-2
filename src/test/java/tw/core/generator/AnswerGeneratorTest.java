package tw.core.generator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tw.core.exception.OutOfRangeAnswerException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * 在AnswerGeneratorTest文件中完成AnswerGenerator中对应的单元测试
 */
@RunWith(MockitoJUnitRunner.class)
public class AnswerGeneratorTest {

    @Mock
    private AnswerGenerator answerGenerator;

    @Mock
    private RandomIntGenerator randomIntGenerator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp()  throws OutOfRangeAnswerException {
        answerGenerator = new AnswerGenerator(randomIntGenerator);
    }

    @Test
    public void should_throw_Exception_when_input_numbers_repeat() throws OutOfRangeAnswerException {
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("1 2 3 3");
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answerGenerator.generate();
    }

    @Test
    public void should_throw_Exception_when_input_numbers_more_than_10() throws OutOfRangeAnswerException {
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("10 2 3 3");
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answerGenerator.generate();
    }
    @Test
    public void should_return_generate_numbers_when_format_is_correct() throws OutOfRangeAnswerException {
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("1 2 3 4");
        assertThat(answerGenerator.generate().toString()).isEqualTo("1 2 3 4");
    }

}

