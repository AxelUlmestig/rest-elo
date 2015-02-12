package test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import elo.EloCalculator;
import elo.Participant;

public class EloTest {
	private static final double RATING_RANGE = 500;
	private static final double RATING_MIN = 400;
	private static final int SCORE_RANGE = 100;

	@Test
	public void conservationTest() {
		int amount = 100;
		Random r = new Random();
		Participant[] participants = new Participant[amount];
		for (int i = 0; i < amount; i++) {
			double rating = RATING_MIN + r.nextDouble() * RATING_RANGE;
			int score = r.nextInt(SCORE_RANGE);
			Participant p = new Participant(rating, score);
			participants[i] = p;
		}
		double initialTotalRating = totalRating(participants);
		for (int i = 0; i < amount; i++) {
			EloCalculator.updateRating(participants);
			scrambleScore(participants);
		}
		double postTotalRating = totalRating(participants);
		assertEquals(initialTotalRating, postTotalRating, 0.001);
	}

	@Test
	public void winnerTest() {
		int nbrOfIterations = 100;
		double initialRating = 800;
		Participant[] participants = new Participant[2];
		for (int i = 0; i < nbrOfIterations; i++) {
			participants[0] = new Participant(initialRating, 0);
			participants[1] = new Participant(initialRating, 0);
			scrambleScore(participants);
			EloCalculator.updateRating(participants);
			Participant p1 = participants[0];
			Participant p2 = participants[1];
			if (p1.newRating() >= p2.newRating()) {
				assertTrue(p1.getScore() >= p2.getScore());
			}
		}
	}

	@Test
	public void tieTest() {
		int nbrOfIterations = 100;
		int score = 100;
		Participant[] participants = new Participant[2];
		for (int i = 0; i < nbrOfIterations; i++) {
			participants[0] = new Participant(0, score);
			participants[1] = new Participant(0, score);
			scrambleRating(participants);
			double initP1Rating = participants[0].newRating();
			double initP2Rating = participants[1].newRating();
			EloCalculator.updateRating(participants);
			double postP1Rating = participants[0].newRating();
			if(postP1Rating >= initP1Rating) {
				assertTrue(initP1Rating <= initP2Rating);
			} else {
				assertTrue(initP1Rating > initP2Rating);
			}
		}
	}

	private double totalRating(Participant[] participants) {
		double sum = 0;
		for (Participant participant : participants) {
			sum += participant.newRating();
		}
		return sum;
	}

	private void scrambleScore(Participant[] participants) {
		Random r = new Random();
		for (Participant participant : participants) {
			int score = r.nextInt(SCORE_RANGE);
			participant.setScore(score);
		}
	}

	private void scrambleRating(Participant[] participants) {
		Random r = new Random();
		for (Participant participant : participants) {
			double rating = RATING_MIN + r.nextDouble() * RATING_RANGE;
			participant.setRating(rating);
		}
	}
}
