package de.dhbwka.uno.application.game;


import de.dhbwka.uno.application.model.PlayerWithConnection;
import de.dhbwka.uno.application.persistance.HighScoreStorageRepository;
import de.dhbwka.uno.domain.*;

import java.util.List;
import java.util.Optional;

public class Game {
    private final List<PlayerWithConnection> players;
    private final CardStack cardStack;

    private Card activeCard;
    private int activeIndex = 0;
    private int direction = 1;
    private final HighScoreStorageRepository highScoreStorageRepository;

    public Game(
            List<PlayerWithConnection> players,
            CardStack cardStack,
            Card activeCard,
            HighScoreStorageRepository highScoreStorageRepository) {
        this.players = players;
        this.cardStack = cardStack;
        this.activeCard = activeCard;
        this.highScoreStorageRepository = highScoreStorageRepository;
    }

    public void start() {
        while(!isGameFinished()) {
            broadcastActivePlayer(getActivePlayer().player());
            next();
        }
        Player winner = getWinner();
        highScoreStorageRepository.addWin(winner);
        broadcastWinner(winner);
        broadcastHighScore();
    }

    private void broadcastHighScore() {
        List<SimplePlayer> simplePlayers = players.stream()
                .map(pwc -> (SimplePlayer) pwc.player())
                .toList();
        HighScore highScore = highScoreStorageRepository.getHighScore()
                .filter(simplePlayers);
        for(PlayerWithConnection p : players) {
            p.playerConnection().broadcastHighScore(highScore);
        }
    }

    private void broadcastActivePlayer(Player activePlayer) {
        for(PlayerWithConnection p : players) {
            p.playerConnection().broadcastActivePlayer(activePlayer);
        }
    }

    public void next() {
        Card card;
        do {
            card = getActivePlayer().placeCard(activeCard);
        } while(card != null && !activeCard.isCompatibleWith(card));

        int nextPlayerOffset = 1;
        Card nextNewCard = card;

        if(card != null) {
            if(card.hasAction()) {

                for (int i = 0; i < card.getAction().getDraw(); i++) {
                    getNextPlayer().player().getCardStack().add(cardStack.consumeFirst());
                }

                if (card.getAction().getAction() == Action.BLOCK) {
                    nextPlayerOffset = 2;
                } else if (card.getAction().getAction() == Action.CHANGE_DIRECTION) {
                    changeDirection();
                } else if (card.getAction().getAction() == Action.CHANGE_COLOR) {
                    CardColor color = getActivePlayer().playerConnection().selectColor();
                    nextNewCard = new Card(color);
                }
            }
            getActivePlayer().player().getCardStack().remove(card);
            activeCard = nextNewCard;
            cardStack.add(card);
        } else {
            getActivePlayer().player().getCardStack().add(cardStack.consumeFirst());
        }

        activeIndex = getNextPlayerIndex(nextPlayerOffset);
    }

    public boolean isGameFinished() {
        if(cardStack.isFinished()) {
            return true;
        }
        for(PlayerWithConnection player : players) {
            if(player.player().getCardStack().isFinished()) {
                return true;
            }
        }
        return false;
    }

    private void changeDirection() {
        direction = direction * -1;
    }

    private PlayerWithConnection getActivePlayer() {
        return players.get(activeIndex);
    }

    private int getNextPlayerIndex(int offset) {
        return (activeIndex + offset * direction + players.size()) % players.size();
    }

    private  PlayerWithConnection getNextPlayer() {
        return players.get(getNextPlayerIndex(1));
    }

    private Player getWinner() {
        Optional<Player> optionalPlayer = players.stream()
                .map(PlayerWithConnection::player)
                .filter(p -> p.getCardStack().isFinished())
                .findFirst();
        return optionalPlayer.orElse(null);
    }

    private void broadcastWinner(Player winner) {
        for(PlayerWithConnection player : players) {
            player.playerConnection().broadcastWinner(winner);
        }
    }

}
