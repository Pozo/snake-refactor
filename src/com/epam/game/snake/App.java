package com.epam.game.snake;


public class App {

	public static void main(String[] args) {
	    new Thread(new SnakeGameImpl()).start();
	}

}
