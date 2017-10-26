package thirdclass;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

import domain.utils.Constants;

public class ThirdClassHomeworkSophieAerdker {

	public static void main(String[] args) {
		GraphErrors gr = new GraphErrors();

		// gr.addPoint(1, 2, 0.5, 0.5);
		// gr.addPoint(2, 3, 0.25, 0.3);

		for (int i = 1; i < 21; i++) {
			double r = OnlyGravitationalHeating.radius(i) / Constants.solarRadius;
			gr.addPoint(i, r, 0, 0);
		}

		gr.setTitle("Solar radius with only gravitational heating");
		gr.setTitleX("Age of the sun [solar age]");
		gr.setTitleY("Radius of the sun [solar radius]");
		gr.setMarkerColor(4);
		gr.setMarkerStyle(2);

		TCanvas canvas = new TCanvas("Only gravitational heating", 800, 800);
		canvas.draw(gr);
		// SaveCanvas.saveCanvas(canvas);
	}

}
