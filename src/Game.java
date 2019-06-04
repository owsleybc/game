import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Game {

	public static void main(String[] args) {

		GameBoard b = new GameBoard();
		GameDraw d = new GameDraw(b);
		GameScore s = null;
		Random r = new Random();
		GamePiece n = new GamePiece(r.nextInt(7));
		GamePiece p = null;
		b.drawBoard(d);
		b.drawPlay(d);
		boolean play = false;
		boolean line = false;
		for (int i : d.getEventIterable()) {
			if (i == KeyEvent.VK_ENTER) {
				s = new GameScore();
				b.play(d, s);
			}
			if (i == KeyEvent.VK_H) {
				b.drawHelp(d);
			}
			if (i == GameDraw.timerEvent) {
				play = true;
			}
			while (play) {
				if (b.line(d, s)) {
					line = true;
				}
				if (!line) {
					d.draw();
					b.nextPiece(d, n, Color.black);
					n.draw(d);
					p = n;
					p.setX(5 - (1 + p.getLength()) / 2);
					p.setY(1);
					d.setColor(p.getColor());
					p.draw(d);
					if (b.intersect(p)) {
						b.gameOver(d, s);
						play = false;
						break;
					}
					n = new GamePiece(r.nextInt(7));
					n.setX(13 - (double) n.getLength() / 2);
					n.setY(4);
					d.setColor(n.getColor());
					n.draw(d);
					b.nextPiece(d, n, Color.darkGray);
				}
				d.draw();
				for (int j : d.getEventIterable()) {
					if (line && j == GameDraw.timerEvent) {
						b.lineClear(d);
						line = false;
						break;
					}
					if (!line) {
						d.setColor(Color.black);
						p.draw(d);
						if (j == GameDraw.timerEvent) {
							p.setY(p.getY() + 1);
							if (b.downOut(p) || b.intersect(p)) {
								p.setY(p.getY() - 1);
								b.pinPiece(d, p);
								break;
							}
						}
						if (j == KeyEvent.VK_P) {
							b.pause(d, p);
							for (int k : d.getEventIterable()) {
								if (k == KeyEvent.VK_P) {
									b.unPause(d, p, s);
									break;
								}
								if (k == KeyEvent.VK_Q) {
									b.unPause(d, p, s);
									j = KeyEvent.VK_Q;
									break;
								}
							}
						}
						if (j == KeyEvent.VK_Q) {
							b.nextPiece(d, n, Color.black);
							n.draw(d);
							b.gameOver(d, s);
							play = false;
							break;
						}
						if (j == KeyEvent.VK_Z) {
							p.rotateLeft();
							if (b.rotateLeft(p)) {
								p.rotateRight();
							}
						}
						if (j == KeyEvent.VK_X || j == KeyEvent.VK_UP) {
							p.rotateRight();
							if (b.rotateRight(p)) {
								p.rotateLeft();
							}
						}
						if (j == KeyEvent.VK_LEFT) {
							p.setX(p.getX() - 1);
							if (b.leftOut(p) || b.intersect(p)) {
								p.setX(p.getX() + 1);
							}
						}
						if (j == KeyEvent.VK_RIGHT) {
							p.setX(p.getX() + 1);
							if (b.rightOut(p) || b.intersect(p)) {
								p.setX(p.getX() - 1);
							}
						}
						if (j == KeyEvent.VK_DOWN) {
							p.setY(p.getY() + 1);
							if (b.downOut(p) || b.intersect(p)) {
								p.setY(p.getY() - 1);
							}
						}
						if (j == KeyEvent.VK_SPACE) {
							while (!b.downOut(p) && !b.intersect(p)) {
								p.setY(p.getY() + 1);
							}
							p.setY(p.getY() - 1);
							b.pinPiece(d, p);
							b.stopStart(d, s);
							break;
						}
						d.setColor(p.getColor());
						p.draw(d);
					}
					d.draw();
				}
			}
		}
	}
}
