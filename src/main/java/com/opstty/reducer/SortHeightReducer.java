package com.opstty.reducer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SortHeightReducer extends Reducer<DoubleWritable, IntWritable, DoubleWritable, IntWritable> {

    private IntWritable result = new IntWritable();

    public void reduce(DoubleWritable key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int value = 0;
        for (IntWritable val : values) {
            value += val.get();
        }

        result.set(value);
        context.write(key, result);
    }

}
