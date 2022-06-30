package test;

import P1.MagicSquare;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MagicSquareTest {

    @Test
    void convertStringToInt() {
        List<String> data_test = new ArrayList<>();
        data_test.add("1\t2");
        assertFalse(MagicSquare.convertStringToInt(data_test)); // 测试行和列不相等情况
    }

    @org.junit.jupiter.api.Test
    void judgeInt() {
        assertFalse(MagicSquare.judgeInt("-1")); // 测试负数
        assertFalse(MagicSquare.judgeInt("1.25")); // 测试浮点数
        assertFalse(MagicSquare.judgeInt("1 2")); // 测试非\t隔开
    }

    @org.junit.jupiter.api.Test
    void judgeMatrix() {
        List<List<Integer>> data_test = new ArrayList<>();
        int length_test = 2;
        List<Integer> row = new ArrayList<>();
        row.add(1);
        row.add(1);
        data_test.add(row);
        data_test.add(row);
        assertTrue(MagicSquare.judgeMatrix(data_test, length_test));
    }

    @org.junit.jupiter.api.Test
    void generateMagicSquareTest() {
        String filepath = "test/test.txt";
        assertFalse(MagicSquare.generateMagicSquare(8, filepath)); // 测试偶数

        assertFalse(MagicSquare.generateMagicSquare(-5, filepath)); // 测试负数

        assertTrue(MagicSquare.generateMagicSquare(5, filepath)); // 测试正数
    }
}