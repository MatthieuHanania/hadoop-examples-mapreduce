# YARN & MapReduce 2
Hugo Freitas & Matthieu Hanania
<ins> Link : https://github.com/MatthieuHanania/hadoop-examples-mapreduce
Please check the commit

# 1 MapReduce JAVA

## 1.6 Send the JAR to the edge node

### 1.6.1 Microsoft Windows
I do all the installation and we can see that the file is in the server
```
[m.hanania@hadoop-edge01 ~]$ ls
...
hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar
...
```

### 1.6.3 Run the job

I run this command\
``yarn jar hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar wordcount``

We got the same thing than in the previous TP
```
Usage: wordcount <in> [<in>...] <out>
```

## 1.8 Remarkable trees of Paris
Firstly I put the file in my edge space using filezilla and to my hdfs space using ``hdfs dfs -copyFromLocal trees.csv``

Then, in all the Mapper, we need to ignore the first line of ``trees.csv``
I transform the line received into String, and then I check if the first part of the line is not "Geopoint"
```
String val = value.toString();
String[] parts = val.split(";");
if(!parts[0].equals("GEOPOINT")){
...
```

We can also use the token
```
StringTokenizer itr = new StringTokenizer(value.toString(),";");
if (!(itr.nextToken().equals("GEOPOINT"))){
```

## 1.8.1 Districts containing trees (very easy)

### <ins>Creation

Firstly I create the function in the file ``AppDriver.java``

```
...
 programDriver.addClass("list_district", list_district.class,
    "A map/reduce program that display list of district");
    ...
```

Then I create a new job java file called ``list_district.java``
The file contain the same function than ``wordcount.java`` but it calls its own mapper and reducer based on the those of wordcount. 
So I create them ``list_districtMapper.java`` and ``list_districtReducer.java``

The Mapper received the csv file line by line. So I split a line into parts and I kept only the district (the second part)

```
String val = value.toString();
String[] parts = val.split(";");
if(!parts[0].equals("GEOPOINT")){
    String arrondissement = parts[1];
    word.set(arrondissement);
    context.write(word, one);
}

```
<br>
In the reducer, I just remove the part that loop through the values.

To launch the job, I use
``
yarn jar hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar list_district /user/m.hanania/trees.csv /user/m.hanania/list_dir
``
It create a file in the hdfs system, when we read it

``hdfs dfs -cat list_dis/part-r-00000`` we get the list of the districts

```
11      1
12      1
13      1
14      1
15      1
16      1
...
```

### <ins>Test

Test are usefull because if they are not well made, 

Now let's write some code in the ``test`` file

To do that, I copy the test file that are already exist. 
I just change the testMap function to set the result we want

<br>
For the mapper:

The Test must return `` 7  1``

```bash
public void testMap() throws IOException, InterruptedException {
        String value = "(48.857140829, 2.29533455314);7;";
        this.list_districtMapper.map(null, new Text(value), this.context);
        verify(this.context, times(1))
                .write(new Text("7"), new IntWritable(1));
    }
}
```
<br>

For the reducer:
```bash
this.list_districtReducer.reduce(new Text(key), values, this.context);
        verify(this.context).write(new Text(key), new IntWritable(1));
```



## 1.8.2 Show all existing species (very easy)

### <ins> Code
I create the job command

```bash
programDriver.addClass("species", species.class,
    "A map/reduce program that displays the list of different species trees");
```
Then the job file ``species.java``
The content is the same than before but it calls its own mapper and reducer

```bash
job.setJarByClass(species.class);
job.setMapperClass(speciesMapper.class);
job.setCombinerClass(speciesReducer.class);
job.setReducerClass(speciesReducer.class);
```

The mapper ``speciesMapper`` is also the same as before but keep only the species (column 3 of the file)

```bash
String[] trees = (value.toString()).split(";");
if(!trees[0].equals("GEOPOINT")){ //ignore first line
    String species = trees[3];
    word.set(species);
    context.write(word, one);
}
```

The reducer ``speciesReducer`` is the same as before

To start the job we use  
``yarn jar hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar species /user/m.hanania/trees.csv /user/m.hanania/species
``

and get
```bash
21/10/08 22:31:33 INFO mapreduce.Job:  map 0% reduce 0%
21/10/08 22:31:41 INFO mapreduce.Job:  map 100% reduce 0%
21/10/08 22:31:46 INFO mapreduce.Job:  map 100% reduce 100%
...

acerifolia      1
araucana        1
atlantica       1
australis       1
...
```

### <ins> Test

I also setup the test part. The species correspond to the forth part of the line delimited by ";" so I print "pomifera 0" as requested in the Mapper

