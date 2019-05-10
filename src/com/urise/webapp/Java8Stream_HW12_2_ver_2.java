package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Java8Stream_HW12_2_ver_2 {
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
        int sum = 0;
        for (int i : integers) {
            sum += i;
        }
        if (sum % 2 == 0) {
            return integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());
        }
        return integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
    }
}
