package info.hska.erstiduell;

/**
 *
 * @author Tim Roes
 */
public class Team {

	private String name;
	private int points;
	private int penalty = 0;

	public Team(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPenalty() {
		this.penalty++;
	}

	public int getPenalty() {
		return this.penalty;
	}

	public void resetPenalty() {
		this.penalty = 0;
	}

}
