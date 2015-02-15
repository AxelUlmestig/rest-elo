package elo;

public class Participant {
	private String id;
	private double rating;
	private int score;

	public Participant(String id, double rating, int score) {
		this.id = id;
		this.rating = rating;
		this.score = score;
	}
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
