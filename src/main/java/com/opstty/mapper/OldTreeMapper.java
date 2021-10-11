package com.opstty.mapper;

import com.opstty.DistrictAgeWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.DataInput;
import java.io.IOException;

public class OldTreeMapper extends Mapper<Object, Text, DistrictAgeWritable, IntWritable> {

    private IntWritable one = new IntWritable(1);
    private DistrictAgeWritable districtAgeWritable= new DistrictAgeWritable();

    public void map(Object key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {

        String[] trees = (value.toString()).split(";");
        if(!trees[0].equals("GEOPOINT")){ //ignore first line
            try{
                int age =Integer.parseInt(trees[5]); //get the year plant
                int district = Integer.parseInt(trees[1]); //get the district

                districtAgeWritable.setAge(age);
                districtAgeWritable.setDistrict(district);

                context.write(districtAgeWritable, one);
            }catch (NumberFormatException e){}

        }
    }
}