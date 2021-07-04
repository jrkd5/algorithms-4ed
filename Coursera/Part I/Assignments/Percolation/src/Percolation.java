import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final boolean[][] sites;
    private int numOpen;
    private final int gridDim;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException("'n' must be a positive integer");

        uf = new WeightedQuickUnionUF(n*n + 2);
        sites = new boolean[n][n];
        gridDim = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (!(row > 0 && row <= gridDim) || !(col > 0 && col <= gridDim))
            throw new IllegalArgumentException("Incorrect row or column");

        int xRow = row - 1;
        int xCol = col - 1;
        if (!sites[xRow][xCol])
        {
            sites[xRow][xCol] = true;
            numOpen++;

            // Translates current grid site to the 'uf' position
            int pos = xRow * gridDim + col;

            if (row == 1) uf.union(0, pos);
            if (row == gridDim) uf.union(pos, gridDim * gridDim + 1);

            // Check upper site and connect if open
            if (row > 1 && isOpen(row-1, col)) {
                uf.union(pos - gridDim, pos);
            }

            // Check lower site and connect if open
            if (row < gridDim && isOpen(row+1, col)) {
                uf.union(pos + gridDim, pos);
            }

            // Check right site and connect if open
            if (col < gridDim && isOpen(row, col+1)) {
                uf.union(pos, pos+1);
            }

            // Check left site and connect if open
            if (col > 1 && isOpen(row, col-1)) {
                uf.union(pos, pos-1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (!(row > 0 && row <= gridDim) || !(col > 0 && col <= gridDim))
            throw new IllegalArgumentException("Incorrect row or column");
        return sites[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (!(row > 0 && row <= gridDim) || !(col > 0 && col <= gridDim))
            throw new IllegalArgumentException("Incorrect row or column");
        return uf.find(0) == uf.find((row-1)* gridDim + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return uf.find(0) == uf.find(gridDim * gridDim + 1);
    }

    private void displayGrid(int row, int col)
    {
        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                if (sites[i][j]) StdOut.print("1 ");
                else             StdOut.print("0 ");
            }
            StdOut.println();
        }

        StdOut.println();
        StdOut.println("No. open sites: " + numberOfOpenSites());
        StdOut.println("(" + row + ", " + col + ") is full: " + isFull(row, col));
        StdOut.println("Percolates: " + percolates());
        StdOut.println();
    }

    // test client (optional)
    public static void main(String[] args)
    {
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        p.displayGrid(1, 1);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            p.open(row, col);
            p.displayGrid(row, col);
        }
    }
}
