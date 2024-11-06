package io.file;

import java.io.File;
import java.io.IOException;

public class OldFilePath {
    public static void main(String[] args) throws IOException {
        File file = new File("temp/..");
        System.out.println("path = " + file.getPath());

        // 절대 경로
        // 경로의 처음부터 내가 입력한 모든 경로를 다 표현한다.
        System.out.println("Absolute path = " + file.getAbsolutePath());

        // 정규 경로
        // 경로의 계산이 모두 끝난 경로. 정규 경로는 하나만 존재한다.
        System.out.println("Canonical path = " + file.getCanonicalPath());

        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println((f.isFile() ? "F" : "D") + " | " + f.getName());
        }
    }
}
