package com.opstty;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DistrictAgeWritable implements Writable {

    private double age;
    private double district;

    public DistrictAgeWritable(){

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        //dataOutput.writeInt(age.get());
        //dataOutput.writeInt(district.get());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        age = in.readDouble();
        district = in.readDouble();
    }

}

