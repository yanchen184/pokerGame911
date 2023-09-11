package yc1125;

import lombok.Data;

import java.util.List;

@Data
public class PokerPlayerData {

    String playerName;
    Boolean isPlayer;
    List<PokerPropertiesData> pokerPropertiesDataList;
    int score;
}
