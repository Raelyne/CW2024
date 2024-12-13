package com.example.demo.actors;

/**
 * The Destructible interface defines the contract for objects that can be damaged
 * and destroyed within the game. Any class that implements this interface must
 * provide implementations for the methods to handle taking damage and being destroyed.
 */
public interface Destructible {

	/**
	 * This method is called when the object takes damage. The implementation of this
	 * method should define how the object reacts to receiving damage (e.g., reducing health).
	 */
	void takeDamage();

	/**
	 * This method is called when the object is destroyed. The implementation should
	 * define what happens when the object is destroyed (e.g., removing the object from the game world).
	 */
	void destroy();
	
}
