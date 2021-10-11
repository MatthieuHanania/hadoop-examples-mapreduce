package com.opstty.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class speciesMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {


        String[] trees = (value.toString()).split(";");
        if(!trees[0].equals("GEOPOINT")){ //ignore first line
            String species = trees[3];
            word.set(species);
            context.write(word, one);
        }
    }
}
