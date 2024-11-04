package charset;

import static java.nio.charset.StandardCharsets.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncodingMain1 {

    static final Charset EUC_KR = Charset.forName("EUC-KR");
    static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {
        System.out.println("== ASCII 영문 처리 ==");
        encoding("a", US_ASCII);
        encoding("a", ISO_8859_1);
        encoding("a", EUC_KR);
        encoding("a", UTF_8);
        encoding("a", UTF_16BE);

        System.out.println("한글 지원");
        encoding("가", EUC_KR);
        encoding("가", MS_949);
        encoding("가", UTF_8);
        encoding("가", UTF_16BE);

    }
    
    private static void encoding(String text, Charset charset) {
        // 문자열을 바이트로 변경할 때 반드시 Charset이 필요
        byte[] bytes = text.getBytes(charset);
        System.out.printf("%s -> [%s] 인코딩 -> %s %sbtye\n", text, charset, Arrays.toString(bytes), bytes.length);
    }

}
