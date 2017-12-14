package integrationMethodSophie3;

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
	private JLabel Calculation;
	private JRadioButton CalculatingPressure;
	private JRadioButton CalculatingTemperature;
	private JRadioButton SolarDensityButton;
	private JTextField Metal;
	private JTextField Hydrogen;
	private JLabel MetalLabel;
	private JLabel HydrogenLabel;
	private JLabel MetalHydrogen;
	private JLabel Method;
	private JLabel TemperatureLabel;
	private JLabel LuminosityLabel;
	private JTextField Temperature;
	private JTextField Luminosity;
	private JRadioButton SolarLuminosityButton;

	// input in text fields:
	private double r;
	private double rho;
	private int n;
	private double l;
	private double X;
	private double Z;
	private double T_0;
	private double L_0;

	private boolean T = false; // Calculation of Temperature/Pressure
	private boolean P = false;
	private boolean E = false; // Euler Method
	private boolean S = false; // Simspons Method
	private boolean N = false; // step size
	private boolean L = false; // step length
	private boolean SR = false; // radius in solar radii
	private boolean SD = false; // density in average solar density
	private boolean SL = false; // luminosity in solar luminosity

	public GUIPressure() {

		super("Calculating Pressure and Temperature in the center of a sphere"); // initialize JFrame
		init();
		constructWindow();
	}

	private void StartCalculation() { // by pushing the 'Start' button

		this.T = CalculatingTemperature.isSelected();
		this.P = CalculatingPressure.isSelected();
		this.E = EulerButton.isSelected();
		this.S = SimpsonButton.isSelected();
		this.N = StepButton.isSelected();
		this.L = StepSizeButton.isSelected();
		this.SR = SolarRadiusButton.isSelected();
		this.SD = SolarDensityButton.isSelected();
		this.SL = SolarLuminosityButton.isSelected();

		// set progress bar:
		this.progressBar.setValue(0);
		this.progressBar.setMaximum(100);

		// input in solar units?
		if (SR) {
			this.r = Constants.solarRadius * Double.parseDouble(RadiusTextField.getText());
		} else if (SR == false) {
			this.r = Double.parseDouble(RadiusTextField.getText());
		}
		if (SD) {
			this.rho = 1.408 * 1000 * Double.parseDouble(RadiusTextField.getText());
		} else if (SR == false) {
			this.rho = Double.parseDouble(RadiusTextField.getText());
		}

		if (N) {
			this.n = Integer.parseInt(StepTextField.getText());
		} else if (L) {
			this.l = Double.parseDouble(StepTextField.getText());
		}

		if (SL) {
			this.L_0 = 3.828 * 1e26 * Double.parseDouble(Luminosity.getText());
		} else if (SL == false) {
			this.L_0 = Double.parseDouble(Luminosity.getText());
		}

		this.T_0 = Double.parseDouble(Temperature.getText());
		this.Z = Double.parseDouble(Metal.getText());
		this.X = Double.parseDouble(Hydrogen.getText());

		// errors or start of calculation:
		if (this.E == false && this.S == false) {
			System.err.println("No Method has been selected!");

		} else if (this.N == false && this.L == false) {
			System.err.println("No step parameter has been selected!");

		} else if (this.P == false && this.T == false) {
			System.err.println("Select Pressure or Temperature!");

		} else if (this.P) {

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

		else {

			System.out.println("Starting calculation...");
			final IntegrationListener listener = this;
			Thread thread = new Thread() {

				public void run() {

					// choose 'IntegrateTemperature' to calculate the Temperature with an average
					// density
					// choose 'IntegrateTemperature2' to calculate the Temperature with a density
					// depending on the radius/pressure in the sphere

					IntegratingTemperature integrate = new IntegratingTemperature();
					// IntegratingTemperature2 integrate = new IntegratingTemperature2();
					integrate.addListener(listener);
					integrate.setX(X);
					integrate.setZ(Z);
					integrate.setL_0(L_0);
					integrate.setT_0(T_0);

					if (E) {

						if (N && T) {

							System.out.println("Temperature calculated with Euler Method: "
									+ integrate.EulersMethod(r, rho, n) + " K");
						} else if (L && T) {

							System.out.println("Temperature calculated with Euler Method: "
									+ integrate.EulersMethod(r, rho, l) + " K");
						}

					} else if (S) {

						if (N && T) {

							System.out.println("Temperature calculated with Simpsons Method: "
									+ integrate.SimpsonsMethod(r, rho, n) + " K");
						} else if (L && T) {

							System.out.println("Temperature calculated with Simpsons Method: "
									+ integrate.SimpsonsMethod(r, rho, l) + " K");
						}
					}
				}

			};
			thread.start();

		}

	}

	@Override
	public void nextStep(IntegrationEvent event) {

		// integration listener: update progress bar and status:
		if (event.isFinished() == false) {
			this.progressBar.setValue(event.getValue());
			this.status.setForeground(Color.red);
			this.status.setText("Calculating...");
		} else if (event.isFinished()) {
			this.status.setForeground(Color.blue);
			this.status.setText("Finished");
			this.progressBar.setValue(100);

		}

	}

	private void init() {

		// creation of text fields and buttons
		// default settings are solar parameters

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
		this.SolarRadiusButton = new JRadioButton("unit in solar radii");
		this.progressBar = new JProgressBar();
		this.status = new JLabel();
		this.Calculation = new JLabel("Calculation of:");
		this.CalculatingPressure = new JRadioButton("Pressure");
		this.CalculatingTemperature = new JRadioButton("Temperature");
		this.SolarDensityButton = new JRadioButton("unit in average solar density");
		this.Metal = new JTextField("0.0122");
		this.Hydrogen = new JTextField("0.7381");
		this.MetalLabel = new JLabel("Metal Z");
		this.HydrogenLabel = new JLabel("Hydrogen X");
		this.MetalHydrogen = new JLabel("For Calculation of Temperature:");
		this.Method = new JLabel("Integration Method:");
		this.TemperatureLabel = new JLabel("effective Temperature [K]");
		this.Temperature = new JTextField("5770");
		this.LuminosityLabel = new JLabel("Luminosity at the surface [W]");
		this.Luminosity = new JTextField("1");
		this.SolarLuminosityButton = new JRadioButton("unit in solar Luminosity");

		// set status
		this.status.setForeground(Color.green);
		this.status.setText("Ready");

		// set progress bar to zero
		this.progressBar.setMinimum(0);
		this.progressBar.setValue(0);

		this.SolarDensityButton.setSelected(true);
		this.SolarRadiusButton.setSelected(true);
		this.SolarLuminosityButton.setSelected(true);

		// happens when the buttons are clicked
		this.EulerButton.addActionListener(e -> buttonIsClicked());
		this.SimpsonButton.addActionListener(e -> buttonIsClicked());
		this.StepButton.addActionListener(e -> buttonIsClicked());
		this.StepSizeButton.addActionListener(e -> buttonIsClicked());
		this.aJButton.addActionListener(e -> StartCalculation());
		this.CalculatingPressure.addActionListener(e -> buttonIsClicked());
		this.CalculatingTemperature.addActionListener(e -> buttonIsClicked());

		makeJPanel();

	}

	private void makeJPanel() {

		// construction of the JPanel

		this.aJPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);
		gc.weightx = 3;
		gc.weighty = 3;
		gc.ipady = 20;
		gc.fill = GridBagConstraints.NONE;

		gc.gridy = 0;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = rightPadding;

		this.aJPanel.add(this.Calculation, gc);
		gc.gridx++;
		this.aJPanel.add(this.CalculatingPressure, gc);
		gc.gridx++;
		this.aJPanel.add(this.CalculatingTemperature, gc);
		gc.gridx++;
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		gc.gridy++;
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
		gc.gridx++;
		this.aJPanel.add(this.SolarDensityButton, gc);
		gc.gridy++;
		gc.gridx--;
		gc.gridx--;
		this.aJPanel.add(this.StepLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.StepTextField, gc);
		gc.gridx++;
		this.aJPanel.add(this.StepButton, gc);
		gc.gridx++;
		this.aJPanel.add(this.StepSizeButton, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(this.MetalHydrogen, gc);
		gc.gridy++;
		gc.gridy++;
		this.aJPanel.add(this.TemperatureLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Temperature, gc);
		gc.gridx++;
		this.aJPanel.add(this.LuminosityLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Luminosity, gc);
		gc.gridx++;
		this.aJPanel.add(this.SolarLuminosityButton, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(this.MetalLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Metal, gc);
		gc.gridx++;
		this.aJPanel.add(this.HydrogenLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Hydrogen, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(this.Method, gc);
		gc.gridy++;
		this.aJPanel.add(this.EulerButton, gc);
		gc.gridx++;
		this.aJPanel.add(this.SimpsonButton, gc);
		gc.gridy++;
		gc.gridx--;
		this.aJPanel.add(this.aJButton, gc);
		gc.gridx++;
		this.aJPanel.add(this.progressBar, gc);
		gc.gridx++;
		this.aJPanel.add(this.status, gc);
		gc.gridy++;

	}

	private void constructWindow() {

		setLayout(new FlowLayout());
		add(this.aJPanel);
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth() - (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight() - (int) screenSize.getHeight();
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

	}

	public void buttonIsClicked() {

		// if Euler is selected, Simpsons method is disabled ...
		// -> you can only calculate one method
		// the status and progress bar are set to default values
		// -> Calculation is ready

		if (this.EulerButton.isSelected()) {
			this.SimpsonButton.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
			this.progressBar.setValue(0);
		} else if (this.EulerButton.isSelected() == false) {
			this.SimpsonButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.SimpsonButton.isSelected()) {
			this.EulerButton.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
			this.progressBar.setValue(0);
		} else if (this.SimpsonButton.isSelected() == false) {
			this.EulerButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.StepButton.isSelected()) {
			this.StepSizeButton.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		} else if (this.StepButton.isSelected() == false) {
			this.StepSizeButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.StepSizeButton.isSelected()) {
			this.StepButton.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		} else if (this.StepSizeButton.isSelected() == false) {
			this.StepButton.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}
		if (this.CalculatingPressure.isSelected()) {
			this.CalculatingTemperature.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
			this.progressBar.setValue(0);
		} else if (this.CalculatingPressure.isSelected() == false) {
			this.CalculatingTemperature.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}

		if (this.CalculatingTemperature.isSelected()) {
			this.CalculatingPressure.setEnabled(false);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
			this.progressBar.setValue(0);
		} else if (this.CalculatingTemperature.isSelected() == false) {
			this.CalculatingPressure.setEnabled(true);
			this.status.setForeground(Color.green);
			this.status.setText("Ready");
		}

	}

	public static void main(String[] args) {

		// run the GUI:

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
