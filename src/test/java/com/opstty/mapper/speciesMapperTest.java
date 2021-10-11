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
public class speciesMapperTest {
    @Mock
    private Mapper.Context context;
    private speciesMapper speciesMapper;

    @Before
    public void setup() {
        this.speciesMapper = new speciesMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        String value = "(48.857140829, 2.29533455314);7;Maclura;pomifera;Moraceae;";
        this.speciesMapper.map(null, new Text(value), this.context);
        verify(this.context, times(1))
                .write(new Text("pomifera"), new IntWritable(1));
    }
}
