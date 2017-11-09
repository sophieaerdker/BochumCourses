package homework5Sophie;

public class ReactionChain {

	// for reaction A -> B -> C or A -> B, if B is stable choose tauB = 0

	private double A_0;
	private double lambdaA;
	private double lambdaB;

	// The amounts of A, B, C derived by solving the DGLs:
	// dA/dt = -lambda_A * A
	// dB/dt = lambda_A * A - lambda_B * B
	// dC/dt = lambda * B
	// and the condition: dA + dB + dC = 0 with A(0) = A_0, B(0) = C(0) = 0

	public double amountA(double t) {
		// returns amount of A after time t
		return A_0 * Math.exp(-lambdaA * t);
	}

	public double amountB(double t) {
		// returns amount of B after time t
		// If tauA = tauB, there is no B because all produced B decays directly to C
		// and the effective reaction is A -> C
		if (lambdaA != lambdaB) {
			return A_0 * lambdaA / (lambdaB - lambdaA) * (Math.exp(-lambdaA * t) - Math.exp(-lambdaB * t));
		} else {
			return 0;
		}
	}

	public double amountC(double t) {
		// returns amount of C after time t
		// If tauA = tauB, there is no B because all produced B decays directly to C
		// and the effective reaction is A -> C
		if (lambdaA != lambdaB) {
			return A_0 * (1
					- 1 / (lambdaB - lambdaA) * (lambdaB * Math.exp(-lambdaA * t) - lambdaA * Math.exp(-lambdaB * t)));
		} else {
			return A_0 * (1 - Math.exp(-lambdaA * t));
		}

	}

	public ReactionChain(double A_0, double tauA, double tauB) {

		this.A_0 = A_0;
		this.lambdaA = Math.log(2) / tauA;
		if (tauB != 0) {
			this.lambdaB = Math.log(2) / tauB;
		} else if (tauB == 0) {
			// tauB = 0 -> B is stable -> lambda = 0
			lambdaB = 0;
		}
	}

	public ReactionChain(double tauA, double tauB) {

		// If A_0 is not defined the relative amounts of A, B and C are calculated:
		this.lambdaA = Math.log(2) / tauA;
		if (tauB != 0) {
			this.lambdaB = Math.log(2) / tauB;
		} else if (tauB == 0) {
			// tauB = 0 -> B is stable -> lambda = 0
			lambdaB = 0;
		}
		this.A_0 = 1;

	}
}
