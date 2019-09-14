package com.yaboong.alterbridge;

import com.yaboong.alterbridge.common.TestProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(TestProfile.TEST)
public class AlterBridgeApplicationTests {

    @Test
    public void contextLoads() {
    }

}
