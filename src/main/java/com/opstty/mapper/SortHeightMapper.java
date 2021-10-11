package com.opstty.mapper;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SortHeightMapper extends Mapper<Object, Text, DoubleWritable, IntWritable> {

    private IntWritable val = new IntWritable();
    private  DoubleWritable word = new DoubleWritable();

    public void map(Object key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {

        String[] trees = (value.toString()).split(";");
        if(!trees[0].equals("GEOPOINT")){ //ignore first line
            try{
                int objectId = Integer.parseInt(trees[11]); //get The object ID
                word.set(Double.parseDouble(trees[6])); //get the height

                val.set(objectId);

                context.write(word, val);
            }catch (NumberFormatException e){}


        }
    }


}
