package tw.core;

import org.junit.Before;
import org.junit.Test;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.core.model.GuessResult;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 在GameTest文件中完成Game中对应的单元测试
 */


public class GameTest {
    private Game game;
    private AnswerGenerator answerGenerator;
    private Answer answer;

    @Before
    public void setUp() throws OutOfRangeAnswerException {
        Answer actualAnswer = new Answer();
        actualAnswer.setNumList(Arrays.asList("1","2","3","4"));
        answerGenerator = mock(AnswerGenerator.class);
        when(answerGenerator.generate()).thenReturn(actualAnswer);
        game = new Game(answerGenerator);
        answer = new Answer();
    }

    @Test
    public void should_return_4A0B_when_input_numbers_all_right() {
        answer.setNumList(Arrays.asList("1","2","3","4"));
        assertThat(game.guess(answer).getResult()).isEqualTo("4A0B");
    }

    @Test
    public void should_return_guess_history() throws Exception {
        answer.setNumList(Arrays.asList("1","2","3","5"));
        game.guess(answer);
        answer.setNumList(Arrays.asList("1","2","3","4"));
        game.guess(answer);
        List<GuessResult> list = game.guessHistory();
        assertThat(list.size() == 2).isTrue();
        assertThat(list.get(0).getResult()).isEqualTo("3A0B");
        assertThat(list.get(1).getResult()).isEqualTo("4A0B");
    }

    @Test
    public void should_return_checkContinue_when_guess_incorrect() throws Exception {
        answer.setNumList(Arrays.asList("1","2","3","5"));
        game.guess(answer);
        assertThat(game.checkCoutinue()).isTrue();
    }

    @Test
    public void should_return_check_status_SUCCESS_when_guess_correct() {
        answer.setNumList(Arrays.asList("1","2","3","4"));
        game.guess(answer);
        assertThat(game.checkStatus()).isEqualTo("success");
    }

    @Test
    public void should_return_check_status_CONTINUE_when_guess_incorrect_within_6_times() {
        for(int i=0; i<5; i++) {
            answer.setNumList(Arrays.asList("1","2","3","5"));
            game.guess(answer);
            assertThat(game.checkStatus()).isEqualTo("continue");
        }
    }

    @Test
    public void should_return_check_status_FAIL_when_guess_incorrect_6_times() {
        for(int i=0; i<5; i++) {
            answer.setNumList(Arrays.asList("1","2","3","5"));
            game.guess(answer);
            assertThat(game.checkStatus()).isEqualTo("continue");
        }
        answer.setNumList(Arrays.asList("1","2","3","5"));
        game.guess(answer);
        assertThat(game.checkStatus()).isEqualTo("fail");
    }

}
