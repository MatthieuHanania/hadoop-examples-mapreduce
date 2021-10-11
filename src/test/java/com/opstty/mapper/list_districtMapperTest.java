package com.opstty.mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class list_districtMapperTest {
    @Mock
    private Mapper.Context context;
    private list_districtMapper list_districtMapper;

    @Before
    public void setup() {
        this.list_districtMapper = new list_districtMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        String value = "(48.8768191638, 2.33210374339);9;";
        this.list_districtMapper.map(null, new Text(value), this.context);
        verify(this.context, times(1))
                .write(new Text("9"), new IntWritable(1));
    }
}