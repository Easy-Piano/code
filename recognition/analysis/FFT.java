package recognition.analysis;
//SASHA
import graphics.visualisation.Visualizer;

/**
 * Created by 1 on 25.04.2014.
 */
public class FFT {
    public static void complexToComplex(int sign, int n,
                                        double[] ar, double[] ai) {
        double scale = Math.sqrt(1.0d/n);

        int i,j;
        for (i=j=0; i<n; ++i) {
            if (j>=i) {
                double tempr = ar[j]*scale;
                double tempi = ai[j]*scale;
                ar[j] = ar[i]*scale;
                ai[j] = ai[i]*scale;
                ar[i] = tempr;
                ai[i] = tempi;
            }
            int m = n/2;
            while (m>=1 && j>=m) {
                j -= m;
                m /= 2;
            }
            j += m;
        }

        int mmax,istep;
        for (mmax=1,istep=2*mmax; mmax<n; mmax=istep,istep=2*mmax) {
            double delta = (double)sign*3.141592654d/(double)mmax;
            for (int m=0; m<mmax; ++m) {
                double w = (double)m*delta;
                double wr = Math.cos(w);
                double wi = Math.sin(w);
                for (i=m; i<n; i+=istep) {
                    j = i+mmax;
                    double tr = wr*ar[j]-wi*ai[j];
                    double ti = wr*ai[j]+wi*ar[j];
                    ar[j] = ar[i]-tr;
                    ai[j] = ai[i]-ti;
                    ar[i] += tr;
                    ai[i] += ti;
                }
            }
            mmax = istep;
        }
    }
    public static double[] RealToReal(double[] signal, int from) {
        int n = Visualizer.N;
        if ((n+from)>signal.length) return null;
        double[] backup = new double[n];
        double[] zeros = new double[n];
        for (int i = 0; i < n; i++) {
            backup[i] = signal[i+from];
            zeros[i] = 0;
        }
        FFT.complexToComplex(-1,n,backup,zeros);//-1, 1, 10 as first parameter.
        for (int i=0; i<n; i++){
            backup[i]=Math.sqrt(backup[i]*backup[i]+zeros[i]*zeros[i]);
        }

        return backup;
    }
}
