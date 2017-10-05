package net.avdw.typing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

//@Ignore("Working on javascript for now")
public class TypingAnalyserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testOneWps() throws InterruptedException {
        System.out.println("testOneWps");
        TypingAnalyser analyser = new TypingAnalyser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        analyser.start();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(1000 / analyser.CHAR_PER_WORD);
                    analyser.keyDown('a');
                }
            } catch (InterruptedException ex) {
                // ignore
            }
        });

        int delay = 5;
        System.out.println(String.format("Simulating typing for %ss", delay));
        TimeUnit.SECONDS.sleep(delay);
        executor.shutdownNow();

        assertEquals("Expecting 60 wpm", 60f, Math.round(analyser.wpm), 1f);
    }

    @Test(expected = IllegalStateException.class)
    public void testCompletedAnalyser() {
        System.out.println("testCompletedAnalyser");
        TypingAnalyser analyser = new TypingAnalyser("a");
        analyser.start();
        analyser.keyDown('a');
        analyser.keyDown('a');
    }

    @Test
    public void testErrors() {
        System.out.println("testErrors");
        TypingAnalyser analyser = new TypingAnalyser("The quick brown fox jumped over the brown dog!");
        analyser.start();
        String transcribed = "The quick fbrown fox jumped over the jbrown dobdg!";
        for (int idx = 0; idx < transcribed.length(); idx++) {
            analyser.keyDown(transcribed.charAt(idx));
        }

        assertEquals("Should only register error once.", 1, analyser.keyErrorFreq.get('g').getSumFreq());
        assertTrue(analyser.pressedKeys.isEmpty());
    }

    @Test
    public void testAccuracy() {
        System.out.println("testAccuracy");
        TypingAnalyser analyser = new TypingAnalyser("The quick brown fox jumped over the brown dog!");
        analyser.start();
        String transcribed = "The quick fbrown fox jumped over the jbrown dobdg!";
        for (int idx = 0; idx < transcribed.length(); idx++) {
            analyser.keyDown(transcribed.charAt(idx));
        }

        assertEquals("Error rate is 93%.", .934, analyser.acc, 0.001);
        assertTrue(analyser.pressedKeys.isEmpty());
    }

    @Test
    public void testKeyUp() throws InterruptedException {
        System.out.println("testKeyPress");
        TypingAnalyser analyser = new TypingAnalyser("The fiction that the magazine does publish");
        analyser.start();
        for (int idx = 0; idx < analyser.presented.length(); idx++) {
            TimeUnit.MILLISECONDS.sleep(50);
            analyser.keyDown(analyser.presented.charAt(idx));
            TimeUnit.MILLISECONDS.sleep(20);
            analyser.keyUp(analyser.presented.charAt(idx));
        }

        assertEquals("Average press incorrect.", 31f, analyser.avgPress.getMean(), analyser.avgPress.getStandardDeviation());
        assertEquals("Average key press incorrect.", 31f, analyser.avgKeyPress.get(' ').getMean(), analyser.avgKeyPress.get(' ').getStandardDeviation());
        assertTrue(analyser.pressedKeys.isEmpty());
    }
    
    @Test
    public void testIgnore() {
        System.out.println("testIgnore");
        TypingAnalyser analyser = new TypingAnalyser("The fiction");
        // backspace, shift, control, alt
        analyser.ignore('e','f');
        analyser.start();
        analyser.keyDown('T');
        analyser.keyDown('h');
        analyser.keyDown('e');
        assertEquals("Th", analyser.transcribed);
    }
    
}
