import java.awt.Color;

public final class GameBoard {

	private double version = 3.0;
	private final int width = 10;
	private final int height = 22;
	private final int size = GameBlock.getSize();
	private boolean[][] matrix;
	private Color[][] color;

	public GameBoard() {
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return size;
	}

	public void drawText(GameDraw d, Color c, int x, int y, int i, String s) {
		d.setColor(c);
		d.drawText(x * size - 12 * i / 2, 6 + y * size + size / 2, s);
	}

	public void drawBoard(GameDraw d) {
		d.setColor(Color.black);
		d.fillRectangle(0, 0, 18 * size, 24 * size);
		d.setColor(Color.white);
		d.drawRectangle(-1 + 2 * size, -1 + 2 * size, 1 + 10 * size,
				1 + 20 * size);
		d.setColor(Color.darkGray);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				d.drawRectangle((2 + x) * size, (2 + y) * size, -1 + size, -1
						+ size);
			}
		}
	}

	public void drawPlay(GameDraw d) {
		d.setColor(Color.black);
		d.fillRectangle(3 * size, 8 * size, 8 * size, size);
		d.fillRectangle(3 * size, 15 * size, 8 * size, size);
		d.setColor(Color.white);
		d.drawRectangle(-1 + 3 * size, -1 + 8 * size, 1 + 8 * size, 1 + size);
		d.drawRectangle(-1 + 3 * size, -1 + 15 * size, 1 + 8 * size, 1 + size);
		drawText(d, Color.white, 7, 8, 19, "Press Enter to Play");
		drawText(d, Color.white, 7, 15, 16, "Press H for Help");
		d.drawRectangle(size, -1 + 3 * size, 16 * size, 1);
		d.drawRectangle(-1 + 13 * size, size, 1, 22 * size);
		drawText(d, Color.white, 15, 1, 8, "Blocks");
		drawText(d, Color.white, 15, 2, 4, "v" + version);
	}

	public void drawHelp(GameDraw d) {
		d.setColor(Color.black);
		d.fillRectangle(2 * size, 2 * size, 10 * size, 20 * size);
		drawText(d, Color.white, 7, 2, 8, "Controls");
		drawText(d, Color.white, 7, 4, 20, "Play");
		drawText(d, Color.white, 7, 5, 16, "Enter");
		drawText(d, Color.white, 7, 7, 20, "Pause");
		drawText(d, Color.white, 7, 8, 16, "P");
		drawText(d, Color.white, 7, 10, 20, "Quit");
		drawText(d, Color.white, 7, 11, 16, "Q");
		drawText(d, Color.white, 7, 13, 20, "Move");
		drawText(d, Color.white, 7, 14, 16, "Left, Right, Down");
		drawText(d, Color.white, 7, 16, 20, "Rotate");
		drawText(d, Color.white, 7, 17, 16, "Z, X, Up");
		drawText(d, Color.white, 7, 19, 20, "Drop");
		drawText(d, Color.white, 7, 20, 16, "Space");
	}

	public void drawScore(GameDraw d, GameScore s, Color c) {
		drawText(d, c, 15, 9, 5, "Level");
		drawText(d, c, 15, 10, 5, String.format("%7d", s.getLevel()));
		drawText(d, c, 15, 11, 5, "Lines");
		drawText(d, c, 15, 12, 5, String.format("%7d", s.getLines()));
		drawText(d, c, 15, 13, 5, "Score");
		drawText(d, c, 15, 14, 5, String.format("%7d", s.getScore()));
	}

	public void play(GameDraw d, GameScore s) {
		drawBoard(d);
		drawScore(d, s, Color.white);
		matrix = new boolean[getHeight()][getWidth()];
		color = new Color[getHeight()][getWidth()];
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				matrix[y][x] = false;
				color[y][x] = Color.black;
			}
		}
		d.startAnimation(s.getSpeed());
	}

	public void gameOver(GameDraw d, GameScore s) {
		d.stopAnimation();
		drawText(d, Color.red, 15, 18, 9, "Game Over");
		drawPlay(d);
	}

	public void pause(GameDraw d, GamePiece p) {
		d.stopAnimation();
		drawText(d, Color.yellow, 15, 18, 6, "Paused");
		d.setColor(p.getColor());
		p.draw(d);
	}

	public void unPause(GameDraw d, GamePiece p, GameScore s) {
		drawText(d, Color.black, 15, 18, 6, "Paused");
		p.draw(d);
		d.startAnimation(s.getSpeed());
	}

	public void stopStart(GameDraw d, GameScore s) {
		d.stopAnimation();
		d.startAnimation(s.getSpeed());
		for (int i : d.getEventIterable()) {
			if (i == GameDraw.timerEvent) {
				break;
			}
		}
	}

	public void nextPiece(GameDraw d, GamePiece n, Color c) {
		d.setColor(c);
		for (int x = 0; x < n.getLength(); x++) {
			for (int y = 0; y < 3 - (n.getLength() / 2); y++) {
				d.drawRectangle((15 + x) * size - n.getLength() * size / 2,
						(3 + y + n.getLength() / 2) * size, -1 + size, -1
								+ size);
			}
		}
		drawText(d, c, 15, 6, 4, "Next");
	}

	public void pinPiece(GameDraw d, GamePiece p) {
		d.setColor(p.getColor());
		p.draw(d);
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (p.intersect(x, y)) {
					matrix[y][x] = true;
					color[y][x] = p.getColor();
				}
			}
		}
	}

	public boolean intersect(GamePiece p) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (p.intersect(x, y) && matrix[y][x]) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean leftOut(GamePiece p) {
		for (int y = 0; y < p.getLength(); y++) {
			if (p.intersect(-1, p.getY() + y) && p.getX() < 0) {
				return true;
			}
		}
		return false;
	}

	public boolean rightOut(GamePiece p) {
		for (int y = 0; y < p.getLength(); y++) {
			if (p.intersect(getWidth(), p.getY() + y)
					&& p.getX() + p.getLength() > getWidth()) {
				return true;
			}
		}
		return false;
	}

	public boolean downOut(GamePiece p) {
		for (int x = 0; x < p.getLength(); x++) {
			if (p.intersect(p.getX() + x, getHeight())
					&& p.getY() + p.getLength() > getHeight()) {
				return true;
			}
		}
		return false;
	}

	public boolean rotateLeft(GamePiece p) {
		for (int i = 0; i < 9; i++) {
			if (leftOut(p) || rightOut(p) || downOut(p) || intersect(p)) {
				if (i == 0) {
					if (p.getLength() == 2) {
						return true;
					}
					p.setY(p.getY() + 1);
				}
				if (i == 1) {
					p.setY(p.getY() - 1);
					p.setX(p.getX() + 1);
				}
				if (i == 2) {
					p.setX(p.getX() - 2);
				}
				if (i == 3) {
					p.setX(p.getX() + 1);
					p.setY(p.getY() - 1);
				}
				if (i == 4) {
					p.setY(p.getY() + 1);
					if (p.getLength() == 3) {
						return true;
					}
					p.setY(p.getY() + 2);
				}
				if (i == 5) {
					p.setY(p.getY() - 2);
					p.setX(p.getX() + 2);
				}
				if (i == 6) {
					p.setX(p.getX() - 4);
				}
				if (i == 7) {
					p.setX(p.getX() + 2);
					p.setY(p.getY() - 2);
				}
				if (i == 8) {
					p.setY(p.getY() + 2);
					return true;
				}
			}
		}
		return false;
	}

	public boolean rotateRight(GamePiece p) {
		for (int i = 0; i < 9; i++) {
			if (leftOut(p) || rightOut(p) || downOut(p) || intersect(p)) {
				if (i == 0) {
					if (p.getLength() == 2) {
						return true;
					}
					p.setY(p.getY() + 1);
				}
				if (i == 1) {
					p.setY(p.getY() - 1);
					p.setX(p.getX() - 1);
				}
				if (i == 2) {
					p.setX(p.getX() + 2);
				}
				if (i == 3) {
					p.setX(p.getX() - 1);
					p.setY(p.getY() - 1);
				}
				if (i == 4) {
					p.setY(p.getY() + 1);
					if (p.getLength() == 3) {
						return true;
					}
					p.setY(p.getY() + 2);
				}
				if (i == 5) {
					p.setY(p.getY() - 2);
					p.setX(p.getX() - 2);
				}
				if (i == 6) {
					p.setX(p.getX() + 4);
				}
				if (i == 7) {
					p.setX(p.getX() - 2);
					p.setY(p.getY() - 2);
				}
				if (i == 8) {
					p.setY(p.getY() + 2);
					return true;
				}
			}
		}
		return false;
	}

	private boolean line(int y) {
		for (int x = 0; x < getWidth(); x++) {
			if (!matrix[y][x]) {
				return false;
			}
		}
		return true;
	}

	public boolean line(GameDraw d, GameScore s) {
		int i = 0;
		for (int y = 0; y < getHeight(); y++) {
			if (line(y)) {
				for (int x = 0; x < getWidth(); x++) {
					d.setColor(Color.white);
					new GameBlock(2 * x, 2 * y).draw(d);
				}
				i++;
			}
		}
		if (i > 0) {
			drawScore(d, s, Color.black);
			s.changeScore(i);
			drawScore(d, s, Color.white);
			stopStart(d, s);
			return true;
		}
		return false;
	}

	public void lineClear(GameDraw d) {
		for (int j = 0; j < getHeight(); j++) {
			if (line(j)) {
				for (int y = j; y > 0; y--) {
					for (int x = 0; x < getWidth(); x++) {
						matrix[y][x] = matrix[-1 + y][x];
						color[y][x] = color[-1 + y][x];
						d.setColor(color[y][x]);
						new GameBlock(2 * x, 2 * y).draw(d);
					}
				}
			}
		}
	}
}
