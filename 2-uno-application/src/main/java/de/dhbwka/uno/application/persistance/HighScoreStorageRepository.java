package de.dhbwka.uno.application.persistance;

import de.dhbwka.uno.domain.HighScore;
import de.dhbwka.uno.domain.SimplePlayer;

public interface HighScoreStorageRepository {

    void addWin(SimplePlayer player);
    HighScore getHighScore();

}