``speciesMapperTest``
```bash
@Test
public void testMap() throws IOException, InterruptedException {
    String value = "(48.857140829, 2.29533455314);7;Maclura;pomifera;Moraceae;";
    this.speciesMapper.map(null, new Text(value), this.context);
    verify(this.context, times(1))
            .write(new Text("pomifera"), new IntWritable(1));
}
```

``speciesReducerTest`` is the same as before

## 1.8.3 Number of trees by kinds (easy)
### <ins> Code

I create the function on the ``AppDriver`` and all the file

\
<ins>``NumTrees.java``

The file is the same as before, juste call its own Mapper and 

\
<ins> ``NumTreesMapper.java``

The code just have to print the Kind of a tree and 1 
```bash
String[] trees = (value.toString()).split(";");
if(!trees[0].equals("GEOPOINT")){ //ignore first line
    String kind = trees[2]; //get the kind
    word.set(kind);
    context.write(word, one);
}
```

\
<ins> ``NumTreesReducer.java``

The code is the same as ``IntSumReducer.java``

Because we just want to group kind together and make a sum

To try, we launch the job
``yarn jar hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar NumTrees /user/m.hanania/trees.csv /user/m.hanania/kind``

And we got 
```bash
...
21/10/09 14:33:59 INFO mapreduce.Job:  map 100% reduce 100%
...
```
``
hdfs dfs -cat kind/part-r-00000``
```
Acer    3
Aesculus        3
Ailanthus       1
Alnus   1
Araucaria       1
Broussonetia    1
Calocedrus      1
...
```

###<ins> Test

\
<ins> ``NumTreesMapperTest``

The Mapper receive a line like"(48.8685686134, 2.31331809304);8;Calocedrus" and I return just the tree kind, "Calocedrus" in our case

So on the test, we just code 
```bash
String value = "(48.8685686134, 2.31331809304);8;Calocedrus";
    this.NumTreesMapper.map(null, new Text(value), this.context);
    verify(this.context, times(1))
            .write(new Text("Calocedrus"), new IntWritable(1));
```
When we test it, it returns no error

\
<ins> ``NumTreesReducerTest``

The reducer must group together and count the trees kinds
We copy the ``IntSumReducerTest``

If we test with 3 values, the sum is oviously 3

```bash
public void testReduce() throws IOException, InterruptedException {
    String key = "key";
    IntWritable value = new IntWritable(1);
    Iterable<IntWritable> values = Arrays.asList(value, value, value);
    this.NumTreesReducer.reduce(new Text(key), values, this.context);
    verify(this.context).write(new Text(key), new IntWritable(3));
    }
```

## 1.8.4 Maximum height per kind of tree (average)

###<ins> Code

I create the function in the ``AppDriver``
```
programDriver.addClass("TallKind", TallKind.class,
    "A map/reduce program that display the tallest trees for each kind of trees");
```

Then, The job ``TallKind.java`` It call its own mapper and reducer

\
<ins>``TallKindMapper``

We avoid the first line, and then we get the colomn 2 and 6 of the file that correspond to the kind and the height.

In the file, the height is a double number , but understand as a string by the code. So we transform a String into a Double into an int. And we send it to the Reducer

```bash
try{
    int taille = (int) Double.parseDouble(trees[6]);
    tall.set(taille);
    word.set(kind);
    context.write(word, tall);
}catch (NumberFormatException e){}
```

\
<ins>`TallKinReducer`

The Reducer received from the mapper a kind and a height for all trees.

For each kind, we want to keep the taller. So we use a for loop that keep the max height

```bash
int max= 0;
    for (IntWritable value : values) {
        if(max < value.get()){
            max= value.get();
        }
    }
    result.set(max);
    context.write(key, result);
```

\
When we try the job with the command
``
yarn jar hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar TallKind /user/m.hanania/trees.csv /user/m.hanania/tallKind
``

We got
```
Acer    16
Aesculus        30
Ailanthus       35
Alnus   16
Araucaria       9
Broussonetia    12
...
Sequoia 30
Sequoiadendron  35
Styphnolobium   10
Taxodium        35
Taxus   13
Tilia   20
Ulmus   15
Zelkova 30
```

We see that correspond to the max height of all kind of trees.


###<ins> Test

We also want to create the test file of the Mapper and Reducer

<ins>`TallKindMapperTest`

We send to the Mapper a line like 
`(48.8685686134, 2.31331809304);8;Calocedrus;decurrens;Cupressaceae;1854;20.0;195.0;`

And it must return `(Calocedrus,20)`

