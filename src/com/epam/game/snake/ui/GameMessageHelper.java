package com.epam.game.snake.ui;


public class GameMessageHelper {
    /*
    private static Properties prop = new Properties();
    
    {
        try {
            prop.load(GameMessageHelper.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    */
    public static String getAboutMessage() {
        return "K�sz�t�: K�rlek Refaktor�lj\n" + "Programn�v: Snake\n" + "Verzi�sz�m: v0.7";
    }

    public static Object getControllMessage() {
        return "Ir�ny�t�s a kurzor seg�ts�g�vel:\n" + "-Fel ny�l: a k�gy� felfele mozog\n"
                + "-Le ny�l: a k�gy� lefele mozog\n" + "-Jobbra ny�l: a k�gy� jobbra mozog\n" + "-Balra ny�l: a k�gy� balra mozog\n";
    }

    public static String getGameOverMessage() {
        return "A j�t�knak v�ge!";
    }

    public static String getCongratMessage() {
        return "Gratul�lok! Felker�lt�l a toplist�ra. K�rlek add meg a neved (max 10 bet�):";
    }

    public static String getApologizeMessage() {
        return "Sajnos nem ker�lt be az eredm�nyed a legjobb 10-be. Pr�b�lkozz �jra (F2).";
    }
}
