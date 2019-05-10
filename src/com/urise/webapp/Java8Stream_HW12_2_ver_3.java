package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Java8Stream_HW12_2_ver_3 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(18);
        list.add(7);
        list.add(10);
        list.add(3);
        list.add(2);
        list = oddOrEven(list);
        for (int i : list) {
            System.out.println(i + " ");
        }
    }

    private static List<Integer> oddOrEven(final List<Integer> integers) {
        return integers.stream().filter(x -> ((integers.stream().reduce((acc, y) -> acc + y).get()) % 2 != 0 && x % 2 == 0) || ((integers.stream().reduce((acc, y) -> acc + y).get()) % 2 == 0 && x % 2 != 0)).collect(Collectors.toList());
    }
}
