package yc1125;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class yc123 {

    public static void main(String[] args) {
        game();
    }

    public static void game() {
        Scanner playerInput = new Scanner(System.in);
        PokerGameData pokerGameData = new PokerGameData();
        // 0. 設定玩家人數
        getAllKindsPlayerNumber(playerInput, pokerGameData);
        // 1. 產生一副撲克牌
        List<PokerPropertiesData> allPokers = generatePoker();
        // 依照人數決定每個人獲得幾張
        int getCardByPlayNumber = getCardByPlayNumber(pokerGameData.getPlayerNumber());
        // 3. 發牌
        pokerGameData.setAllPokerList(getCards(allPokers, pokerGameData.getPlayerNumber(), pokerGameData.getPlayerNumberIsPlayer(), getCardByPlayNumber));

        // 遊戲回合數量
        for (int round = 0; round < getCardByPlayNumber; round++) {
            List<PokerCompareData> compareDataList = new ArrayList<>();
            for (int player = 0; player < pokerGameData.getPlayerNumber(); player++) {
                if (Boolean.TRUE.equals(pokerGameData.getAllPokerList().get(player).getIsPlayer())) {
                    // 4. 玩家出牌
                    PokerPropertiesData playerPoker = playerMove(pokerGameData.getAllPokerList().get(player).getPokerPropertiesDataList(), playerInput, pokerGameData.getAllPokerList().get(player).getPlayerName());
                    compareDataList.add(new PokerCompareData(pokerGameData.getAllPokerList().get(player).getPlayerName(), playerPoker));
                } else {
                    // 5. 電腦出牌
                    PokerPropertiesData computerPoker = computerMove(pokerGameData.getAllPokerList().get(player).getPokerPropertiesDataList(), 1);
                    compareDataList.add(new PokerCompareData(pokerGameData.getAllPokerList().get(player).getPlayerName(), computerPoker));
                }
            }
            // 6. 比大小
            String winner = compare(compareDataList);
            // 7. 計分
            for (int player = 0; player < pokerGameData.getPlayerNumber(); player++) {
                if (winner.equals(pokerGameData.getAllPokerList().get(player).getPlayerName())) {
                    pokerGameData.getAllPokerList().get(player).setScore(pokerGameData.getAllPokerList().get(player).getScore() + 1);
                }
            }
        }
        // 8. 計算總分
        for (PokerPlayerData pokerPlayerData : pokerGameData.getAllPokerList()) {
            System.out.println(pokerPlayerData.getPlayerName() + "的總分為:" + pokerPlayerData.getScore());
        }
        // 9. 判斷輸贏
        int maxScore = 0;
        List<String> winnerList = new ArrayList<>();
        for (PokerPlayerData pokerPlayerData : pokerGameData.getAllPokerList()) {
            if (pokerPlayerData.getScore() > maxScore) {
                maxScore = pokerPlayerData.getScore();
                winnerList.clear();
                winnerList.add(pokerPlayerData.getPlayerName());
            } else if (pokerPlayerData.getScore() == maxScore) {
                winnerList.add(pokerPlayerData.getPlayerName());
            }
        }
        System.out.println("獲勝者為:" + winnerList);
        System.out.println("恭喜" + winnerList + "獲勝!!!");
        // 遊戲結束 再來一局?
        System.out.println("遊戲結束 再來一局? (Y/N)");
        String playAgain = playerInput.next();
        if (playAgain.equals("Y")) {
            game();
        } else {
            System.out.println("遊戲結束");
        }
    }

    private static void getAllKindsPlayerNumber(Scanner playerInput, PokerGameData pokerGameData) {
        pokerGameData.setPlayerNumber(getPlayerNumber(playerInput, "請輸入玩家人數"));
        pokerGameData.setPlayerNumberIsPlayer(getPlayerNumber(playerInput, "請輸入真人玩家人數"));
        if (pokerGameData.getPlayerNumberIsPlayer() > pokerGameData.getPlayerNumber()) {
            System.out.println("真人玩家人數不可大於總人數");
            getAllKindsPlayerNumber(playerInput, pokerGameData);
        }
    }

    private static int getPlayerNumber(Scanner playerInput, String text) {
        System.out.println(text);
        return playerInput.nextInt();
    }

    // 牌型比大小
    private static String compare(List<PokerCompareData> compareDataList) {

        String winner = "";
        for (PokerCompareData compareData : compareDataList) {
            if (winner.equals("")) {
                winner = compareData.getPlayerName();
            } else {
                if (compareData.getPokerPropertiesData().getTotalValue() > compareDataList.get(0).getPokerPropertiesData().getTotalValue()) {
                    winner = compareData.getPlayerName();
                }
            }
        }
        // 大家的出牌內容 以及標註贏家
        for (PokerCompareData compareData : compareDataList) {
            if (compareData.getPlayerName().equals(winner)) {
                System.out.println(compareData.getPlayerName() + "出的牌為:" + compareData.getPokerPropertiesData().getPokerName() + " 贏家");
            } else {
                System.out.println(compareData.getPlayerName() + "出的牌為:" + compareData.getPokerPropertiesData().getPokerName());
            }
        }
        return winner;
    }

    private static PokerPropertiesData computerMove(List<PokerPropertiesData> pokerPropertiesDataList, int mode) {
        switch (mode) {
            case 1:
            default:
                return computerMove1(pokerPropertiesDataList);
        }
    }

    private static PokerPropertiesData computerMove1(List<PokerPropertiesData> pokerPropertiesDataList) {
        int random = (int) (Math.random() * pokerPropertiesDataList.size());
        PokerPropertiesData computerPoker = pokerPropertiesDataList.get(random);
        return computerPoker;
    }

    private static PokerPropertiesData playerMove(List<PokerPropertiesData> playerPokerList, Scanner playerInput, String player) {
        System.out.println("你是" + player + "你的手牌有:");
        for (PokerPropertiesData propertiesData : playerPokerList) {
            System.out.printf(propertiesData.getPokerName() + " ");
        }
        System.out.println("請決定要出哪張牌? 可以輸入數字(1~" + playerPokerList.size() + ")或是牌名");
        String player1Input = playerInput.next();
        PokerPropertiesData playerPoker = null;
        if (player1Input.matches("[0-9]+")) {
            int index = Integer.parseInt(player1Input) - 1;
            if (index < 0 || index >= playerPokerList.size()) {
                System.out.println("你輸入的牌名有誤, 請重新輸入:");
                playerMove(playerPokerList, playerInput, player);
                return null;
            }
            playerPoker = playerPokerList.get(index);
        } else {
            for (PokerPropertiesData propertiesData : playerPokerList) {
                if (propertiesData.getPokerName().equals(player1Input)) {
                    playerPoker = propertiesData;
                    break;
                }
            }
            if (playerPoker == null) {
                System.out.println("你輸入的牌名有誤, 請重新輸入:");
                playerMove(playerPokerList, playerInput, player);
                return null;
            }
        }

        System.out.println("你出的牌是" + playerPoker.getPokerName());
        // 扣掉出的牌
        playerPokerList.remove(playerPoker);
        return playerPoker;
    }


    // 1. 產生一副撲克牌
    public static List<PokerPropertiesData> generatePoker() {
        List<PokerPropertiesData> pokerList = new ArrayList<>();
        for (int color = 0; color < 4; color++) {
            for (int number = 2; number < 15; number++) {
                PokerPropertiesData poker = new PokerPropertiesData();
                poker.value = number;
                poker.color = PokerColor.getPokerColor(color);
                poker.pokerName = PokerColor.getPokerName(color, number);
                poker.totalValue = color + number * 10;
                pokerList.add(poker);
            }
        }
        return pokerList;
    }


    // 2. 按人頭隨機不重複的發牌
    public static List<PokerPlayerData> getCards(List<PokerPropertiesData> allPoker, int playNumber, int playNumberIsPlayer, int getCardByPlayNumber) {
        List<PokerPlayerData> pokerListList = new ArrayList<>();
        for (int i = 0; i < playNumber; i++) {
            PokerPlayerData pokerPlayerData = new PokerPlayerData();
            if (i < playNumberIsPlayer) {
                pokerPlayerData.setIsPlayer(true);
                pokerPlayerData.setPlayerName("玩家" + (i + 1));
            } else {
                pokerPlayerData.setIsPlayer(false);
                pokerPlayerData.setPlayerName("電腦" + (i + 1 - playNumberIsPlayer));
            }
            List<PokerPropertiesData> pokerList = new ArrayList<>();
            for (int j = 0; j < getCardByPlayNumber; j++) {
                int random = (int) (Math.random() * allPoker.size());
                PokerPropertiesData poker = allPoker.get(random);
                pokerList.add(poker);
                allPoker.remove(random);
            }
            pokerList.sort((o1, o2) -> o1.getTotalValue() - o2.getTotalValue());
            pokerPlayerData.setPokerPropertiesDataList(pokerList);
            pokerListList.add(pokerPlayerData);

        }
        System.out.println(pokerListList);
        // 順便跟我說哪幾張牌是沒有被選到的
        System.out.println("這幾張牌沒用到:" + allPoker);
        return pokerListList;
    }

    public static int getCardByPlayNumber(int playNumber) {
        return 52 / playNumber;
    }

    // 4. 看牌
    // 5. 計算牌型
    // 6. 比牌
    // 撲克牌發牌


}