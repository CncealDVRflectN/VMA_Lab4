package by.vma.lab4;

public class Rotation {
    private static Matrix a;
    private static Vector b;
    private static Vector x;
    private static Vector r;
    private static final int n = 5;

    public static void vma() {
        try {
            a = new Matrix(n, n);
            b = new Vector(n);
            a.fillDefault();
            b.fillDefault();
            a.print();
            System.out.println();
            x = rotationMethod();
            r = a.mul(x).subtract(b);
            x.print(false);
            System.out.println();
            r.print(true);
            System.out.println();
            a.print();
            System.out.println();
            System.out.println("r = " + r.normI());
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
        double prevFi;
        for(int i = 0; i < n - 1; i++) {
            for (int k = i + 1; k < n; k++) {
                cos = a.matrix[i][i] / Math.sqrt(Math.pow(a.matrix[i][i], 2) + Math.pow(a.matrix[k][i], 2));
                sin = -a.matrix[k][i] / Math.sqrt(Math.pow(a.matrix[i][i], 2) + Math.pow(a.matrix[k][i], 2));
                for (int j = i; j < n; j++) {
                    prevAij = a.matrix[i][j];
                    a.matrix[i][j] = prevAij * cos - a.matrix[k][j] * sin;
                    a.matrix[k][j] = prevAij * sin + a.matrix[k][j] * cos;
                }
                prevFi = b.vector[i];
                b.vector[i] = prevFi * cos - b.vector[k] * sin;
                b.vector[k] = prevFi * sin + b.vector[k] * cos;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            result.vector[i] = b.vector[i];
            for (int j = i + 1; j < n; j++) {
                result.vector[i] -= a.matrix[i][j] * result.vector[j];
            }
        }
        return result;
    }
}
