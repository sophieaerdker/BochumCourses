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

import domain.utils.Constants;

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
	private JRadioButton StepButton;
	private JRadioButton StepSizeButton;
	private JRadioButton SolarRadiusButton;
	private JProgressBar progressBar;
	private JLabel status;

	private double r;
	private double rho;
	private int n;
	private double l;
	private boolean E = false;
	private boolean S = false;
	private boolean N = false;
	private boolean L = false;
	private boolean SR = false;

	public GUIPressure() {

		super("Calculating Pressure in a sphere"); // initialize JFrame with a string(title)
		init();
		constructWindow();
	}

	private void StartCalculation() {

		this.E = EulerButton.isSelected();
		this.S = SimpsonButton.isSelected();
		this.N = StepButton.isSelected();
		this.L = StepSizeButton.isSelected();
		this.SR = SolarRadiusButton.isSelected();

		this.progressBar.setValue(0);
		this.progressBar.setMaximum(100);

		if (SR) {
			this.r = Constants.solarRadius * Double.parseDouble(RadiusTextField.getText());
		} else if (SR == false) {
			this.r = Double.parseDouble(RadiusTextField.getText());
		}
		this.rho = Double.parseDouble(DensityTextField.getText());

		if (N) {
			this.n = Integer.parseInt(StepTextField.getText());
		} else if (L) {
			this.l = Double.parseDouble(StepTextField.getText());
		}

		if (this.E == false && this.S == false) {
			System.err.println("No Method has been selected!");

		} else if (this.N == false && this.L == false) {
			System.err.println("No step parameter has been selected!");

		} else {

			System.out.println("Starting calculation...");
			final IntegrationListener listener = this;
			Thread thread = new Thread() {
				public void run() {

					IntegratingPressure integrate = new IntegratingPressure();
					integrate.addListener(listener);

					if (E) {

						if (N) {
							System.out.println("Pressure calculated with Euler Method: "
									+ integrate.EulersMethod(r, rho, n) + " Pa");
						} else if (L) {
							System.out.println("Pressure calculated with Euler Method: "
									+ integrate.EulersMethod(r, rho, l) + " Pa");
						}
					} else if (S) {
						if (N) {
							System.out.println("Pressure calculated with Simpsons Method: "
									+ integrate.SimpsonsMethod(r, rho, n) + " Pa");
						} else if (L) {
							System.out.println("Pressure calculated with Simpsons Method: "
									+ integrate.SimpsonsMethod(r, rho, l) + " Pa");
						}
					}
				}
			};
			thread.start();

		}
	}

	@Override
	public void nextStep(IntegrationEvent event) {
		if (event.isFinished() == false) {
			this.progressBar.setValue(event.getValue());
			this.status.setForeground(Color.red);
			this.status.setText("Calculating Pressure...");
			// System.out.println(event.getValue());
		} else if (event.isFinished()) {
			// System.out.println("Calculation is finished");
			this.status.setForeground(Color.blue);
			this.status.setText("Finished");
			this.progressBar.setValue(100);

		}

	}

	private void init() {
		this.aJPanel = new JPanel();
		this.DensityLabel = new JLabel("density [kg/m^3] :");
		this.DensityTextField = new JTextField("1", 5);
		this.RadiusLabel = new JLabel("radius [m] :");
		this.RadiusTextField = new JTextField("1", 5);
		this.aJButton = new JButton("Start calculation!");
		this.StepLabel = new JLabel("number of steps or stepsize:");
		this.StepTextField = new JTextField("10", 5);
		this.EulerButton = new JRadioButton("Eulers Method");
		this.SimpsonButton = new JRadioButton("Simpsons Method");
		this.StepButton = new JRadioButton("number of steps");
		this.StepSizeButton = new JRadioButton("step size [m]");
		this.SolarRadiusButton = new JRadioButton("unit of radius in solar radii");
		this.progressBar = new JProgressBar();
		this.status = new JLabel();
		this.status.setForeground(Color.green);
		this.status.setText("Ready");

		this.progressBar.setMinimum(0);
		this.progressBar.setValue(0);

		this.EulerButton.addActionListener(e -> buttonIsClicked());
		this.SimpsonButton.addActionListener(e -> buttonIsClicked());
		this.StepButton.addActionListener(e -> buttonIsClicked());
		this.StepSizeButton.addActionListener(e -> buttonIsClicked());
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
		gc.gridx++;
		this.aJPanel.add(this.SolarRadiusButton, gc);
		gc.gridy++;
		gc.gridx--;
		gc.gridx--;
		this.aJPanel.add(this.DensityLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.DensityTextField, gc);
		gc.gridy++;
		gc.gridx--;
		this.aJPanel.add(this.StepButton, gc);
		gc.gridx++;
		this.aJPanel.add(this.StepSizeButton, gc);
		gc.gridx--;
		gc.gridy++;
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
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
			this.progressBar.setValue(0);
		}
		if (this.EulerButton.isSelected() == false) {
			this.SimpsonButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.SimpsonButton.isSelected()) {
			this.EulerButton.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
			this.progressBar.setValue(0);
		}
		if (this.SimpsonButton.isSelected() == false) {
			this.EulerButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.StepButton.isSelected()) {
			this.StepSizeButton.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.StepButton.isSelected() == false) {
			this.StepSizeButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.StepSizeButton.isSelected()) {
			this.StepButton.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.StepSizeButton.isSelected() == false) {
			this.StepButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
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
