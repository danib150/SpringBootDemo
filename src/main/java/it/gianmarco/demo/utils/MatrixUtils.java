package it.gianmarco.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MatrixUtils {
    public static Set<Integer> oppositeDiagonal(int[][] matrix) {
        Set<Integer> result = new HashSet<>();
        int l = matrix.length-1;
        int c = 0;
        while (l >= 0) {
            log.info("{}", matrix[c][l]);
            result.add(matrix[c][l]);
            l--;
            c++;
        }
        return result;
    }

    public static Set<Integer> diagonal(int[][] matrix) {
        Set<Integer> result = new HashSet<>();
        int c = 0;
        while (matrix.length > c) {
            log.info("{}", matrix[c][c]);
            result.add(matrix[c][c]);
            c++;
        }

        return result;

    }
}
