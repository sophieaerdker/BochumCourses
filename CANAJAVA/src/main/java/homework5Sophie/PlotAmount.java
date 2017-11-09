package homework5Sophie;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

import domain.utils.SaveCanvas;

public class PlotAmount {

	private double tauA; // in min
	private double tauB;
	private double deltaT; // min
	private double limitT;
	private int x; // save canvas? 1 = yes, 0 = no

	public void plotABC() {

		GraphErrors grA1 = new GraphErrors();
		GraphErrors grB1 = new GraphErrors();
		GraphErrors grC1 = new GraphErrors();

		grB1.setTitleX("Time t [min]");
		grB1.setTitleY("Relative Amount of A (blue), B (red) and C (green) [%]");
		grB1.setMarkerColor(2);
		grC1.setMarkerColor(3);
		grA1.setMarkerColor(4);
		grB1.setMarkerSize(3);
		grC1.setMarkerSize(3);
		grA1.setMarkerSize(3);

		ReactionChain reaction1 = new ReactionChain(this.tauA, this.tauB);

		for (double t = 0; t < limitT; t += deltaT) {
			double B = 100 * reaction1.amountB(t);
			double C = 100 * reaction1.amountC(t);
			double A = 100 * reaction1.amountA(t);
			grB1.addPoint(t, B, 0, 0);
			grC1.addPoint(t, C, 0, 0);
			grA1.addPoint(t, A, 0, 0);
		}

		grB1.setTitle("Reaction A -> B -> C with half-life A = " + tauA + "min and half-life B = " + tauB + "min");

		TCanvas canvas1 = new TCanvas("Reaction Chain A -> B -> C", 800, 800);
		canvas1.draw(grB1);
		canvas1.draw(grC1, "same");
		canvas1.draw(grA1, "same");
		if (x == 1) {
			SaveCanvas.saveCanvas(canvas1);
		}
	}

	public void plotAC() {

		GraphErrors grA1 = new GraphErrors();
		GraphErrors grC1 = new GraphErrors();

		grA1.setTitleX("Time t [min]");
		grA1.setTitleY("Relative Amount of A (blue) and C (green) [%]");
		grC1.setMarkerColor(3);
		grA1.setMarkerColor(4);
		grC1.setMarkerSize(3);
		grA1.setMarkerSize(3);

		ReactionChain reaction1 = new ReactionChain(this.tauA, this.tauB);

		for (double t = 0; t < limitT; t += deltaT) {
			double C = 100 * reaction1.amountC(t);
			double A = 100 * reaction1.amountA(t);
			grC1.addPoint(t, C, 0, 0);
			grA1.addPoint(t, A, 0, 0);
		}

		grA1.setTitle("Reaction A -> B -> C with half-life A = " + tauA + "min and half-life B = " + tauB + "min");

		TCanvas canvas1 = new TCanvas("Reaction Chain A -> B -> C", 800, 800);
		canvas1.draw(grA1);
		canvas1.draw(grC1, "same");
		if (x == 1) {
			SaveCanvas.saveCanvas(canvas1);
		}
	}

	public void plotAB() {

		GraphErrors grA1 = new GraphErrors();
		GraphErrors grB1 = new GraphErrors();

		grA1.setTitleX("Time t [min]");
		grA1.setTitleY("Relative Amount of A (blue) and B (red) [%]");
		grB1.setMarkerColor(2);
		grA1.setMarkerColor(4);
		grB1.setMarkerSize(3);
		grA1.setMarkerSize(3);

		ReactionChain reaction1 = new ReactionChain(this.tauA, this.tauB);

		for (double t = 0; t < limitT; t += deltaT) {
			double B = 100 * reaction1.amountB(t);
			double A = 100 * reaction1.amountA(t);
			grB1.addPoint(t, B, 0, 0);
			grA1.addPoint(t, A, 0, 0);
		}

		grA1.setTitle("Reaction A -> B -> C with half-life A = " + tauA + "min and half-life B = " + tauB + "min");

		TCanvas canvas1 = new TCanvas("Reaction Chain A -> B -> C", 800, 800);
		canvas1.draw(grA1);
		canvas1.draw(grB1, "same");
		if (x == 1) {
			SaveCanvas.saveCanvas(canvas1);
		}
	}

	public PlotAmount(double tauA, double tauB, double deltaT, double limitT, int x) {

		this.tauA = tauA;
		this.tauB = tauB;
		this.deltaT = deltaT;
		this.limitT = limitT;
		this.x = x;
	}

	public PlotAmount(double tauA, double tauB, double deltaT, double limitT) {

		this.tauA = tauA;
		this.tauB = tauB;
		this.deltaT = deltaT;
		this.limitT = limitT;
		this.x = 0;
	}

}
