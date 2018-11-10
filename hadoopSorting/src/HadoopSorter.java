import org.apache.hadoop.fs.Path;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HadoopSorter {
    public static void sort(String input, String output) throws IOException, InterruptedException, ClassNotFoundException {
        Path inputPath = new Path(input);
        Path outputDir = new Path(output);
        Configuration conf = new Configuration(true);
        Job job = Job.getInstance(conf);
        job.setJarByClass(HadoopSorter.class);
        job.setMapperClass(MapTask.class);
        job.setReducerClass(ReduceTask.class);
        job.setNumReduceTasks(1);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setSortComparatorClass(IntComparator.class);

        FileInputFormat.addInputPath(job, inputPath);
        job.setInputFormatClass(TextInputFormat.class);

        FileOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(TextOutputFormat.class);

        int code = job.waitForCompletion(true) ? 0 : 1;
        System.exit(code);
    }

}
