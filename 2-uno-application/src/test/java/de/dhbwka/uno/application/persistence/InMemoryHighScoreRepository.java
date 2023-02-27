package de.dhbwka.uno.application.persistence;

import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.HighScore;
import de.dhbwka.uno.domain.SimplePlayer;

import java.util.Map;

public class InMemoryHighScoreRepository implements HighScoreStorageRepository {
    private final HighScore highScore = new HighScore();

    @Override
    public void addWin(SimplePlayer player) {
        Map<SimplePlayer, Integer> wins = highScore.getElements();
        if (!wins.containsKey(player)) {
            wins.put(player, 0);
        }

        wins.put(player, wins.get(player) + 1);
    }

    @Override
    public HighScore getHighScore() {
        return highScore;
    }

}
