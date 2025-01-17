package it.gianmarco.demo.utils;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;

public class MatrixUtilsTest {

    private static final int[][] MATRIX = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    @Test
    public void testMatrix() {
        assert Objects.equals(Set.of(1,5,9), MatrixUtils.diagonal(MATRIX));
    }

    @Test
    public void testMatrixOpposite() {
        assert Objects.equals(Set.of(3,5,7), MatrixUtils.oppositeDiagonal(MATRIX));
    }
}
