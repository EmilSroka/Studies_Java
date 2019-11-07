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
        return operate(addend, new Addition());
    }

    public Matrix add(double scalar){
        return operate(scalar, new Addition());
    }

    public Matrix sub(Matrix subtrahend){
        if(!sameSize(subtrahend)) {
            throw new RuntimeException(String.format("%d x %d matrix can't be subtract from %d x %d matrix", rows, cols, subtrahend.cols, subtrahend.rows));
        }
        return operate(subtrahend, new Subtraction());
    }

    public Matrix sub(double scalar){
        return operate(scalar, new Subtraction());
    }

    public Matrix mul(Matrix factor){
        if(!sameSize(factor)) {
            throw new RuntimeException(String.format("%d x %d matrix can't be multiply by %d x %d matrix", rows, cols, factor.cols, factor.rows));
        }
        return operate(factor, new Multiplication());
    }

    public Matrix mul(double scalar){
        return operate(scalar, new Multiplication());
    }

    public Matrix div(Matrix divisor){
        if(!sameSize(divisor)) {
            throw new RuntimeException(String.format("%d x %d matrix can't be divide by %d x %d matrix", rows, cols, divisor.cols, divisor.rows));
        }
        return operate(divisor, new Division());
    }

    public Matrix div(double scalar){
        return operate(scalar, new Division());
    }

    public Matrix dot(Matrix matrix) {
        if( cols != matrix.rows ){
            throw new RuntimeException(String.format(
                    "Number of columns of first matrix (%d) is different than number of rows of second matrix (%d)",
                    cols, matrix.rows));
        }
        Matrix result = new Matrix(rows, matrix.cols);
        for(int row = 1; row<=rows; row++){
            for(int col = 1; col<=matrix.cols;col++){
                int sum = 0;
                for(int i=1; i<=cols; i++){
                    sum += get(row, i) * matrix.get(i, col);
                }
                result.set(row, col, sum);
            }
        }
        return result;
    }

    public double frobenius(){
        int sum = 0;
        for(int i=0; i<data.length; i++){
            sum += data[i] * data[i];
        }
        return Math.sqrt(sum);
    }

    /* helpers */
    private Matrix operate(Matrix operand, Operation operation){
        Matrix result = new Matrix(rows, cols);
        for(int row=1; row <= rows; row++){
            for(int col=1; col <= cols; col++) {
                double matrixElement = operation.result(get(row, col), operand.get(row, col));
                result.set(row, col, matrixElement);
            }
        }
        return result;
    }

    private Matrix operate(double scalar, Operation operation){
        Matrix result = new Matrix(rows, cols);
        for(int row=1; row <= rows; row++){
            for(int col=1; col <= cols; col++) {
                double matrixElement = operation.result(get(row, col), scalar);
                result.set(row, col, matrixElement);
            }
        }
        return result;
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

interface Operation {
    double result(double leftOperand, double rightOperand);
}

class Addition implements Operation{
    @Override
    public double result(double leftOperand, double rightOperand) {
        return leftOperand + rightOperand;
    }
}

class Subtraction implements Operation{
    @Override
    public double result(double leftOperand, double rightOperand) {
        return leftOperand - rightOperand;
    }
}

class Multiplication implements Operation{
    @Override
    public double result(double leftOperand, double rightOperand) {
        return leftOperand * rightOperand;
    }
}

class Division implements Operation{
    @Override
    public double result(double leftOperand, double rightOperand) {
        return leftOperand / rightOperand; // IEEE 754 -> x / 0 = Inf
    }
}

