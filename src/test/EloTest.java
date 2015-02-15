package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import elo.EloCalculator;
import elo.Participant;
import elo.RaceResult;

public class EloTest {
	private static final double RATING_RANGE = 500;
	private static final double RATING_MIN = 400;
	private static final int SCORE_RANGE = 100;

	@Test
	public void conservationTest() {
		int amount = 100;
		String[] ids = new String[amount];
		Random r = new Random();
		Participant[] participants = new Participant[amount];
		for (int i = 0; i < amount; i++) {
			ids[i] = "p" + i;
			double rating = RATING_MIN + r.nextDouble() * RATING_RANGE;
			int score = r.nextInt(SCORE_RANGE);
			Participant p = new Participant(ids[i], rating, score);
			participants[i] = p;
		}
		RaceResult rr = new RaceResult(participants);
		double initialTotalRating = totalRating(participants);
		for (int i = 0; i < amount; i++) {
			EloCalculator.updateRating(rr);
			scrambleScore(rr);
		}
		double postTotalRating = totalRating(participants);
		assertEquals(initialTotalRating, postTotalRating, 0.001);
	}

	@Test
	public void winnerTest() {
		String[] ids = {"p1", "p2"};
		int nbrOfIterations = 100;
		double initialRating = 800;
		Participant[] participants = new Participant[2];
		for (int i = 0; i < nbrOfIterations; i++) {
			participants[0] = new Participant(ids[0], initialRating, 0);
			participants[1] = new Participant(ids[1], initialRating, 0);
			RaceResult rr = new RaceResult(participants);
			scrambleScore(rr);
			EloCalculator.updateRating(rr);
			Participant p1 = rr.participants[0];
			Participant p2 = rr.participants[1];
			if (p1.getRating() >= p2.getRating()) {
				assertTrue(p1.getScore() >= p2.getScore());
			}
		}
	}

	@Test
	public void tieTest() {
		String[] ids = {"p1", "p2"};
		int nbrOfIterations = 100;
		int score = 100;
		Participant[] participants = new Participant[2];
		for (int i = 0; i < nbrOfIterations; i++) {
			participants[0] = new Participant(ids[0], 0, score);
			participants[1] = new Participant(ids[1], 0, score);
			RaceResult rr = new RaceResult(participants);
			scrambleRating(rr);
			double initP1Rating = participants[0].getRating();
			double initP2Rating = participants[1].getRating();
			EloCalculator.updateRating(rr);
			double postP1Rating = participants[0].getRating();
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
			sum += participant.getRating();
		}
		return sum;
	}

	private void scrambleScore(RaceResult rr) {
		Participant[] participants = rr.participants;
		Random r = new Random();
		for (Participant participant : participants) {
			int score = r.nextInt(SCORE_RANGE);
			participant.setScore(score);
		}
	}

	private void scrambleRating(RaceResult rr) {
		Participant[] participants = rr.participants;
		Random r = new Random();
		for (Participant participant : participants) {
			double rating = RATING_MIN + r.nextDouble() * RATING_RANGE;
			participant.setRating(rating);
		}
	}
}
