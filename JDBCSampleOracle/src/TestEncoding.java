import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by vincent on 2017/6/11.
 */
public class TestEncoding {
    public static void main(String[] args) {
        String a = "a";
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(a);
        //系統預設
        System.out.println(a.getBytes().length);
        //UTF-16,一般每個字 2bytes，外加2 byte
        byte[] bytes = a.getBytes(StandardCharsets.UTF_16BE);
        System.out.println("UTF-16LE="+bytes.length);
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }

        bytes = a.getBytes(StandardCharsets.UTF_16);
        System.out.println("UTF-16="+bytes.length);
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
        //UTF-8，1－4byte不等，中文字3bytes
        bytes = a.getBytes(Charset.forName("UTF-8"));
        System.out.println("UTF-8="+bytes.length);
        //中文字2byte，英文1byte
        bytes = a.getBytes(Charset.forName("MS950"));
        System.out.println("MS950="+bytes.length);


    }
}
