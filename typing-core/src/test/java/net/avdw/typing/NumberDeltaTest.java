package net.avdw.typing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class NumberDeltaTest {
    @Test
    public void testBasic() throws InterruptedException {
        TypingAnalyser analyser = new TypingAnalyser("The quick brown fox jumps over the lazy dog!");
        analyser.start();
        NumberDelta watch = new NumberDelta(() -> analyser.avgDelta.getMean());
        watch.update();
        
        analyser.keyDown('T');
        TimeUnit.MILLISECONDS.sleep(100);
        analyser.keyUp('T');
        TimeUnit.MILLISECONDS.sleep(100);
        watch.update();
        
        analyser.keyDown('h');
        TimeUnit.MILLISECONDS.sleep(100);
        analyser.keyUp('h');
        TimeUnit.MILLISECONDS.sleep(100);
        watch.update();
        
        analyser.keyDown('e');
        TimeUnit.MILLISECONDS.sleep(100);
        analyser.keyUp('e');
        TimeUnit.MILLISECONDS.sleep(100);
        watch.update();

        assertEquals(126d, watch.last, 1);
        assertEquals(24d, watch.delta, 1);
    }
}
