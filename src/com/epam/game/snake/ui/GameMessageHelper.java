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
        return "Készítõ: Kérlek Refaktorálj\n" + "Programnév: Snake\n" + "Verziószám: v0.7";
    }

    public static Object getControllMessage() {
        return "Irányítás a kurzor segítségével:\n" + "-Fel nyíl: a kígyó felfele mozog\n"
                + "-Le nyíl: a kígyó lefele mozog\n" + "-Jobbra nyíl: a kígyó jobbra mozog\n" + "-Balra nyíl: a kígyó balra mozog\n";
    }

    public static String getGameOverMessage() {
        return "A játéknak vége!";
    }

    public static String getCongratMessage() {
        return "Gratulálok! Felkerültél a toplistára. Kérlek add meg a neved (max 10 betû):";
    }

    public static String getApologizeMessage() {
        return "Sajnos nem került be az eredményed a legjobb 10-be. Próbálkozz újra (F2).";
    }
}
