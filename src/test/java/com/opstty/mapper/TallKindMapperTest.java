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
public class TallKindMapperTest {
    @Mock
    private Mapper.Context context;
    private TallKindMapper TallKindMapper;

    @Before
    public void setup() {
        this.TallKindMapper = new TallKindMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        String value = "(48.8685686134, 2.31331809304);8;Calocedrus;decurrens;Cupressaceae;1854;20.0;195.0;";
        this.TallKindMapper.map(null, new Text(value), this.context);
        verify(this.context, times(1))
                .write(new Text("Calocedrus"), new IntWritable(20));
    }
}
