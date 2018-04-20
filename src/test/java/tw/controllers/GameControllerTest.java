package tw.controllers;

import org.junit.Before;
import org.junit.Test;
import tw.core.Game;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.core.generator.RandomIntGenerator;
import tw.views.GameView;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * 在GameControllerTest文件中完成GameController中对应的单元测试
 */
public class GameControllerTest {

    private GameController gameController;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp()  throws OutOfRangeAnswerException {
        gameController = new GameController(new Game(new AnswerGenerator(new RandomIntGenerator())), new GameView());
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
}