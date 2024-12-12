package com.example.demo.actors.projectile;

public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "Projectiles/bossFirealt.gif";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -18;
	private static final int INITIAL_X_POSITION = 950;

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}