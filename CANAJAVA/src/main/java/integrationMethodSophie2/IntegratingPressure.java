package integrationMethodSophie2;

import java.util.ArrayList;
import java.util.List;

import domain.utils.Constants;

public class IntegratingPressure {

	private List<IntegrationListener> myListeners;

	public IntegratingPressure() {
		this.myListeners = new ArrayList<>();
	}

	public double EulersMethod(double r, double rho, int n) {

		double deltaR = r / n;
		double P = 0;
		double c = 4 * Math.PI / 3 * Constants.gravitationalConstant * rho * rho;

		for (int i = 0; i < n + 1; i++) {

			P += c * r * deltaR;

			r -= deltaR;
			notifyMyListeners(i, false);

		}

		notifyMyListeners(n, true);
		return P;
	}

	double SimpsonsMethod(double r, double rho, int n) {

		double P = 0;
		double deltaR = r / n;
		double c = 4 * Math.PI / 3 * Constants.gravitationalConstant * rho * rho;

		for (int i = 0; i < n + 1; i++) {

			if (i % 2 == 0) {

				P += 2 * c * (r - i * deltaR);

				notifyMyListeners(i, false);

			} else {

				P += 4 * c * (r - i * deltaR);

				notifyMyListeners(i, false);
			}
		}

		P = P * deltaR / 3;

		notifyMyListeners(n, true);
		return P;
	}

	private void notifyMyListeners(int value, boolean finished) {

		IntegrationEvent event = new IntegrationEvent(value, finished);

		for (IntegrationListener IntegrationListener : myListeners) {
			IntegrationListener iEventListener = IntegrationListener;
			iEventListener.nextStep(event);
		}

	}

	public void addListener(IntegrationListener listener) {

		myListeners.add(listener);

	}

	public void removeListener(IntegrationListener listener) {

		myListeners.remove(listener);

	}

}
