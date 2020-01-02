package cn.wangz.javautils.util;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Set;
import java.util.function.Function;

/**
 * @author wang_zh
 * @date 2020/1/2
 */
public class SetStringUtils {

    public static PipedOutputStream outputStream(Set<String> set, Function<String, String> transform) throws IOException {
        final PipedOutputStream out = new PipedOutputStream();
        PipedInputStream is = null;
        try {
            is = new PipedInputStream(1024 * 1024);
            is.connect(out);

            new Thread(() -> {
                try {
                    System.out.println("start");
                    for (String item: set) {
                        if (transform != null) {
                            item = transform.apply(item);
                        }
                        item += "\n";
                        out.write(item.getBytes());
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "pipeline-thread").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}
