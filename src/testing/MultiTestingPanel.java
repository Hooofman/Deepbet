package testing;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MultiTestingPanel  extends JPanel{
	protected static JLabel startIt;
	protected static JLabel endIt;
	protected static JLabel incIt;
	
	protected static JLabel startMomentum;
	protected static JLabel endMomentum;
	protected static JLabel incMomentum;
	
	protected static JLabel startLearningRate;
	protected static JLabel endLearningRate;
	protected static JLabel incLearningRate;
	
	protected static JLabel numberOfCalculations;
	protected static JLabel numberOfThreads;
	
	protected static JTextField txtstartIt;
	protected static JTextField txtendIt;
	protected static JTextField txtincIt;
	
	protected static JTextField txtstartMomentum;
	protected static JTextField txtendMomentum;
	protected static JTextField txtincMomentum;
	
	protected static JTextField txtstartLearningRate;
	protected static JTextField txtendLearningRate;
	protected static JTextField txtincLearningRate;
	

	protected static JButton calculateCalculations;
	protected static JTextField txtNbrOfThreads;
	
	public MultiTestingPanel(ActionListener listener) {
		init();
		calculateCalculations.addActionListener(listener);
	}
	
	protected static void updateCalculations() {
		//int it = (Integer.parseInt(txtendIt.getText()) / Integer.parseInt(txtincIt.getText()) ) - Integer.parseInt(txtstartIt.getText())/Integer.parseInt(txtincIt.getText()) / Integer.parseInt(txtincIt.getText());
		//double LR = ((Double.parseDouble(txtendLearningRate.getText()) /Double.parseDouble(txtincLearningRate.getText())) - (Double.parseDouble(txtstartLearningRate.getText()))/Double.parseDouble(txtincLearningRate.getText()));
		//double mom = ((Double.parseDouble(txtendMomentum.getText()) /Double.parseDouble(txtincMomentum.getText())) - (Double.parseDouble(txtstartMomentum.getText()))/Double.parseDouble(txtincMomentum.getText()));
		int number = 0;
		for ( int it = Integer.parseInt(txtstartIt.getText()); it < Integer.parseInt(txtendIt.getText()); it += Integer.parseInt(txtincIt.getText())){
			for(double lr = Double.parseDouble(txtstartLearningRate.getText()); lr <Double.parseDouble(txtendLearningRate.getText()); lr += Double.parseDouble(txtincMomentum.getText()) ) {
				for(double mom = Double.parseDouble(txtstartMomentum.getText()); mom < Double.parseDouble(txtendMomentum.getText()); mom += Double.parseDouble(txtincMomentum.getText())) {
					number++;
				}
			}
		}
		
		numberOfCalculations.setText("Number of calculations: "+number);
	}
	
	public void init() {
		setLayout(new GridLayout(11,2,5,5));
		
		
		setBorder(new EmptyBorder(10, 10, 10, 10));
		startIt = new JLabel("Start number of Iterations");
		endIt = new JLabel("End number of Iterations");
		incIt = new JLabel("Increase each step");
		
		startMomentum = new JLabel("Start Momentum");
		endMomentum = new JLabel("End Momentum");
		incMomentum = new JLabel("Increase Momentum");
		
		startLearningRate = new JLabel("Start Learningrate");
		endLearningRate = new JLabel("End Learningrate");
		incLearningRate = new JLabel("Increase Learningrate");
		
		numberOfCalculations = new JLabel("Number of calculations: ");
		numberOfThreads = new JLabel("Number of Threads to use:");
		calculateCalculations = new JButton("Calculate");
		
		txtstartIt = new JTextField("1000");
		txtendIt = new JTextField("5000");
		txtincIt = new JTextField("1000");
		
		txtstartMomentum = new JTextField("0.1");
		txtendMomentum = new JTextField("0.5");
		txtincMomentum = new JTextField("0.1");
		
		txtstartLearningRate = new JTextField("0.1");
		txtendLearningRate = new JTextField("0.5");
		txtincLearningRate = new JTextField("0.1");
		txtNbrOfThreads = new JTextField("3");
		add(startIt);
		add(txtstartIt);
		
		add(endIt);
		add(txtendIt);
		
		add(incIt);
		add(txtincIt);
		
		add(startMomentum);
		add(txtstartMomentum);
		
		add(endMomentum);
		add(txtendMomentum);
		
		add(incMomentum);
		add(txtincMomentum);
		
		add(startLearningRate);
		add(txtstartLearningRate);
		
		add(endLearningRate);
		add(txtendLearningRate);
		
		add(incLearningRate);
		add(txtincLearningRate);
		
		add(numberOfCalculations);
		add(calculateCalculations);
		
		add(numberOfThreads);
		add(txtNbrOfThreads);
	}
}
