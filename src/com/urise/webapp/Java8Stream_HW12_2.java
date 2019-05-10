package com.urise.webapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Java8Stream_HW12_2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(45);
        list.add(17);
        list.add(4);
        list.add(3);
        list.add(9);
        list = oddOrEven(list);
        for (int i : list) {
            System.out.println(i + " ");
        }
    }

    private static List<Integer> oddOrEven(final List<Integer> integers) {
        int sum = integers.stream().reduce((acc, y) -> acc + y).get();
        if (sum % 2 == 0) {
            return integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());
        }
        return integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
    }

    private static List<Integer> oddOrEven2(List<Integer> integers) {
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

    private static List<Integer> oddOrEven3(final List<Integer> integers) {
        return integers.stream().filter(x -> ((integers.stream().reduce((acc, y) -> acc + y).get()) % 2 != 0 && x % 2 == 0) || ((integers.stream().reduce((acc, y) -> acc + y).get()) % 2 == 0 && x % 2 != 0)).collect(Collectors.toList());
    }
}
