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
    void asArray_returnTwoDimensionalArray(){
        double[][] input = {{1,2,3},{1,2,3},{1,2,3}};
        Matrix matrix = new Matrix(input);
        assertTrue(Arrays.deepEquals(input, matrix.asArray()));
    }
    @Test
    void get_returnValueOfMatrixElement() {
        Matrix matrix = new Matrix(new double [][] {{1,2,3},{1,2,3},{1,2,3}});
        assertEquals(matrix.get(1,1), 1);
        assertEquals(matrix.get(1,2), 2);
        assertEquals(matrix.get(3,3), 3);
    }
    @Test
    void set_overwriteMatrixElement() {
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
    @Test
    void reshape_changeDimensions() {
        Matrix matrix = new Matrix(7,8);
        matrix.reshape(4,14);
        assertEquals(matrix.rows,4);
        assertEquals(matrix.cols, 14);
    }
    @Test
    void reshape_lengthOfDataArrayNotChange() {
        Matrix matrix = new Matrix(7,8);
        int before = matrix.data.length;
        matrix.reshape(28,2);
        int after = matrix.data.length;
        assertEquals(after, before);
    }
    @Test
    void reshape_throwErrorIfNumberOfMatrixElementsWillChangeAfterReshape() {
        Matrix matrix = new Matrix(7,8);
        assertThrows(RuntimeException.class, () -> matrix.reshape(3,5));
    }
    @Test
    void shape_returnArrayWithDimensions() {
        int [][] dimensions = new int[][] {{1,1},{7,8},{120,1},{1,22},{5,5}};
        for(int[] dimension: dimensions){
            int rows = dimension[0];
            int cols = dimension[1];
            Matrix matrix = new Matrix(rows, cols);
            int[] result = matrix.shape();
            assertTrue(Arrays.equals(dimension, result));
        }
    }
    @Test
    void add_addMatrixElementsWithTheSameRowAndColumn(){
        Matrix matrix = new Matrix(new double[][] {{3,14,3.3}, {4,5,6,7}, {4.5,7,333,66}});
        Matrix addend = new Matrix(new double[][] {{1,2,3,10}, {4,5,6,11}, {7,8,9,12}});
        Matrix expectedResult = new Matrix(new double[][] {{4, 16, 6.3, 10}, {8, 10, 12, 18}, {11.5, 15, 342, 78}});
        Matrix result = matrix.add(addend);

        double[][] expectedResultAsArray = expectedResult.asArray();
        double[][] resultAsArray = result.asArray();

        for(int row=0; row<resultAsArray.length ;row++){
            assertArrayEquals(expectedResultAsArray[row], resultAsArray[row], 0.00001);
        }
    }
    @Test
    void add_acceptAsArgumentOnlyMatrixWithTheSameDimensions(){
        Matrix matrix3x4 = new Matrix(new double[][] {{3,14,3.3}, {4,5,6,7}, {4.5,7,333,66}});
        Matrix addend3x5 = new Matrix(new double[][] {{1,2,3,10}, {4,5,6,11,14}, {7,8,9,12}});
        assertThrows(RuntimeException.class, () -> matrix3x4.add(addend3x5));
    }
    @Test
    void sub_subtractMatrixElementsWithTheSameRowAndColumn(){
        Matrix matrix = new Matrix(new double[][] {{3,14,3.3}, {4,5,6,7}, {4.5,7,333,66}});
        Matrix subtrahend = new Matrix(new double[][] {{1,2,3,10}, {4,5,6,11}, {7,8,9,12}});
        Matrix expectedResult = new Matrix(new double[][] {{2, 12, 0.3, -10}, {0, 0, 0, -4}, {-2.5, -1, 324, 54}});
        Matrix result = matrix.sub(subtrahend);

        double[][] expectedResultAsArray = expectedResult.asArray();
        double[][] resultAsArray = result.asArray();

        for(int row=0; row<resultAsArray.length ;row++){
            assertArrayEquals(expectedResultAsArray[row], resultAsArray[row], 0.00001);
        }
    }
    @Test
    void sub_acceptAsArgumentOnlyMatrixWithTheSameDimensions(){
        Matrix matrix3x4 = new Matrix(new double[][] {{3,14,3.3}, {4,5,6,7}, {4.5,7,333,66}});
        Matrix subtrahend3x5 = new Matrix(new double[][] {{1,2,3,10}, {4,5,6,11,14}, {7,8,9,12}});
        assertThrows(RuntimeException.class, () -> matrix3x4.add(subtrahend3x5));
    }
}