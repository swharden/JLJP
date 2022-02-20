public class Linalg {

	public static double[] solve(double[][] Mv, double[] vv) {

		// copy
		double[][] M = new double[Mv.length][Mv[0].length];
		double[] v = new double[vv.length];
		for (int n = 0; n < M.length; n++)
			for (int m = 0; m < M[0].length; m++)
				M[n][m] = Mv[n][m];
		for (int n = 0; n < v.length; n++)
			v[n] = vv[n];

		// row reduce
		int n = 0, m = 0; // row, column
		while ((n < M.length) && (m < M[0].length)) {

			// look for the largest number in the column m
			double maxv = 0.;
			int maxn = -10;
			for (int tn = n; tn < M.length; tn++)
				if (Math.abs(M[tn][m]) > maxv) {
					maxv = Math.abs(M[tn][m]);
					maxn = tn;
				}
			if (maxn < 0) {
				m++;
				continue;
			}

			// order
			if (maxn != n) {
				for (int j = m; j < M[0].length; j++) {
					double h = M[n][j];
					M[n][j] = M[maxn][j];
					M[maxn][j] = h;
				}
				double h = v[n];
				v[n] = v[maxn];
				v[maxn] = h;
			}

			// normalize
			for (int j = m + 1; j < M[0].length; j++)
				M[n][j] /= M[n][m];
			v[n] /= M[n][m];
			M[n][m] = 1.;

			// reduce
			for (int tn = 0; tn < M.length; tn++)
				if (tn != n) {
					for (int j = m + 1; j < M[0].length; j++)
						M[tn][j] -= M[n][j] * M[tn][m];
					v[tn] -= v[n] * M[tn][m];
					M[tn][m] = 0.;
				}

			n++;
			m++;
		}

		// solution
		double[] x = new double[M[0].length];
		for (int j = 0; j < x.length; j++)
			x[j] = 0.;
		n = 0;
		m = 0;
		while ((n < M.length) && (m < M[0].length)) {

			// look for the first 1
			if (M[n][m] == 0) {
				m++;
				continue;
			}

			// set value
			x[m] = v[n];

			n++;
			m++;
		}

		return x;

	}

	public static double[] prod(double[][] A, double[] v) {
		double[] r = new double[A.length];
		assert A[0].length == v.length : "prod: wrong length\n";
		for (int n = 0; n < r.length; n++) {
			r[n] = 0.;
			for (int j = 0; j < A[0].length; j++)
				r[n] += A[n][j] * v[j];
		}
		return r;
	}

	public static double[][] identity(int num) {
		double[][] r = new double[num][num];
		for (int j = 0; j < num; j++)
			for (int k = 0; k < num; k++)
				r[j][k] = (j == k) ? 1. : 0.;
		return r;
	}

	public static double scalarProd(double[] a, double[] b) {
		double r = 0.;
		for (int n = 0; n < a.length; n++)
			r += a[n] * b[n];
		return r;
	}

	public static double[] scalarMultiply(double[] a, double b) {
		double[] r = new double[a.length];
		for (int n = 0; n < r.length; n++)
			r[n] = a[n] * b;
		return r;
	}

	public static double[][] prod(double[][] A, double[][] B) {
		double[][] R = new double[A.length][B[0].length];
		assert A[0].length == B.length : "prod: wrong length\n";
		for (int n = 0; n < R.length; n++)
			for (int m = 0; m < R[0].length; m++) {
				R[n][m] = 0.;
				for (int j = 0; j < A[0].length; j++)
					R[n][m] += A[n][j] * B[j][m];
			}
		return R;
	}

	public static double[][] transpose(double[][] M) {
		double[][] R = new double[M[0].length][M.length];
		for (int n = 0; n < R.length; n++)
			for (int m = 0; m < R[0].length; m++)
				R[n][m] = M[m][n];
		return R;
	}

	public static double[] sum(double a, double A[], double b, double B[]) {
		double[] R = new double[A.length];
		assert A.length == B.length : "sum: wrong length\n";
		for (int n = 0; n < R.length; n++)
			R[n] = a * A[n] + b * B[n];
		return R;
	}

	public static double[][] sum(double a, double A[][], double b, double B[][]) {
		double[][] R = new double[A.length][A[0].length];
		assert A.length == B.length : "sum: wrong length 1\n";
		assert A[0].length == B[0].length : "sum: wrong length 2\n";
		for (int n = 0; n < R.length; n++)
			for (int m = 0; m < R[0].length; m++)
				R[n][m] = a * A[n][m] + b * B[n][m];
		return R;
	}

	public static void main(String[] args) {

		double M[][] = new double[10][10];
		for (int n = 0; n < 10; n++)
			for (int m = 0; m < 10; m++)
				M[n][m] = Math.random() * 2. - 1.;

		double v[] = new double[10];
		for (int n = 0; n < 10; n++)
			v[n] = Math.random() * 2. - 1.;

		double[] x = solve(M, v);
		double[] t = prod(M, x);

		System.out.println("# The following columns should be equal");
		for (int n = 0; n < 10; n++)
			System.out.println("" + t[n] + " " + v[n]);

	}

}
