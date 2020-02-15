import java.util.*;

public class Solver {
	private EquationSystem es;

	public Solver(EquationSystem es) {
		this.es = es;
	}

	public static void stop() {
		running = false;
	}

	private static boolean running = true;

	private static void goOn() throws InterruptedException {
		if (!running) {
			running = true;
			throw new InterruptedException("Calculation aborted");
		}
	}

	public void solve(double[] x) throws InterruptedException {
		if (es.number() == 0)
			return;

		list.add(new Point(x));
		while (list.get(0).getM() > 1.0) {
			goOn();

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
				double[][] Mm = new double[es.number()][es.number()];
				for (int j = 0; j < es.number(); j++)
					for (int k = 0; k < es.number(); k++)
						Mm[j][k] = list.get(k).getF(j) - list.get(es.number()).getF(j);
				double[] mF0 = new double[es.number()];
				for (int j = 0; j < es.number(); j++)
					mF0[j] = -list.get(es.number()).getF(j);
				double[] u = Linalg.solve(Mm, mF0);
				double[][] Vm = new double[es.number()][es.number()];
				for (int j = 0; j < es.number(); j++)
					for (int k = 0; k < es.number(); k++)
						Vm[j][k] = list.get(k).getIndex(j) - list.get(es.number()).getIndex(j);
				double[] delta = Linalg.prod(Vm, u);
				for (int j = 0; j < es.number(); j++)
					x[j] = list.get(es.number()).getIndex(j) + delta[j];
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

		public Point(double[] x) {

			this.x = x;
			f = new double[es.number()];
			try {
				es.equations(x, f);
			} catch (Exception ex) {
				f[0] = 10000.0;
			}

			m = 0.0;
			for (int j = 0; j < es.number(); j++) {
				double mm = Math.abs(f[j]);
				if (mm > m)
					m = mm;
			}
		}

		public double getM() {
			return m;
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
