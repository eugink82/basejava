package com.urise.webapp;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class Java8Stream_HW12_1 {
    public static void main(String[] args) {
        int[] mass = new int[]{3, 7, 6, 7, 3, 4, 9};
        System.out.println(minValue(mass));
    }

    private static int minValue(int[] mass) {
        return Arrays.stream(mass).distinct().sorted().reduce((acc,x)->acc*10+x).getAsInt();
    }
}
