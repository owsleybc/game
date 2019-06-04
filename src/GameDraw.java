//  borrowed from ngrounds

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class GameDraw {

	private static final float fontSize = 20.0f;
	public static final int timerEvent = 0;
	private final String title;
	private final int width;
	private final int height;
	private JFrame frame;
	private BufferedImage onscreenImage;
	private BufferedImage offscreenImage;
	private Graphics graphics;
	private Timer timer;
	private boolean animated = false;
	private BlockingQueue<Integer> eventQueue = new ArrayBlockingQueue<>(100);

	public GameDraw(GameBoard b) {
		width = 18 * b.getSize();
		height = 24 * b.getSize();
		title = "Blocks";
		init();
	}

	private void init() {
		frame = new JFrame();
		onscreenImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		offscreenImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		final ImageIcon icon = new ImageIcon(onscreenImage);
		final JLabel label = new JLabel(icon);
		frame.setContentPane(label);
		frame.setResizable(false);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(new KeyAdapter() {

			public void keyPressed(final KeyEvent e) {
				eventQueue.add(e.getKeyCode());
			}
		});
		graphics = offscreenImage.createGraphics();
		graphics.fillRect(0, 0, width, height);
		graphics.setFont(Font.decode(Font.MONOSPACED).deriveFont(Font.BOLD,
				fontSize));
		update();
	}

	public void setColor(final Color color) {
		graphics.setColor(color);
	}

	public void drawRectangle(final int x, final int y, final int w, final int h) {
		graphics.drawRect(x, y, w, h);
		update();
	}

	public void fillRectangle(final int x, final int y, final int w, final int h) {
		graphics.fillRect(x, y, w, h);
		update();
	}

	public void drawText(final int x, final int y, final String text) {
		graphics.drawString(text, x, y);
		update();
	}

	public void update() {
		if (!animated) {
			draw();
		}
	}

	public void draw() {
		onscreenImage.getGraphics().drawImage(offscreenImage, 0, 0, null);
		frame.repaint();
	}

	public Iterable<Integer> getEventIterable() {
		return new Iterable<Integer>() {

			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {

					private boolean closed = false;
					private Integer next;

					public Integer next() {
						return next;
					}

					public boolean hasNext() {
						try {
							next = eventQueue.take();
							closed = (next == Integer.MIN_VALUE);
						} catch (final InterruptedException ie) {
						}
						return !closed;
					}

					public void remove() {
					}
				};
			}
		};
	}

	public void startAnimation(final long delay) {
		if (animated) {
			return;
		}
		animated = true;
		timer = new Timer("GUI Refresh");
		timer.schedule(new TimerTask() {

			public void run() {
				eventQueue.add(0);
			}
		}, 0, delay);
	}

	public void stopAnimation() {
		if (timer != null) {
			timer.cancel();
		}
		animated = false;
		eventQueue.clear();
	}

	public void disableDoubleBuffering() {
		animated = false;
	}

	public void close() {
		if (timer != null) {
			timer.cancel();
		}
		eventQueue.clear();
		eventQueue.add(Integer.MIN_VALUE);
	}
}
