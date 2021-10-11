package com.opstty.reducer;

import com.opstty.DistrictAgeWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class OldTreeReducer extends Reducer<DistrictAgeWritable, IntWritable, DistrictAgeWritable, IntWritable> {

    private IntWritable result = new IntWritable();

    public void reduce(DistrictAgeWritable key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {


        result.set(1);
        context.write(key, result);
    }
}
