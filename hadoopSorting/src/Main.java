public class Main{
    public static void main(String[] args) throws Exception {
        String method = args[0];
        if (method.equals("generate")) {
            FilterGenerator.generate();
        } else if (method.equals("sort")) {
            HadoopSorter.sort(Constants.UNSORTED_FILE_NAME, Constants.SORTED_FILE_DIR);
        } else if (method.equals("validate")) {
            HadoopValidator.validate(Constants.SORTED_FILE_DIR + "/" + Constants.SORTED_FILE_NAME, Constants.VALIDATED_FILE_DIR);
        } else {
            throw new Exception("Wrong method parameter");
        }
    }
}
