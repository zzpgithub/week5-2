package tw.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import tw.commands.InputCommand;
import tw.core.Answer;
import tw.core.Game;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.views.GameView;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * 在GameControllerTest文件中完成GameController中对应的单元测试
 */

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    private GameController gameController;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Mock
    private InputCommand inputCommand;

    @Mock
    private AnswerGenerator answerGenerator;

    @Before
    public void setUp()  throws OutOfRangeAnswerException {
        when(answerGenerator.generate()).thenReturn(Answer.createAnswer("1 2 3 4"));
        gameController = new GameController(new Game(answerGenerator), new GameView());
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    public void should_return_game_begin_message() throws Exception {
        gameController.beginGame();
        assertThat(systemOut()).isEqualTo("------Guess Number Game, You have 6 chances to guess!  ------");
    }

    @Test
    public void should_return_fail_when_6_inputs_were_wrong_numbers() throws Exception{
        when(inputCommand.input()).thenReturn(Answer.createAnswer("5 7 3 4"))
                .thenReturn(Answer.createAnswer("1 3 5 4"))
                .thenReturn(Answer.createAnswer("2 9 1 3"))
                .thenReturn(Answer.createAnswer("3 4 5 6"))
                .thenReturn(Answer.createAnswer("2 1 3 4"))
                .thenReturn(Answer.createAnswer("6 5 3 4"));

        gameController.play(inputCommand);
        //assertThat(systemOut()).isEqualTo("Guess Result:");
        assertThat(systemOut().contains(
                "[Guess Numbers: 5 7 3 4, Guess Result: 2A0B]\n" +
                "[Guess Numbers: 1 3 5 4, Guess Result: 2A1B]\n" +
                "[Guess Numbers: 2 9 1 3, Guess Result: 0A3B]\n" +
                "[Guess Numbers: 3 4 5 6, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 2 1 3 4, Guess Result: 2A2B]\n" +
                "[Guess Numbers: 6 5 3 4, Guess Result: 2A0B]\n" +
                "Game Status: fail")).isTrue();
        Mockito.verify(inputCommand, Mockito.times(6)).input();
    }

    @Test
    public void should_return_success_when_guess_correct_numbers() throws Exception{
        when(inputCommand.input()).thenReturn(Answer.createAnswer("5 7 3 4"))
                .thenReturn(Answer.createAnswer("1 2 3 4"));

        gameController.play(inputCommand);
        assertThat(systemOut().contains(
                        "[Guess Numbers: 5 7 3 4, Guess Result: 2A0B]\n" +
                        "[Guess Numbers: 1 2 3 4, Guess Result: 4A0B]\n" +
                        "Game Status: success")).isTrue();
        Mockito.verify(inputCommand, Mockito.times(2)).input();
    }
}