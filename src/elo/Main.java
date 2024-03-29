package elo;


public class Main {
	public static void main(String[] args) {		
		Participant p1 = new Participant("p1", 453, 150);
		Participant p2 = new Participant("p2", 123, 150);
		//Participant p3 = new Participant(453, 234);
		Participant[] participants = {p1, p2};
		RaceResult raceResult = new RaceResult(participants);
		double averageRating = averageRating(participants);
		System.out.println("average rating before: " + averageRating);
		EloCalculator.updateRating(raceResult);
		averageRating = averageRating(participants);
		System.out.println("average rating after: " + averageRating);
	}
	
	public static double averageRating(Participant[] participants){
		double total = 0;
		for(int i = 0; i < participants.length; i++){
			Participant p = participants[i];
			total += p.getRating();
			System.out.println(p.getId() + " rating: " + p.getRating());
		}
		return total/(participants.length);
	}
}
 