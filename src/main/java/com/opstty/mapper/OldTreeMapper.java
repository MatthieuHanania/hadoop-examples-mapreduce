package com.opstty.mapper;

import com.opstty.DistrictAgeWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.DataInput;
import java.io.IOException;

public class OldTreeMapper extends Mapper<Object, Text, DistrictAgeWritable, IntWritable> {


    private DistrictAgeWritable districtAgeWritable = new DistrictAgeWritable();
    private IntWritable zero = new IntWritable(0);

    IntWritable Wage = new IntWritable();
    IntWritable Wdistrict = new IntWritable();

    public void map(Object key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {

        String[] trees = (value.toString()).split(";");
        if(!trees[0].equals("GEOPOINT")){ //ignore first line
            try{
                double district = (double) Integer.parseInt(trees[1]); //get the district
                double yearPlant =  (double) Integer.parseInt(trees[5]); //get the year plant

                String truc = String.valueOf(district);
                truc+= String.valueOf(yearPlant);


                context.write(districtAgeWritable, zero);
            }catch (NumberFormatException e){}

        }
    }

}
