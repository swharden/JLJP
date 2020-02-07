import java.util.*;
import JSci.maths.matrices.*;
import JSci.maths.vectors.*;
import JSci.maths.symbolic.*;
import JSci.maths.fields.*;
import JSci.maths.*;

/** Numeric solver for sets of equations */
public class Solver {
	private EquationSystem es;

	/**
	 * Creates the solver.
	 * 
	 * @param es the system of equations that must be solved.
	 */
	public Solver(EquationSystem es) {
		this.es = es;
	}

	/** Stop all the ongoing calculations. */
	public static void stop() {
		running = false;
	}

	// needed by stop()
	private static boolean running = true;

	private static void goOn() throws InterruptedException {
		if (!running) {
			running = true;
			throw new InterruptedException("Calculation aborted");
		}
	}

	/**
	 * Solve the system. The initial guess and the result are in the same array.
	 * 
	 * @param x the solution.
	 */
	public void solve(double[] x) throws InterruptedException {
		if (es.number() == 0)
			return;

		int tot = 0;

		list.add(new Point(x));
		while (list.get(0).getM() > 1.0) {
			goOn();

			// System.out.println(""+tot+" : "+list.get(0).getM());
			// tot++;
			// if (tot==2000) {
			// for (int j=0;j<list.size();j++)
			// System.out.println(""+list.get(j).getIndex(0)+" "+list.get(j).getIndex(1)+"
			// "+list.get(j).getM()+" "+list.get(j).getF(0)+" "+list.get(j).getF(1));
			// System.exit(0);
			// }

			suggest();
			Collections.sort(list);
			while (list.size() > es.number() * 4)
				list.remove(list.size() - 1);
		}
		for (int j = 0; j < es.number(); j++)
			x[j] = list.get(0).getIndex(j);
	}

	private ArrayList<Point> list = new ArrayList<Point>();

	private int sn = 0;

	private void suggest() {
		double[] x = new double[es.number()];
		switch (sn) {
		case 0:
			if (list.size() >= es.number() + 1) {
				DoubleSquareMatrix Mm = new DoubleSquareMatrix(es.number());
				for (int j = 0; j < es.number(); j++)
					for (int k = 0; k < es.number(); k++)
						Mm.setElement(j, k, list.get(k).getF(j) - list.get(es.number()).getF(j));
				DoubleVector mF0 = new DoubleVector(es.number());
				for (int j = 0; j < es.number(); j++)
					mF0.setComponent(j, -list.get(es.number()).getF(j));
				AbstractDoubleVector u = LinearMath.solve(Mm, mF0);
				DoubleSquareMatrix Vm = new DoubleSquareMatrix(es.number());
				for (int j = 0; j < es.number(); j++)
					for (int k = 0; k < es.number(); k++)
						Vm.setElement(j, k, list.get(k).getIndex(j) - list.get(es.number()).getIndex(j));
				AbstractDoubleVector delta = Vm.multiply(u);
				for (int j = 0; j < es.number(); j++)
					x[j] = list.get(es.number()).getIndex(j) + delta.getComponent(j);
				break;
			}
		case 1:
			for (int j = 0; j < es.number(); j++)
				x[j] = list.get(0).getIndex(j) * (Math.random() - 0.5) * 4.0;
			break;
		case 2:
			for (int j = 0; j < es.number(); j++)
				x[j] = (Math.random() - 0.5) * 4.0;
			break;
		case 3:
			for (int j = 0; j < es.number(); j++) {
				double min, max;
				min = max = list.get(0).getIndex(j);
				for (int k = 0; k < list.size(); k++) {
					double v = list.get(k).getIndex(j);
					if (v < min)
						min = v;
					if (v > max)
						max = v;
				}
				if (min == max) {
					if (min > 0.0) {
						min *= 0.8;
						max *= 1.2;
					} else if (min < 0.0) {
						min *= 1.2;
						max *= 0.8;
					} else {
						min = -1.0;
						max = 1.0;
					}
				}
				x[j] = (min + max) / 2.0 + (max - min) * (Math.random() - 0.5) * 3.0;
			}
			break;
		}
		list.add(new Point(x));
		sn++;
		if (sn > 3)
			sn = 0;
	}

	private class Point implements Comparable<Point> {
		private double[] x;
		private double[] f;
		private double m;
		private double rms;

		public Point() {
		}

		public Point(double[] x) {
			this.x = x;
			f = new double[es.number()];
			try {
				es.equations(x, f);
			} catch (Exception ex) {
				f[0] = 10000.0;
			}
			m = rms = 0.0;
			for (int j = 0; j < es.number(); j++) {
				double mm = Math.abs(f[j]);
				if (mm > m)
					m = mm;
				rms += mm * mm;
			}
		}

		public double getM() {
			return m;
		}

		public double getRms() {
			return rms;
		}

		public double getIndex(int n) {
			return x[n];
		}

		public double getF(int n) {
			return f[n];
		}

		public int compareTo(Point p) {
			if (getM() < p.getM())
				return -1;
			if (getM() > p.getM())
				return 1;
			return 0;
		}

	}

}