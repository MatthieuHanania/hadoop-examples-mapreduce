package com.opstty.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class speciesReducerTest {
    @Mock
    private Reducer.Context context;
    private speciesReducer speciesReducer;

    @Before
    public void setup() {
        this.speciesReducer = new speciesReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        String key = "key";
        IntWritable value = new IntWritable(1);
        Iterable<IntWritable> values = Arrays.asList(value, value, value);
        this.speciesReducer.reduce(new Text(key), values, this.context);
        verify(this.context).write(new Text(key), new IntWritable(1));
    }

}
