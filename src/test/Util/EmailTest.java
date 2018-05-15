package Util;

import com.crankz.weixin.util.EmailUtil;
import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void Send() {
        EmailUtil.SendEmail("标题","内容", "外资业务");
    }
}
