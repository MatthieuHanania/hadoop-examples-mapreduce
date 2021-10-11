package com.opstty;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DistrictAgeWritable implements Writable {

    private int age;
    private int district;

    public DistrictAgeWritable(){}

    public void setAge(int age){
        this.age = age;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.district = in.readInt();
        this.age = in.readInt();
    }


    public int getAge(){ return this.age; }

    public int getDistrict(){ return this.district; }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.age);
        dataOutput.writeInt(this.district);
    }




}

