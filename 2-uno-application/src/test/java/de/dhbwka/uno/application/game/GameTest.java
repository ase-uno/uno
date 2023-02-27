package de.dhbwka.uno.application.game;

import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.application.persistence.InMemoryHighScoreRepository;
import de.dhbwka.uno.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void gameFinishesWhenOnePlayerHasNoCards() {
        Game game = new TestGameBuilder()
                .oneCard()
                .build();
        game.start();
        assertTrue(game.isGameFinished());
    }

    @Test
    void firstPlayerWins() {
        HighScoreStorageRepository highScoreStorageRepository = new InMemoryHighScoreRepository();
        Game game = new TestGameBuilder()
                .oneCard()
                .highScoreStorageRepository(highScoreStorageRepository)
                .build();
        game.start();

        HighScore highScore = highScoreStorageRepository.getHighScore();

        boolean player1HasWins = highScore.getElements().containsKey(new SimplePlayer("1"));
        boolean player2HasWins = highScore.getElements().containsKey(new SimplePlayer("2"));

        assertTrue(player1HasWins);
        assertFalse(player2HasWins);

        int winsForPlayer1 = highScore.getElements().get(new SimplePlayer("1"));

        assertEquals(1, winsForPlayer1);
    }

    @Test
    void firstUserGetsInputPrompt() {
        MockedPlayerConnection mockedPlayerConnection = new MockedPlayerConnection();
        Game game = new TestGameBuilder()
                .oneCard()
                .playerConnection1(mockedPlayerConnection)
                .build();

        game.start();

        assertEquals(1, mockedPlayerConnection.getInputCalled());
    }

    @Test
    void allUsersGetBroadcastMessages() {
        MockedPlayerConnection mockedPlayerConnection1 = new MockedPlayerConnection();
        MockedPlayerConnection mockedPlayerConnection2 = new MockedPlayerConnection();

        Game game = new TestGameBuilder()
                .oneCard()
                .playerConnection1(mockedPlayerConnection1)
                .playerConnection2(mockedPlayerConnection2)
                .build();

        game.start();

        assertEquals(1, mockedPlayerConnection1.getBroadcastActivePlayerCalled());
        assertEquals(1, mockedPlayerConnection1.getBroadcastWinnerCalled());

        assertEquals(1, mockedPlayerConnection2.getBroadcastActivePlayerCalled());
        assertEquals(1, mockedPlayerConnection2.getBroadcastWinnerCalled());
    }

    @Test
    void gameFinishesWhenNoCardsAreLeftOnStack() {

        CardStack cardStack = TestGameBuilder.generateCardStack(0);
        Game game = new TestGameBuilder()
                .noCards()
                .cardStack(cardStack)
                .build();
        game.start();

        assertTrue(game.isGameFinished());
        assertTrue(cardStack.isFinished());
    }

    @Test
    void noUserGetsAWinWhenNoCardsAreLeftOnStack() {

        HighScoreStorageRepository highScoreStorageRepository = new InMemoryHighScoreRepository();
        Game game = new TestGameBuilder()
                .noCards()
                .highScoreStorageRepository(highScoreStorageRepository)
                .build();
        game.start();

        HighScore highScore = highScoreStorageRepository.getHighScore();

        boolean player1HasWins = highScore.getElements().containsKey(new SimplePlayer("1"));
        boolean player2HasWins = highScore.getElements().containsKey(new SimplePlayer("2"));

        assertFalse(player1HasWins);
        assertFalse(player2HasWins);
    }

    @Test
    void userTwoGetsPromptedWhenUserOneHasNoCompatibleCards() {

        CardStack cardStack = new CardStack(List.of(new Card(CardColor.BLUE, new CardNumber(3))));
        MockedPlayerConnection playerConnection1 = new MockedPlayerConnection();
        MockedPlayerConnection playerConnection2 = new MockedPlayerConnection();

        Game game = new TestGameBuilder()
                .oneCard()
                .cardStack(TestGameBuilder.generateCardStack(2))
                .cardStackPlayer1(cardStack)
                .playerConnection1(playerConnection1)
                .playerConnection2(playerConnection2)
                .build();

        game.start();

        assertEquals(1, playerConnection1.getInputCalled());
        assertEquals(1, playerConnection2.getInputCalled());
    }

    @Test
    void userTwoWinsWhenUserOneHasNoCompatibleCards() {

        CardStack cardStack = new CardStack(List.of(new Card(CardColor.BLUE, new CardNumber(3))));
        HighScoreStorageRepository highScoreStorageRepository = new InMemoryHighScoreRepository();

        Game game = new TestGameBuilder()
                .oneCard()
                .cardStack(TestGameBuilder.generateCardStack(2))
                .cardStackPlayer1(cardStack)
                .highScoreStorageRepository(highScoreStorageRepository)
                .build();

        game.start();

        HighScore highScore = highScoreStorageRepository.getHighScore();
        boolean player1HasWins = highScore.getElements().containsKey(new SimplePlayer("1"));
        boolean player2HasWins = highScore.getElements().containsKey(new SimplePlayer("2"));

        assertFalse(player1HasWins);
        assertTrue(player2HasWins);

        int winsForPlayer2 = highScore.getElements().get(new SimplePlayer("2"));

        assertEquals(1, winsForPlayer2);
    }

}
