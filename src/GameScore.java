public class GameScore {

	private final int startSpeed = 800;
	private final int oneLine = 10;
	private final int twoLine = 30;
	private final int threeLine = 60;
	private final int fourLine = 100;
	private final int[] line = new int[] { oneLine, twoLine, threeLine,
			fourLine };
	private boolean fourLines;
	private int quad;
	private int level;
	private int lines;
	private int score;
	private int speed;

	public GameScore() {
		quad = 1;
		level = 1;
		lines = 0;
		score = 0;
		speed = startSpeed;
	}

	public int getLevel() {
		return level;
	}

	public int getLines() {
		return lines;
	}

	public int getScore() {
		return score;
	}

	public int getSpeed() {
		return speed;
	}

	public void changeScore(int i) {
		if (i == 4 && fourLines) {
			quad++;
		}
		if (i == 4) {
			fourLines = true;
		} else {
			fourLines = false;
			quad = 1;
		}
		lines += i;
		score += level * line[i - 1] * quad;
		if (level < 20 && lines >= level * 10) {
			level++;
			speed -= (startSpeed / 20);
		}
	}
}
