package lab2;

public class Matrix {
    double[]data;
    int rows;
    int cols;

    public Matrix(int rows, int cols){
        allocateData(rows, cols);
    }

    public Matrix(double[][] _data){
        int numberOfColumns = Utils.findMaxRow(_data);
        int numberOfRows = _data.length;
        allocateData(numberOfRows,numberOfColumns);
        for(int row=0; row<rows; row++){
            int firstIndex = row * cols;
            System.arraycopy(_data[row], 0, data, firstIndex, _data[row].length);
        }
    }

    public double[][] asArray(){
        double[][] newArray = new double[rows][];
        for(int row=0; row<rows; row++){
            newArray[row] = new double[cols];
            System.arraycopy(data, row*cols, newArray[row], 0, cols);
        }
        return newArray;
    }

    public double get(int rowIndex, int colIndex){
        return data[getOffsetIndex(rowIndex, colIndex)];
    }

    public void set (int rowIndex, int colIndex, double value){
        data[getOffsetIndex(rowIndex, colIndex)] = value;
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[ ");
        for(int row=1;row<=rows;row++){
            buf.append("[ ");
            for(int col=1;col<=cols;col++){
                buf.append(data[getOffsetIndex(row,col)]);
                if(col != cols){
                    buf.append(", ");
                }
            }
            buf.append(" ]");
            if(row != rows){
                buf.append(", ");
            }
        }
        buf.append(" ]");
        return buf.toString();
    }

    public void reshape(int newRows,int newCols){
        if(rows*cols != newRows*newCols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d", rows, cols, newRows, newCols));
        }

        rows = newRows;
        cols = newCols;
    }

    public int[] shape(){
        return new int[] {rows, cols};
    }

    public Matrix add(Matrix addend){
        if(!sameSize(addend)) {
            throw new RuntimeException(String.format("%d x %d matrix can't be add to %d x %d matrix", rows, cols, addend.cols, addend.rows));
        }
        Matrix sum = new Matrix(rows, cols);
        for(int row=1; row <= rows; row++){
            for(int col=1; col <= cols; col++){
                sum.set(row, col, get(row, col) + addend.get(row, col));
            }
        }
        return sum;
    }

    public Matrix sub(Matrix subtrahend){
        if(!sameSize(subtrahend)) {
            throw new RuntimeException(String.format("%d x %d matrix can't be add to %d x %d matrix", rows, cols, subtrahend.cols, subtrahend.rows));
        }
        Matrix sum = new Matrix(rows, cols);
        for(int row=1; row <= rows; row++){
            for(int col=1; col <= cols; col++) {
                sum.set(row, col, get(row, col) - subtrahend.get(row, col));
            }
        }
        return sum;
    }

    private boolean sameSize(Matrix matrix){
        return rows == matrix.rows && cols == matrix.cols;
    }

    private void allocateData(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows*cols];
    }

    private int getOffsetIndex(int rowIndex, int colIndex){
        return (rowIndex - 1) * cols + colIndex-1;
    }
}

class Utils{
    public static int findMaxRow(double[][] table){
        int max = 0;
        for(double[] row: table){
            if(row.length > max){
                max = row.length;
            }
        }
        return max;
    }
}
