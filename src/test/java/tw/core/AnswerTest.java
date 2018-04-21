package tw.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.model.Record;

import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * 在AnswerTest文件中完成Answer中对应的单元测试
 */
public class AnswerTest {
    //private Answer answer;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void should_return_answer_when_input_correct_format_numbers() {
        Answer rightAnswer = new Answer();
        rightAnswer.setNumList(Arrays.asList("1","2","3","4"));
        assertThat(rightAnswer.createAnswer("1 2 3 4").toString()).isEqualTo("1 2 3 4");
    }

    @Test
    public void should_throw_Exception_when_input_numbers_repeat() throws OutOfRangeAnswerException {
        Answer answer = new Answer();
        answer.setNumList(Arrays.asList("1", "1", "2","3"));
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answer.validate();
    }

    @Test
    public void should_throw_Exception_when_input_numbers_more_than_10() throws OutOfRangeAnswerException {
        Answer answer = new Answer();
        answer.setNumList(Arrays.asList("10", "1", "2","3"));
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answer.validate();
    }

    @Test
    public void should_return_all_wrong() throws Exception {
        int[] result = new int[]{0,0};
        Answer actualAnswer=Answer.createAnswer("1 2 3 4");
        Answer answer=Answer.createAnswer("5 6 7 8");
        Record record=actualAnswer.check(answer);
        assertThat(record.getValue()).isEqualTo(result);
    }

    @Test
    public void should_return_all_correct() throws Exception {
        int[] result = new int[]{4,0};
        Answer actualAnswer=Answer.createAnswer("1 2 3 4");
        Answer answer=Answer.createAnswer("1 2 3 4");
        Record record=actualAnswer.check(answer);
        assertThat(record.getValue()).isEqualTo(result);
    }

    @Test
    public void should_return_4_correct_2_and_3_wrong_position() throws Exception {
        int[] result = new int[]{1,2};
        Answer actualAnswer=Answer.createAnswer("1 2 3 4");
        Answer answer=Answer.createAnswer("0 3 2 4");
        Record record=actualAnswer.check(answer);
        assertThat(record.getValue()).isEqualTo(result);
    }
}