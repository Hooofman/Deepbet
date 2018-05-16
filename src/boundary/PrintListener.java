package boundary;

public interface PrintListener {
	public void updateText(String text);
	public void updateProgress(int current, int max);
}
