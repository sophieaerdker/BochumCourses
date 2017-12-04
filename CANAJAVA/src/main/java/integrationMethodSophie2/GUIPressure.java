package integrationMethodSophie2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIPressure extends JFrame implements IntegrationListener {

	private JPanel aJPanel;
	private JLabel DensityLabel;
	private JLabel RadiusLabel;
	private JLabel StepLabel;
	private JTextField RadiusTextField;
	private JTextField DensityTextField;
	private JTextField StepTextField;
	private JButton aJButton;
	private JRadioButton EulerButton;
	private JRadioButton SimpsonButton;
	private JProgressBar progressBar;
	private JLabel status;

	private double r;
	private double rho;
	private int n;
	private boolean E = false;
	private boolean S = false;

	public GUIPressure() {

		super("Calculating Pressure in a sphere"); // initialize JFrame with a string(title)
		init();
		constructWindow();
	}

	private void StartCalculation() {

		this.r = Double.parseDouble(RadiusTextField.getText());
		this.rho = Double.parseDouble(DensityTextField.getText());
		this.n = Integer.parseInt(StepTextField.getText());
		this.progressBar.setMaximum(this.n);

		this.E = EulerButton.isSelected();
		this.S = SimpsonButton.isSelected();

		if (this.E == false && this.S == false) {
			System.err.println("No Method has been selected!");
		} else {

			System.out.println("Starting calculation...");
			IntegratingPressure integrate = new IntegratingPressure();
			integrate.addListener(this);
			this.progressBar.setMaximum(this.n);

			if (this.E) {

				System.out.println("Pressure calculated with Euler Method: " + integrate.EulersMethod(r, rho, n));
			} else if (this.S) {
				System.out.println("Pressure calculated with Simpsons Method: " + integrate.SimpsonsMethod(r, rho, n));
			}
		}
	}

	@Override
	public void nextStep(IntegrationEvent event) {
		if (event.isFinished() == false) {
			this.progressBar.setValue(event.getValue());
			this.status.setForeground(Color.red);
			this.status.setText("Calculating Pressure...");
			System.out.println(event.getValue());
		} else if (event.isFinished()) {
			System.out.println("Calculation is finished");
			this.status.setForeground(Color.blue);
			this.status.setText("Finished");

		}

	}

	private void init() {
		this.aJPanel = new JPanel();
		this.DensityLabel = new JLabel("specify density");
		this.DensityTextField = new JTextField("1", 5);
		this.RadiusLabel = new JLabel(" specify radius");
		this.RadiusTextField = new JTextField("1", 5);
		this.aJButton = new JButton("Start calculation!");
		this.StepLabel = new JLabel("specify number of steps");
		this.StepTextField = new JTextField("10", 5);
		this.EulerButton = new JRadioButton("Eulers Method");
		this.SimpsonButton = new JRadioButton("Simpsons Method");
		this.progressBar = new JProgressBar();
		this.status = new JLabel();
		this.status.setForeground(Color.green);
		this.status.setText("Ready");

		this.progressBar.setMinimum(0);
		// this.progressBar.setMaximum(100);
		this.progressBar.setValue(0);
		// this.progressBar.setForeground(Color.red);

		this.EulerButton.addActionListener(e -> buttonIsClicked());
		this.SimpsonButton.addActionListener(e -> buttonIsClicked());
		this.aJButton.addActionListener(e -> StartCalculation());

		makeJPanel();

	}

	private void makeJPanel() {

		this.aJPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);
		gc.weightx = 2;
		gc.weighty = 2;
		gc.ipady = 10;
		gc.fill = GridBagConstraints.NONE;

		gc.gridy = 0;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = rightPadding;
		this.aJPanel.add(this.RadiusLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.RadiusTextField, gc);
		gc.gridy++;
		gc.gridx--;
		this.aJPanel.add(this.DensityLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.DensityTextField, gc);
		gc.gridy++;
		gc.gridx--;
		this.aJPanel.add(this.StepLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.StepTextField, gc);
		gc.gridy++;
		gc.gridx--;
		this.aJPanel.add(this.EulerButton, gc);
		gc.gridx++;
		this.aJPanel.add(this.SimpsonButton, gc);
		gc.gridy++;
		gc.gridx--;
		this.aJPanel.add(this.aJButton, gc);
		gc.gridx++;
		this.aJPanel.add(this.progressBar, gc);
		gc.gridy++;
		gc.gridx--;
		this.aJPanel.add(this.status, gc);
	}

	private void constructWindow() {

		setLayout(new FlowLayout());
		add(this.aJPanel);
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth() - (int) screenSize.getWidth() / 3;
		int height = (int) screenSize.getHeight() - (int) screenSize.getHeight();
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

	}

	public void buttonIsClicked() {

		if (this.EulerButton.isSelected()) {
			this.SimpsonButton.setEnabled(false);
		}
		if (this.EulerButton.isSelected() == false) {
			this.SimpsonButton.setEnabled(true);
		}
		if (this.SimpsonButton.isSelected()) {
			S = true;
			this.EulerButton.setEnabled(false);
		}
		if (this.SimpsonButton.isSelected() == false) {
			this.EulerButton.setEnabled(true);
		}

	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new GUIPressure();
			}
		});

	}

}
