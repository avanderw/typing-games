package net.avdw.typing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class AnalyserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testOneWps() {
        System.out.println("Testing one word per second");
        Analyser analyser = new Analyser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        analyser.start();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(1000 / analyser.CHAR_PER_WORD);
                    analyser.put('a');
                }
            } catch (InterruptedException ex) {
                // ignore
            }
        });

        try {
            int delay = 5;
            System.out.println(String.format("Simulating typing for %ss", delay));
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(Analyser.class.getName()).log(Level.SEVERE, null, ex);
        }
        executor.shutdownNow();

        assertEquals("Expecting 60 wpm", 60f, Math.round(analyser.wpm()), 1f);
    }

    @Test (expected = IllegalStateException.class)
    public void testCompletedAnalyser() {
        System.out.println("testCompletedAnalyser");
        Analyser analyser = new Analyser("a");
        analyser.start();
        analyser.put('a');
        analyser.put('a');
    }
}
