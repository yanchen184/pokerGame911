package yc1125;

import lombok.Data;

import java.util.List;

@Data
public class PokerCompareData {

    String playerName;
    PokerPropertiesData pokerPropertiesData;

    public PokerCompareData(String player, PokerPropertiesData player1Poker) {
        this.playerName = player;
        this.pokerPropertiesData = player1Poker;
    }
}
