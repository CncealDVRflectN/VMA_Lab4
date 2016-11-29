package by.vma.lab4;

public class Main {
    private static class Matrix {
        public double[][] matrix;
        private int lines;
        private int columns;

        public Matrix(int lines, int columns) throws Exception {
            if (lines < 1 || columns < 1) {
                throw new Exception("Неверный размер.");
            }
            this.lines = lines;
            this.columns = columns;
            this.matrix = new double[lines][columns];
        }

        public void fillDefault() {
            double[][] a = {{0.6444, 0.0000, -0.1683, 0.1184, 0.1973},
                    {-0.0395, 0.4208, 0.0000, -0.0802, 0.0263},
                    {0.0132, -0.1184, 0.7627, 0.0145, 0.0460},
                    {0.0395, 0.0000, -0.0960, 0.7627, 0.0000},
                    {0.0263, -0.0395, 0.1907, -0.0158, 0.5523}};
            this.lines = 5;
            this.columns = 5;
            this.matrix = a;
        }

        public Vector mul(Vector vector) throws Exception {
            if (columns != vector.getLength()) {
                throw new Exception("Неверная матрица или вектор.");
            }
            Vector result = new Vector(vector.getLength());
            for (int i = 0; i < lines; i++) {
                result.vector[i] = 0;
                for (int j = 0; j < columns; j++) {
                    result.vector[i] += matrix[i][j] * vector.vector[j];
                }
            }
            return result;
        }
    }

    private static class Vector {
        public double[] vector;
        private int length;

        public Vector(int length) throws Exception {
            if (length < 1) {
                throw new Exception("Неверный размер.");
            }
            this.length = length;
            vector = new double[length];
        }

        public int getLength() {
            return length;
        }

        public void print(boolean exponent) {
            for (double item : vector) {
                if (exponent) {
                    System.out.printf("%e\n", item);
                } else {
                    System.out.printf("%.5f\n", item);
                }
            }
        }

        public void fillDefault() {
            double[] b = {1.2677, 1.6819, -2.3657, -6.5369, 2.8351};
            this.length = 5;
            this.vector = b;
        }

        public Vector subtract(Vector sub) throws Exception {
            if (length != sub.getLength()) {
                throw new Exception("Неверный вектор.");
            }
            Vector result = new Vector(length);
            for (int i = 0; i < length; i++) {
                result.vector[i] = this.vector[i] - sub.vector[i];
            }
            return result;
        }

        public double normI() {
            double max = Math.abs(vector[0]);
            for (int i = 1; i < length; i++) {
                if (Math.abs(vector[i]) > max) {
                    max = Math.abs(vector[i]);
                }
            }
            return max;
        }
    }

    private static Matrix a;
    private static Vector b;
    private static final int n = 5;

    public static void main (String[] args) {
        Vector x;
        Vector r;
        try {
            a = new Matrix(n, n);
            b = new Vector(n);
            a.fillDefault();
            b.fillDefault();
            x = rotationMethod();
            a.fillDefault();
            b.fillDefault();
            r = a.mul(x).subtract(b);
            System.out.println("Вектор X:");
            x.print(false);
            System.out.println();
            System.out.println("Вектор невязки R:");
            r.print(true);
            System.out.println();
            System.out.println("Норма вектора невязки ||R|| = " + r.normI());
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Vector rotationMethod() throws Exception {
        Vector result = new Vector(n);
        double cos;
        double sin;
        double prevAij;
        double prevBi;
        double denom;
        for(int i = 0; i < n - 1; i++) {
            for (int k = i + 1; k < n; k++) {
                denom = Math.sqrt(Math.pow(a.matrix[i][i], 2) + Math.pow(a.matrix[k][i], 2));
                cos = a.matrix[i][i] / denom;
                sin = -a.matrix[k][i] / denom;
                a.matrix[i][i] = denom;
                a.matrix[k][i] = 0;
                for (int j = i + 1; j < n; j++) {
                    prevAij = a.matrix[i][j];
                    a.matrix[i][j] = prevAij * cos - a.matrix[k][j] * sin;
                    a.matrix[k][j] = prevAij * sin + a.matrix[k][j] * cos;
                }
                prevBi = b.vector[i];
                b.vector[i] = prevBi * cos - b.vector[k] * sin;
                b.vector[k] = prevBi * sin + b.vector[k] * cos;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            result.vector[i] = b.vector[i];
            for (int j = i + 1; j < n; j++) {
                result.vector[i] -= a.matrix[i][j] * result.vector[j];
            }
            result.vector[i] /= a.matrix[i][i];
        }
        return result;
    }
}
