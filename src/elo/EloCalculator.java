package elo;

public class EloCalculator {
	public static int K = 32;
	public static int D = 400;
	
	public static void setState(int k, int d){
		K = k;
		D = d;
	}
	
	public static void updateRating(Participant[] participants) {
		double[][] expectedScores = new double[participants.length][participants.length];
		double[][] actualScores = new double[participants.length][participants.length];
		for (int i = 0; i < participants.length; i++) {
			for (int j = 0; j < participants.length; j++) {
				Participant p1 = participants[i];
				Participant p2 = participants[j];
				expectedScores[j][i] = expectedScore(p1, p2);
				actualScores[j][i] = translatedScore(p1, p2);
			}
		}
		// update ratings
		for (int i = 0; i < participants.length; i++) {
			Participant participant = participants[i];
			double currentRating = participant.newRating();
			double totalExpectation = 0;
			double totalActual = 0;
			for (int j = 0; j < participants.length; j++) {
				totalExpectation += expectedScores[j][i];
				totalActual += actualScores[j][i];
			}
			double newRating = newRating(currentRating, totalActual,
					totalExpectation);
			participant.setRating(newRating);
		}
	}

	private static double expectedScore(Participant player, Participant opponent) {
		double exponential = Math.pow(10,
				(opponent.newRating() - player.newRating()) / D);
		double denominator = 1 + exponential;
		return Math.pow(denominator, -1);
	}

	private static double translatedScore(Participant participant,
			Participant opponent) {
		int s1 = participant.getScore();
		int s2 = opponent.getScore();
		if (s1 > s2) {
			return 1;
		} else if (s1 < s2) {
			return 0;
		} else {
			return 0.5;
		}
	}

	private static double newRating(double currentRating, double actualScore,
			double expectedScore) {
		return currentRating + K * (actualScore - expectedScore);
	}

}
