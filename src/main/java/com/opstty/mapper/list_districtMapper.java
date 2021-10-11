package com.opstty.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class list_districtMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {

        /* Two ways to get the district
       StringTokenizer itr = new StringTokenizer(value.toString(),";");
       if (!(itr.nextToken().equals("GEOPOINT"))){
                word.set(itr.nextToken());
                context.write(word, one);
       }*/

        String val = value.toString();
        String[] parts = val.split(";");

        if(!parts[0].equals("GEOPOINT")){
            String arrondissement = parts[1];
            word.set(arrondissement);
            context.write(word, one);

        }

    }
}
