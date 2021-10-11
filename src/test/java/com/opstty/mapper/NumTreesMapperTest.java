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
public class NumTreesMapperTest {

    @Mock
    private Mapper.Context context;
    private NumTreesMapper NumTreesMapper;

    @Before
    public void setup() {
        this.NumTreesMapper = new NumTreesMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        String value = "(48.8685686134, 2.31331809304);8;Calocedrus";
        this.NumTreesMapper.map(null, new Text(value), this.context);
        verify(this.context, times(1))
                .write(new Text("Calocedrus"), new IntWritable(1));
    }
}
