package de.dhbwka.uno.application.game;

import de.dhbwka.uno.application.model.PlayerWithConnection;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.application.persistence.InMemoryHighScoreRepository;
import de.dhbwka.uno.domain.*;

import java.util.List;

public class TestGameBuilder {

    public static CardStack generateCardStack(int numberOfCards) {
        List<Card> cards = new TestCardProvider(numberOfCards, new Card(CardColor.RED, new CardNumber(1)))
                .listAllCards();
        return new CardStack(cards);
    }


    private int playerCardStackSize;
    private int cardStackSize;
    private CardStack cardStackOverwrite;
    private CardStack player1CardStackOverwrite;

    private HighScoreStorageRepository highScoreStorageRepository;
    private PlayerConnection playerConnection1;
    private PlayerConnection playerConnection2;

    public TestGameBuilder() {

        this.cardStackOverwrite = null;
        this.highScoreStorageRepository = new InMemoryHighScoreRepository();
        this.playerConnection1 = new MockedPlayerConnection();
        this.playerConnection2 = new MockedPlayerConnection();

    }

    public TestGameBuilder oneCard() {
        this.playerCardStackSize = 1;
        this.cardStackSize = 1;

        return this;
    }

    public TestGameBuilder noCards() {
        this.playerCardStackSize = 1;
        this.cardStackSize = 0;

        return this;
    }

    public TestGameBuilder cardStack(CardStack cardStack) {
        this.cardStackOverwrite = cardStack;

        return this;
    }

    public TestGameBuilder cardStackPlayer1(CardStack cardStack) {
        this.player1CardStackOverwrite = cardStack;

        return this;
    }

    public TestGameBuilder highScoreStorageRepository(HighScoreStorageRepository highScoreStorageRepository) {
        this.highScoreStorageRepository = highScoreStorageRepository;

        return this;
    }

    public TestGameBuilder playerConnection1(MockedPlayerConnection playerConnection) {
        this.playerConnection1 = playerConnection;
        return this;
    }

    public TestGameBuilder playerConnection2(MockedPlayerConnection playerConnection) {
        this.playerConnection2 = playerConnection;
        return this;
    }

    public Game build() {
        CardStack mainCardStack = cardStackOverwrite != null ? cardStackOverwrite : cardStack(cardStackSize);
        CardStack playerCardStack = cardStack(playerCardStackSize);
        Card activeCard = mainCardStack.getCardList().isEmpty() ? null : mainCardStack.getCardList().get(0);

        return new Game(players(playerCardStack), mainCardStack, activeCard, highScoreStorageRepository);
    }

    private CardStack cardStack(int numberOfCards) {
        return generateCardStack(numberOfCards);
    }

    private List<PlayerWithConnection> players(CardStack playerCardStack) {
        CardStack cardStackPlayer1 = player1CardStackOverwrite != null ? player1CardStackOverwrite : playerCardStack;

        return List.of(new PlayerWithConnection(new Player("1", cardStackPlayer1), playerConnection1),
                new PlayerWithConnection(new Player("2", playerCardStack), playerConnection2));
    }

}
