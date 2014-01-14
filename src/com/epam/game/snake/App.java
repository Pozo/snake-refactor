package com.epam.game.snake;


public class App {
	private static final String SNAKE_GAME_MAIN_THREAD_NAME = "SnakeGame [main]";

    public static void main(String[] args) {
	    new Thread(new SnakeGameImpl(), SNAKE_GAME_MAIN_THREAD_NAME).start();
	}
}
