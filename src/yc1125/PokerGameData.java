package yc1125;

import lombok.Data;

import java.util.List;

@Data
public class PokerGameData {

    /**
     * 玩家人數
     */
    Integer playerNumber;
    /**
     * 多少玩家為真人
     */
    Integer playerNumberIsPlayer;
    /**
     * 所有撲克牌
     */
    List<PokerPlayerData> allPokerList;
}
