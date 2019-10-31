package lab2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void constructor_acceptDimensions(){
        Matrix size5x7 = new Matrix(5,7);
        assertEquals(size5x7.rows, 5);
        assertEquals(size5x7.cols, 7);
    }
    @Test
    void constructor_fillWithZeros(){
        Matrix filled = new Matrix(2,2);
        assertEquals(filled.data[0], 0);
        assertEquals(filled.data[1], 0);
        assertEquals(filled.data[2], 0);
        assertEquals(filled.data[3], 0);
    }
    @Test
    void constructor_acceptSquareArray(){
        Matrix size3x3 = new Matrix(new double[][]{{1.0, 2.0, 3.0},{4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}});
        assertEquals(size3x3.rows, 3);
        assertEquals(size3x3.cols, 3);
    }
    @Test
    void constructor_acceptRectangleArray(){
        Matrix size3x4 = new Matrix(new double[][]{{1.0, 2.0, 3.0, 4.0},{5.0, 6.0, 7.0, 8.0}, {9.0, 10.0, 11.0, 12.0}});
        assertEquals(size3x4.rows, 3);
        assertEquals(size3x4.cols, 4);
    }
    @Test
    void constructor_acceptRowsWithDifferentLength(){
        Matrix size3x4 = new Matrix(new double[][]{{1.0, 2.0, 3.0, 4.0},{5.0, 6.0, 7.0}, {9.0, 10.0}});
        assertEquals(size3x4.rows, 3);
        assertEquals(size3x4.cols, 4);
    }
    @Test
    void constructor_fillMissingMatrixElementsWithZero(){
        Matrix filled = new Matrix(new double[][]{{1.0, 2.0, 3.0, 4.0},{5.0, 6.0, 7.0}, {9.0, 10.0}});
        assertEquals(filled.data[7], 0);
        assertEquals(filled.data[10], 0);
        assertEquals(filled.data[11], 0);
    }
    @Test
    void asArray(){
        double[][] input = {{1,2,3},{1,2,3},{1,2,3}};
        Matrix matrix = new Matrix(input);
        assertTrue(Arrays.deepEquals(input, matrix.asArray()));
    }
    @Test
    void get() {
        Matrix matrix = new Matrix(new double [][] {{1,2,3},{1,2,3},{1,2,3}});
        assertEquals(matrix.get(1,1), 1);
        assertEquals(matrix.get(1,2), 2);
        assertEquals(matrix.get(3,3), 3);
    }
    @Test
    void set() {
        Matrix matrix = new Matrix(2,2);
        matrix.set(1,1,5);
        matrix.set(2,2,999);
        assertEquals(matrix.data[0], 5);
        assertEquals(matrix.data[3], 999);
    }
    @Test
    void toString_addCorrectData() {
        String expectedResult = "[ [ 1.0, 2.0, 3.0 ], [ 4.0, 5.0, 6.0 ], [ 7.0, 8.0, 9.0 ] ]";
        Matrix matrix = new Matrix(new double[][]{{1,2,3}, {4,5,6}, {7,8,9}});
        assertEquals(expectedResult, matrix.toString());
    }
    @Test
    void toString_separateEveryValueAndRowByComma() {
        int[][] dimensions = new int[][]{{7,8}, {11,1}, {1,6}};
        for(int[] dimension: dimensions){
            int rows = dimension[0];
            int cols = dimension[1];
            Matrix matrix = new Matrix(rows, cols);
            String result = matrix.toString();
            int numberOfBrackets = result.length() - result.replace(",", "").length();
            assertEquals(numberOfBrackets, (cols-1) * rows + rows-1);
        }
    }
    @Test
    void toString_putRowsIntoBracketsAndAllRowsIntoBrackets() {
        int[][] dimensions = new int[][]{{8,7}, {7,1}, {1,12}};
        for(int[] dimension: dimensions) {
            int rows = dimension[0];
            int cols = dimension[1];
            Matrix matrix = new Matrix(rows, cols);
            String result = matrix.toString();
            int numberOfBrackets = result.length() - result.replace("[", "").replace("]","").length();
            assertEquals(numberOfBrackets, rows * 2 + 2);
        }
    }


    /*

    @Test
    void asArray() {
    }

    @Test
    void get() {
    }

    @Test
    void set() {
    }

    @Test
    void testToString() {
    }

    @Test
    void reshape() {
    }

    @Test
    void shape() {
    }

    @Test
    void add() {
    }

    @Test
    void sub() {
    }

    */
}