package com.urise.webapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;

public class Java8Stream_HW12_2_ver_1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(45);
        list.add(7);
        list.add(8);
        list.add(12);
        list.add(4);
        list = oddOrEven(list);
        for (int i : list) {
            System.out.println(i + " ");
        }
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        BinaryOperator<Integer> binaryInt = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer sum, Integer elem) {
                if ((sum % 2 == 0 && elem % 2 != 0) || (sum % 2 != 0 && elem % 2 == 0)) {
                    return elem;
                }
                return null;
            }
        };
        int sum = 0;
        for (int elem : integers) {
            sum += elem;
        }
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            int item = iterator.next();
            if (binaryInt.apply(sum, item) == null) {
                iterator.remove();
            }
        }
        return integers;
    }
}
