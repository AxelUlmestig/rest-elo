package elo;
public class Participant {
	private double initialRating;
	private double newRating;
	private int score;

	public Participant(double rating, int score) {
		this.initialRating = rating;
		this.score = score;
		setRating(initialRating);
	}

	public double InitialRating() {
		return initialRating;
	}
	
	public double newRating() {
		return newRating;
	}
	
	public void setRating(double rating) {
		this.newRating = rating;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score){
		this.score = score;
	}

}
