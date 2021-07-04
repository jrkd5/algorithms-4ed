import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {

    QuickUnionUF uf;
    private final boolean[][] sites;
    private int numOpen;
    private final int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException("'n' must be a positive integer");

        int N = n*n + 2;
        uf = new QuickUnionUF(N);
        sites = new boolean[n][n];
        gridSize = n;

        // Connect top row to virtual top node
        for (int i = 1; i < n; i++) {
            uf.union(0, i);
        }

        // Connect bottom row to virtual bottom node
        int startPos = gridSize*(gridSize-1) + 1;
        int endPos = startPos + gridSize;
        for (int i = 0; i < gridSize; i++) {
            uf.union(startPos + i, endPos);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (!(row > 0 && row <= gridSize) || !(col > 0 && col <= gridSize))
            throw new IllegalArgumentException("Incorrect row or column");

        int xRow = row - 1;
        int xCol = col - 1;
        if (!sites[xRow][xCol])
        {
            sites[xRow][xCol] = true;
            numOpen++;

            // Translates current grid site to the 'uf' position
            int pos = xRow * gridSize + col;

            // Check upper site and connect if open
            if (row > 1 && isOpen(row-1, col)) {
                uf.union(pos - gridSize, pos);
            }

            // Check lower site and connect if open
            if (row < gridSize && isOpen(row+1, col)) {
                uf.union(pos + gridSize, pos);
            }

            // Check right site and connect if open
            if (col < gridSize && isOpen(row, col+1)) {
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
        if (!(row > 0 && row <= gridSize) || !(col > 0 && col <= gridSize))
            throw new IllegalArgumentException("Incorrect row or column");
        return sites[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (!(row > 0 && row <= gridSize) || !(col > 0 && col <= gridSize))
            throw new IllegalArgumentException("Incorrect row or column");
        return uf.find(0) == uf.find((row-1)*gridSize + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return uf.find(0) == uf.find(gridSize * gridSize + 1);
    }

    private void displayGrid(int row, int col)
    {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
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
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            p.open(row, col);
            p.displayGrid(row, col);
        }
    }
}
