package boundary;

/**
 * Simple interface
 * @author Sven Lindqvist, Johannes Roos
 *
 */
public interface PrintListener {
	public void updateText(String text);
	public void updateProgress(int current, int max);
}
