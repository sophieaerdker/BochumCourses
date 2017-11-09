package homework5Sophie;

public class FirstTask {

	public static void main(String[] args) {

		PlotAmount reaction1 = new PlotAmount(1.0, 10.0, 0.2, 100.0);
		PlotAmount reaction2 = new PlotAmount(10.0, 1.0, 0.5, 100.0);
		PlotAmount reaction3 = new PlotAmount(1.0, 1.0, 0.1, 10.0);

		reaction1.plotABC();
		reaction2.plotABC();
		reaction3.plotAC(); // amount of B is zero if tauA = tauB

	}

}
