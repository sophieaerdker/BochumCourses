package statStarSophie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIStatStar extends JFrame implements IntegrationListener {

	private JPanel aJPanel;
	private JLabel StepLabel;
	private JTextField StepTextField;
	private JRadioButton EulerButton;
	private JRadioButton RKuttaButton;
	private JProgressBar progressBar;
	private JLabel status;
	private JButton StartButton;
	private JComboBox<String> Stars;
	private JLabel StarValues;

	private JTextField Metal;
	private JTextField Hydrogen;
	private JLabel MetalLabel;
	private JLabel HydrogenLabel;
	private JLabel Method;
	private JLabel TemperatureLabel;
	private JLabel LuminosityLabel;
	private JLabel MassLabel;
	private JTextField Temperature;
	private JTextField Luminosity;
	private JTextField Mass;

	private JTextField minMass;
	private JTextField minLuminosity;
	private JTextField minRadius;
	private JLabel minRadiusLabel;
	private JLabel minLuminosityLabel;
	private JLabel minMassLabel;
	private JLabel Termination;

	String[] starList = { "         ", "Sun ", "Alpha Coronae Borealis (A0)", "Beta Pictoris (K0)", "70 Ophiuchi (A5)",
			"61 Cygni (K5)" };

	// input in text fields:
	private int n;
	private double X;
	private double Z;
	private double T_eff;
	private double L_s;
	private double M_s;
	private double M_min;
	private double L_min;
	private double R_min;

	private boolean E = false; // Euler Method
	private boolean RK = false; // Runge-Kutta Method

	public GUIStatStar() {

		super("Calculating a static star"); // initialize JFrame
		init();
		constructWindow();
	}

	private void StartCalculation() { // by pushing the 'Start' button

		this.E = EulerButton.isSelected();
		this.RK = RKuttaButton.isSelected();

		// set progress bar:
		this.progressBar.setValue(0);
		this.progressBar.setMaximum(100);

		this.L_s = Double.parseDouble(Luminosity.getText());
		this.T_eff = Double.parseDouble(Temperature.getText());
		this.Z = Double.parseDouble(Metal.getText());
		this.X = Double.parseDouble(Hydrogen.getText());
		this.M_s = Double.parseDouble(Mass.getText());
		this.n = Integer.parseInt(StepTextField.getText());

		this.R_min = Double.parseDouble(minRadius.getText());
		this.M_min = Double.parseDouble(minMass.getText());
		this.L_min = Double.parseDouble(minLuminosity.getText());

		if (E == false && RK == false) {

			System.err.println("No method has been selected!");

		} else if (M_s == 0 || L_s == 0 || T_eff == 0) {

			System.err.println("Some stellar values are zero!");

		} else if (X == 0 && Z == 0) {

			System.err.println("Some stellar values are zero!");
		} else {

			final IntegrationListener listener = this;
			Thread thread = new Thread() {

				public void run() {

					StatStar star = new StatStar(M_s, L_s, T_eff, X, Z);
					star.addListener(listener);
					star.setL_min(L_min);
					star.setM_min(M_min);
					star.setR_min(R_min);
					star.setN(n);

					int zone = star.Surface();

					if (zone != 0 && E == true) {
						star.mainCalculation(zone);
					} else if (zone != 0 && RK == true) {
						star.RungeKuttaMainCalculation(zone);
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
		this.StepLabel = new JLabel("stepsize [fragment of stellar radius]:");
		this.StepTextField = new JTextField("10000", 5);

		this.EulerButton = new JRadioButton("Eulers Method");
		this.RKuttaButton = new JRadioButton("Runge-Kutta method (RK4)");
		this.StarValues = new JLabel("Choose a given main sequence star and/or set your own values:");

		this.progressBar = new JProgressBar();
		this.status = new JLabel();
		this.StartButton = new JButton("Start calculation!");

		this.Stars = new JComboBox<>(starList);
		this.Stars.setSelectedIndex(1);
		this.Stars.setPreferredSize(new Dimension(150, 40));

		this.Metal = new JTextField("0.01", 5);
		this.Hydrogen = new JTextField("0.74", 5);
		this.MetalLabel = new JLabel("Metal Z");
		this.HydrogenLabel = new JLabel("Hydrogen X");

		this.Method = new JLabel("Set the main calculation method:");
		this.TemperatureLabel = new JLabel("effective Temperature [K]");
		this.Temperature = new JTextField("5770", 5);
		this.LuminosityLabel = new JLabel("Luminosity at the surface [solar luminosity]");
		this.Luminosity = new JTextField("1", 5);
		this.MassLabel = new JLabel("Mass of the star [solar masses]");
		this.Mass = new JTextField("1", 5);

		this.minMass = new JTextField("0.15", 5);
		this.minLuminosity = new JTextField("0.01", 5);
		this.minRadius = new JTextField("0.15", 5);
		this.minRadiusLabel = new JLabel("Center radius [stellar radius]");
		this.minLuminosityLabel = new JLabel("Center luminosity [stellar luminosity]");
		this.minMassLabel = new JLabel("Center mass [stellar mass]");
		this.Termination = new JLabel("Choose the termination conditions:");

		// set status
		this.status.setForeground(Color.green);
		this.status.setText("Ready");

		// set progress bar to zero
		this.progressBar.setMinimum(0);
		this.progressBar.setValue(0);

		// to start the calculation
		this.StartButton.addActionListener(e -> StartCalculation());

		// happens when other buttons are clicked
		this.Stars.addActionListener(e -> selectedStars());
		this.EulerButton.addActionListener(e -> EulerbuttonIsClicked());
		this.RKuttaButton.addActionListener(e -> RKuttabuttonIsClicked());

		this.Temperature.addActionListener(e -> setStatus());
		this.Luminosity.addActionListener(e -> setStatus());
		this.Mass.addActionListener(e -> setStatus());
		this.Hydrogen.addActionListener(e -> setStatus());
		this.Metal.addActionListener(e -> setStatus());
		this.minLuminosity.addActionListener(e -> setStatus());
		this.minRadius.addActionListener(e -> setStatus());
		this.minMass.addActionListener(e -> setStatus());
		this.StepTextField.addActionListener(e -> setStatus());

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

		this.aJPanel.add(this.StarValues, gc);
		gc.gridy++;
		this.aJPanel.add(this.Stars, gc);
		gc.gridx++;
		this.aJPanel.add(this.TemperatureLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Temperature, gc);
		gc.gridx++;
		this.aJPanel.add(this.HydrogenLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Hydrogen, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(this.LuminosityLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Luminosity, gc);
		gc.gridx++;
		this.aJPanel.add(this.MetalLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Metal, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(this.MassLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.Mass, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(this.Termination, gc);
		gc.gridy++;
		this.aJPanel.add(this.minRadiusLabel, gc);
		gc.gridx++;
		gc.gridx++;
		this.aJPanel.add(this.minRadius, gc);
		gc.gridx++;
		this.aJPanel.add(this.minLuminosityLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.minLuminosity, gc);
		gc.gridx++;
		this.aJPanel.add(this.minMassLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.minMass, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(this.Method, gc);
		gc.gridy++;
		this.aJPanel.add(this.EulerButton, gc);
		gc.gridx++;
		gc.gridx++;
		this.aJPanel.add(this.RKuttaButton, gc);
		gc.gridx++;
		this.aJPanel.add(this.StepLabel, gc);
		gc.gridx++;
		this.aJPanel.add(this.StepTextField, gc);
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridx--;
		gc.gridy++;
		this.aJPanel.add(StartButton, gc);
		gc.gridx++;
		gc.gridx++;
		this.aJPanel.add(this.progressBar, gc);
		gc.gridx++;
		this.aJPanel.add(this.status, gc);

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

	public void selectedStars() {
		// System.out.println(this.Stars.getSelectedIndex());

		setStatus();
		int index = this.Stars.getSelectedIndex();

		if (index == 1) {

			// sun
			this.Temperature.setText("5770");
			this.Luminosity.setText("1");
			this.Mass.setText("1");
			this.Hydrogen.setText("0.74");
			this.Metal.setText("0.01");
			this.minLuminosity.setText("0.01");
			this.minRadius.setText("0.15");
			this.minMass.setText("0.15");
			this.StepTextField.setText("10000");

		} else if (index == 0) {

			this.Temperature.setText("  ");
			this.Luminosity.setText("  ");
			this.Mass.setText("  ");
			this.Hydrogen.setText("  ");
			this.Metal.setText("  ");
			this.minLuminosity.setText("  ");
			this.minRadius.setText("  ");
			this.minMass.setText("  ");
			this.StepTextField.setText("  ");

		} else if (index == 2) {

			this.Temperature.setText("9700");
			this.Luminosity.setText("74");
			this.Mass.setText("2.58");
			this.Hydrogen.setText("0.7");
			this.Metal.setText("0.008");
			this.minLuminosity.setText("0.1");
			this.minRadius.setText("0.1");
			this.minMass.setText("0.1");
			this.StepTextField.setText("10000");
		} else if (index == 3) {

			this.Temperature.setText("8052");
			this.Luminosity.setText("8.7");
			this.Mass.setText("1.75");
			this.Hydrogen.setText("0.7");
			this.Metal.setText("0.02");
			this.minLuminosity.setText("0.1");
			this.minRadius.setText("0.1");
			this.minMass.setText("0.1");
			this.StepTextField.setText("10000");
		} else if (index == 4) {

			this.Temperature.setText("5300");
			this.Luminosity.setText("0.54");
			this.Mass.setText("0.9");
			this.Hydrogen.setText("0.72");
			this.Metal.setText("0.01");
			this.minLuminosity.setText("0.01");
			this.minRadius.setText("0.15");
			this.minMass.setText("0.2");
			this.StepTextField.setText("10000");
		} else if (index == 5) {

			this.Temperature.setText("4410");
			this.Luminosity.setText("0.16");
			this.Mass.setText("0.68");
			this.Hydrogen.setText("0.7");
			this.Metal.setText("0.004");
			this.minLuminosity.setText("0.01");
			this.minRadius.setText("0.2");
			this.minMass.setText("0.2");
			this.StepTextField.setText("10000");
		}
	}

	public void EulerbuttonIsClicked() {

		// if Euler is selected, Simpsons method is disabled ...
		// -> you can only calculate one method
		// the status and progress bar are set to default values
		// -> Calculation is ready

		setStatus();

		if (this.EulerButton.isSelected()) {
			this.RKuttaButton.setSelected(false);
		}

	}

	public void setStatus() {

		this.status.setForeground(Color.green);
		this.status.setText("Ready");
		this.progressBar.setValue(0);

	}

	public void RKuttabuttonIsClicked() {

		setStatus();

		if (this.RKuttaButton.isSelected()) {
			this.EulerButton.setSelected(false);
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
				new GUIStatStar();
			}
		});

	}

}
