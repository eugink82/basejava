package com.urise.webapp;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class Java8Stream_HW12_1 {
    public static void main(String[] args) {
        int[] mass = new int[]{3, 7, 6, 7, 3, 4, 9};
        BinaryOperator<Integer> binaryInt = (s1, s2) -> s1 * (int) (Math.pow(10, s2));
        System.out.println(minValue(binaryInt, mass));
    }

    private static int minValue(BinaryOperator<Integer> binaryInt, int[] mass) {
        mass = Arrays.stream(mass).distinct().sorted().toArray();
        int size = mass.length;
        int minVal = 0;
        for (int i = 0; i < mass.length; i++) {
            mass[i] = binaryInt.apply(mass[i], --size);
            minVal += mass[i];
        }
        return minVal;
    }
}
