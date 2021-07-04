import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] outcomes;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Incorrect number of trials or grid size");

        this.trials = trials;
        outcomes = new double[trials];
        int gridSize = n * n;

        for (int i = 0; i < trials; i++)
        {
            Percolation p = new Percolation(n);
            while (!p.percolates())
            {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }
            outcomes[i] = (double) p.numberOfOpenSites() / gridSize;
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(outcomes);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(outcomes);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
