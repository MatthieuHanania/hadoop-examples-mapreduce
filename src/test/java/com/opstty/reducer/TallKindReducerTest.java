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
public class TallKindReducerTest {
    @Mock
    private Reducer.Context context;
    private TallKindReducer TallKindReducer;

    @Before
    public void setup() {
        this.TallKindReducer = new TallKindReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        String key = "key";
        IntWritable value1 = new IntWritable(20);
        IntWritable value = new IntWritable(18);
        Iterable<IntWritable> values = Arrays.asList(value1, value);
        this.TallKindReducer.reduce(new Text(key), values, this.context);
        verify(this.context).write(new Text(key), new IntWritable(20));
    }

}
