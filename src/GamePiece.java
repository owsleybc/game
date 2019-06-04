import java.awt.Color;

public class GamePiece {

	private double x;
	private double y;
	private Color color;
	private boolean[][] shape;
	private boolean[][] rotateShape;

	public GamePiece(int i) {
		if (i == 0) {
			color = Color.red;
			shape = new boolean[4][4];
			shape[1][0] = true;
			shape[1][1] = true;
			shape[1][2] = true;
			shape[1][3] = true;
		}
		if (i == 1) {
			color = Color.cyan;
			shape = new boolean[2][2];
			shape[0][0] = true;
			shape[0][1] = true;
			shape[1][0] = true;
			shape[1][1] = true;
		}
		if (i == 2) {
			color = Color.magenta;
			shape = new boolean[3][3];
			shape[0][0] = true;
			shape[1][0] = true;
			shape[1][1] = true;
			shape[1][2] = true;
		}
		if (i == 3) {
			color = Color.lightGray;
			shape = new boolean[3][3];
			shape[0][1] = true;
			shape[1][0] = true;
			shape[1][1] = true;
			shape[1][2] = true;
		}
		if (i == 4) {
			color = Color.yellow;
			shape = new boolean[3][3];
			shape[0][2] = true;
			shape[1][0] = true;
			shape[1][1] = true;
			shape[1][2] = true;
		}
		if (i == 5) {
			color = Color.green;
			shape = new boolean[3][3];
			shape[0][0] = true;
			shape[0][1] = true;
			shape[1][1] = true;
			shape[1][2] = true;
		}
		if (i == 6) {
			color = Color.blue;
			shape = new boolean[3][3];
			shape[0][1] = true;
			shape[0][2] = true;
			shape[1][0] = true;
			shape[1][1] = true;
		}
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getX() {
		return (int) x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getY() {
		return (int) y;
	}

	public Color getColor() {
		return color;
	}

	public int getLength() {
		return shape.length;
	}

	public void rotateLeft() {
		rotateShape = new boolean[getLength()][getLength()];
		for (int j = 0; j < getLength(); j++) {
			for (int i = 0; i < getLength(); i++) {
				rotateShape[getLength() - i - 1][j] = shape[j][i];
			}
		}
		shape = rotateShape;
	}

	public void rotateRight() {
		rotateShape = new boolean[getLength()][getLength()];
		for (int j = 0; j < getLength(); j++) {
			for (int i = 0; i < getLength(); i++) {
				rotateShape[i][getLength() - j - 1] = shape[j][i];
			}
		}
		shape = rotateShape;
	}

	public boolean intersect(int x, int y) {
		if (x < getX() || y < getY() || x >= getX() + getLength()
				|| y >= getY() + getLength()) {
			return false;
		}
		return shape[(int) (y - getY())][(int) (x - getX())];
	}

	public void draw(GameDraw d) {
		for (int j = 0; j < getLength(); j++) {
			for (int i = 0; i < getLength(); i++) {
				if (shape[j][i]) {
					new GameBlock((int) ((x + i) * 2), (int) ((y + j) * 2))
							.draw(d);
				}
			}
		}
	}
}
