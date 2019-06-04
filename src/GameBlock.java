public class GameBlock {

	private static final int size = 40;
	private int x;
	private int y;

	public GameBlock(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static final int getSize() {
		return size;
	}

	public void draw(GameDraw d) {
		if (y > 2) {
			if (x % 2 == 0 && y % 2 == 0) {
				d.fillRectangle(2 + (2 + x / 2) * size, 2 + (y / 2) * size, -3
						+ size, -3 + size);
			}
			if (x % 2 == 0 && y % 2 == 1) {
				d.fillRectangle(2 + (2 + x / 2) * size, 2 + (y / 2) * size
						+ size / 2, -3 + size, -3 + size);
			}
			if (x % 2 == 1 && y % 2 == 0) {
				d.fillRectangle(2 + (2 + x / 2) * size + size / 2, 2 + (y / 2)
						* size, -3 + size, -3 + size);
			}
			if (x % 2 == 1 && y % 2 == 1) {
				d.fillRectangle(2 + (2 + x / 2) * size + size / 2, 2 + (y / 2)
						* size + size / 2, -3 + size, -3 + size);
			}
		}
	}
}