So we code 
```bash
String value = "(48.8685686134, 2.31331809304);8;Calocedrus;decurrens;Cupressaceae;1854;20.0;195.0;";
this.TallKindMapper.map(null, new Text(value), this.context);
verify(this.context, times(1))
        .write(new Text("Calocedrus"), new IntWritable(20));
```

When we try the test we got `Process finished with exit code 0` that is good.

\
<ins>`TallKindMapperReducer`

The reducer get a kind of trees and multiple height. It must return the taller

So If we send two trees of the same kind, with 20 and 16 as height, the reducer must return 20

```bash
public void testReduce() throws IOException, InterruptedException {
    String key = "key";
    IntWritable value1 = new IntWritable(20);
    IntWritable value = new IntWritable(18);
    Iterable<IntWritable> values = Arrays.asList(value1, value);
    this.TallKindReducer.reduce(new Text(key), values, this.context);
    verify(this.context).write(new Text(key), new IntWritable(20));
}

--
Process finished with exit code 0
```

## 1.8.5 Sort the trees height from smallest to largest (average)
### <ins> Code

Firslty we create the function 

```bash
programDriver.addClass("SortHeight", SortHeight.class,
  "A map/reduce program that sort the trees height");
```

For this job, we want to send at the end, the height sorted. And the reducer sort naturally the keys. So we want height as keys.

That is why, we set the type to the end key to DoubleWritable

So we write on the file ``SortHeight.java`` 
```
job.setReducerClass(SortHeightReducer.class);
job.setOutputKeyClass(DoubleWritable.class);
job.setOutputValueClass(IntWritable.class);
```


<ins>`SortHeightMapper`

The Mapper send a combination of key and height. We choose the `Object_ID` as key because it can identify each trees separately 

```bash
 try{
    int objectId = Integer.parseInt(trees[11]); //get The object ID
    word.set(Double.parseDouble(trees[6])); //get the height

    val.set(objectId);

    context.write(word, val);
}catch (NumberFormatException e){}

```

<ins>`SortHeightReducer`

The reducer just return the height and the Id of all trees

```bash
int value = 0;
for (IntWritable val : values) {
    value += val.get();
}

result.set(value);
context.write(key, result);
```

We get
```
[m.hanania@hadoop-edge01 ~]$ hdfs dfs -cat SortHeight/part-r-00000
2.0     3
5.0     89
6.0     62
9.0     39
10.0    295
11.0    4
12.0    426
13.0    42
14.0    258
15.0    266
16.0    197
18.0    230
20.0    330
```


###<ins> Test

\
<ins> `SortHeightMapperTest`

The MapperTest received a line and return only the height and the ID
The height is a ``DoubleWritable`` and the ID is an `IntWritable`

```bash
String value = "(48.857140829, 2.29533455314);7;Maclura;pomifera;Moraceae;1935;13.0;;Quai Branly, avenue de La Motte-Piquet, avenue de la Bourdonnais, avenue de Suffren;Oranger des Osages;;6;Parc du Champs de Mars";
this.SortHeightMapper.map(null, new Text(value), this.context);
verify(this.context, times(1))
        .write(new DoubleWritable(13.0), new IntWritable(6));
```
``13.0`` and ``6`` in this case

\
<ins> `SortHeightReducerTest`

The reducer get a ``DoubleWritable`` (the height) and an ``IntWritable`` (the ID)

And return them

IT just return what it received


## 1.8.6 District containing the oldest tree

Firstly we create the function in Appdriver

```
programDriver.addClass("OldTree", OldTree.class,
"A map/reduce program that display the district contraining the oldest tree");
```

\
<ins> ``OldTree.java``

In this file we set the mapper and reducer 
And we want to display a couple (district age) as a key
```
job.setOutputKeyClass(DistrictAgeWritable.class);
job.setOutputValueClass(IntWritable.class);
```

Then we create a file ``DistrictAgeWritable`` It is a file that implement writable


The file contain the year and a district. We use getter and setter
```
private int age;
private int district;

public void setAge(int age){ this.age = age; }
...
public int getDistrict(){ return this.district; }

```

We also write function of the Writable class
```bash

public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(this.age);
    dataOutput.writeInt(this.district);
}

public void readFields(DataInput in) throws IOException {
    this.age = in.readInt();
    this.district = in.readInt();
}
```

\
<ins> ``OldTreeMapper``


In this file , we have to put district and year into the new Writable

So we make the function extends of the good types
```
extends Mapper<Object, Text, DistrictAgeWritable, IntWritable> 
```



\
<ins> ``OldTreeMapper``

The reducer just display the age and district 

So the class extends of the good types 

```
OldTreeReducer extends Reducer<DistrictAgeWritable, IntWritable, DistrictAgeWritable, IntWritable> {
```





