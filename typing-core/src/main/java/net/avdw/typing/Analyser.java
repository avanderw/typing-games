package net.avdw.typing;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Analyser {

    String presented;
    String transcribed;

    final int CHAR_PER_WORD = 5;
    final DescriptiveStatistics avgDelta;
    final Map<Character, DescriptiveStatistics> avgKeyDelta;

    private long last;

    Analyser(String presented) {
        this.presented = presented;
        this.transcribed = "";

        avgDelta = new DescriptiveStatistics();
        avgKeyDelta = new HashMap();
    }

    void start() {
        last = System.currentTimeMillis();
    }

    void put(char c) {
        if (transcribed.length() == presented.length()) {
            throw new IllegalStateException("Analyser complete");
        }

        long timeToLast = System.currentTimeMillis() - last;

        transcribed += c; //http://www.keybr.com/practice

        if (!avgKeyDelta.containsKey(c)) {
            avgKeyDelta.put(c, new DescriptiveStatistics());
        }

        avgDelta.addValue(timeToLast);
        avgKeyDelta.get(c).addValue(timeToLast);

        last = System.currentTimeMillis();
    }

    double cps() {
        return 1000d / avgDelta.getMean();
    }

    double wpm() {
        return cps() * 60 / CHAR_PER_WORD;
    }
}
