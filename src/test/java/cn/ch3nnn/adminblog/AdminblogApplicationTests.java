package cn.ch3nnn.adminblog;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SpringBootTest
class AdminblogApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void test() {
        final List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(6);
        list.add(11);
        list.add(134);


        final Stream<Integer> stream = list.stream();
        // stream.filter(s -> s>6).forEach(System.out::println);
        // final Optional<Integer> first = stream.filter(s -> s > 6).findFirst();
        // final Optional<Integer> any = stream.parallel().filter(s -> s > 6).findAny();


        final List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 8, 9);
        final List<String> strings = Arrays.asList("xiao", "ming", "hello");
        // 将字符串list先全部转大写然后收集成list
        final List<String> collect = strings.stream().map(String::toUpperCase).collect(Collectors.toList());


        System.out.println(collect);
        list1.stream().map(s -> s + 3).forEach(System.out::println);

    }

}
