# HadoopSorting

This is the basic hadoop sorting project.

It will generate 10 GB data, sort then validate.

you can export the project as .jar file, then follow below:

Download Hadoop-2.8.5 to ~ path (such as home/username)
Copy the myhadoop.jar to ~path 
 
cd ~/hadoop-2.8.5
Run the following commands one by one: 
1. Generate unsorted numbers
java -jar ~/myhadoop.jar generate
 
2. Sort numbers 
hadoop jar ~/myhadoop.jar sort
 
3. Validate
hadoop jar ~/myhadoop.jar validate 