package com.opstty;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DistrictAgeWritable implements Writable {

    private int age;
    private int district;

    public DistrictAgeWritable(){ }


    public DistrictAgeWritable(int age, int district){
        this.age = age;
        this.district = district;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.age = in.readInt();
        this.district = in.readInt();
    }


    public int getAge(){ return this.age; }

    public int getDistrict(){ return this.district; }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.age);
        out.writeInt(this.district);
    }
}

