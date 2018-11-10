import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


import java.io.IOException;

public class HadoopValidator {

    public static class ValidatorMapTask extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
        String preLine = null;

        @Override
        public void map(LongWritable key, Text value, Mapper.Context context)
                throws InterruptedException {
            String line = value.toString();
            String[] tokens = line.split(Constants.OUTPUT_KEY_VALUE_DIVIDER);
            int valuePart = Integer.parseInt(tokens[1]);
            if (preLine!=null) {
                int preValuePart = Integer.parseInt(preLine.split(Constants.OUTPUT_KEY_VALUE_DIVIDER)[1]);
                if (valuePart < preValuePart) throw new InterruptedException("Validation failed, the numbers are not sorted");
            }
            preLine = line;
        }
    }

    public static void validate(String input, String output) throws IOException, InterruptedException, ClassNotFoundException {
        Path inputPath = new Path(input);
        Path outputDir = new Path(output);
        Configuration conf = new Configuration(true);
        Job job = Job.getInstance(conf);
        job.setJarByClass(HadoopValidator.class);
        job.setMapperClass(ValidatorMapTask.class);
        job.setNumReduceTasks(0);

        FileInputFormat.addInputPath(job, inputPath);
        job.setInputFormatClass(TextInputFormat.class);

        FileOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(TextOutputFormat.class);

        int code = job.waitForCompletion(true) ? 0 : 1;
        if (code == 0) System.out.println("Validation passed: the numbers are sorted");
        System.exit(code);
    }
}
