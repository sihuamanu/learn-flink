package com.imooc.flink.course04;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sihua.ancloudera.com on 2020/2/2.
 */
public class JavaDataSetTransformationApp {
    public static void main(String[] args) throws Exception{
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        mapFunction(env);
        filterFunction(env);
        mapPartitionFuntion(env);
    }

    public static void mapFunction(ExecutionEnvironment env) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        DataSource<Integer> data = env.fromCollection(list);

        data.map(new MapFunction<Integer, Integer>() {
            @Override
            public Integer map(Integer input) throws Exception {
                return input + 1;
            }
        }).print();
    }

    public static void filterFunction(ExecutionEnvironment env) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        DataSource<Integer> data = env.fromCollection(list);

        data.map(new MapFunction<Integer, Integer>() {
            @Override
            public Integer map(Integer input) throws Exception {
                return input + 1;
            }
        })
        .filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer input) throws Exception {
                return input > 5;
            }
        }).print();
    }

    public static void mapPartitionFuntion(ExecutionEnvironment env) throws Exception{
        List<String> list = new ArrayList<String>();
        for(int i=1;i<=100;i++){
            list.add("student: " + i);
        }
        DataSource<String> data = env.fromCollection(list).setParallelism(4);

        /*data.map(new MapFunction<String, String>() {
            public String map(String input) throws Exception{
                String connection = DBUtils.getConnection();
                System.out.println("connection = [" + connection +"]");
                DBUtils.returnConnection(connection);
                return input;
            }
        }).print();*/

        data.mapPartition(new MapPartitionFunction<String, String>() {

            @Override
            public void mapPartition(Iterable<String> inputs, Collector<String> collector) throws Exception {
                String connection = DBUtils.getConnection();
                System.out.println("connection = [" + connection +"]");
                DBUtils.returnConnection(connection);
            }
        }).print();
    }

}
