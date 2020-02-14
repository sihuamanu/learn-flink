package com.imooc.flink.course04;

import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sihua.ancloudera.com on 2020/2/1.
 */
public class JavaDataSetDataSourceApp {
    public static void main(String[] args) throws Exception{
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //fromCollection(env);
        textFile(env);

    }


    public static void fromCollection(ExecutionEnvironment env) throws Exception{
        List<Integer> list = new ArrayList<Integer>();
        for (int i=1; i <= 10; i++){
            list.add(i);
        }
        env.fromCollection(list).print();
    }

    public static void textFile(ExecutionEnvironment env) throws Exception{
        String filePath = "file:///Users/sihua.ancloudera.com/tmp/inputs";
        env.readTextFile(filePath).print();

    }
}
