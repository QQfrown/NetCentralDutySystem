import edu.gpnu.utils.PropertiesUtil;

import java.io.IOException;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws IOException {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("otherInfo.properties").getPath());
        System.out.println(PropertiesUtil.getPro(Thread.currentThread().getContextClassLoader().getResource("otherInfo.properties").getPath(), "firstWeekTime"));
    }
}
