package Util;

import com.rhy.demo.util.EmailUtil;
import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void Send() {
        EmailUtil.SendEmail("标题","内容");
    }
}
