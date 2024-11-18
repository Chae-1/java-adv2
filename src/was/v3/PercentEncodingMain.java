package was.v3;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PercentEncodingMain {
    public static void main(String[] args) {
        // ASCII로 표현할 수 없다면, UTF-8로 % 인코딩을 수행한다.
        String str = "가";

        String encode = URLEncoder.encode(str, StandardCharsets.UTF_8);
        System.out.println("encode = " + encode);
        String decode = URLDecoder.decode(encode, StandardCharsets.UTF_8);
        System.out.println("decode = " + decode);

    }
}
