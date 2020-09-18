public class TestCSV {
    public static void main(String[] args) {
        String test = "c,b,c,\"123,345,789\",,";
        System.out.println(test);
        final String[] tokens = test.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        System.out.println("tokens.length = " + tokens.length);
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
        }
    }
}
