package net.avdw.typing;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Analyser {

    String presented;
    String transcribed;

    final int CHAR_PER_WORD = 5;
    final DescriptiveStatistics avgDelta;
    final DescriptiveStatistics avgPress;
    final Map<Character, DescriptiveStatistics> avgKeyDelta;
    final Map<Character, DescriptiveStatistics> avgKeyPress;
    final Map<Character, Frequency> keyErrorFreq;
    final Map<Character, Long> pressedKeys;

    private long last;
    private int errorIdx;

    Analyser(String presented) {
        this.presented = presented;
        transcribed = "";

        avgDelta = new DescriptiveStatistics();
        avgPress = new DescriptiveStatistics();
        avgKeyDelta = new HashMap();
        avgKeyPress = new HashMap();
        keyErrorFreq = new HashMap();
        pressedKeys = new HashMap();

        errorIdx = -1;

    }

    void start() {
        last = System.currentTimeMillis();
    }

    void keyDown(char c) {
        if (!pressedKeys.isEmpty()) {
            keyUp(pressedKeys.keySet().iterator().next());
        }
        pressedKeys.put(c, System.currentTimeMillis());

        if (transcribed.length() == presented.length()) {
            throw new IllegalStateException("Analyser complete");
        }

        long timeToLast = System.currentTimeMillis() - last;

        if (presented.charAt(transcribed.length()) == c) {
            transcribed += c; //http://www.keybr.com/practice

            if (!avgKeyDelta.containsKey(c)) {
                avgKeyDelta.put(c, new DescriptiveStatistics());
            }

            avgDelta.addValue(timeToLast);
            avgKeyDelta.get(c).addValue(timeToLast);
            
            if (transcribed.length() == presented.length()) {
                pressedKeys.clear();
            }
        } else if (errorIdx != transcribed.length()) {
            if (!keyErrorFreq.containsKey(presented.charAt(transcribed.length()))) {
                keyErrorFreq.put(presented.charAt(transcribed.length()), new Frequency());
            }

            keyErrorFreq.get(presented.charAt(transcribed.length())).addValue(c);
            errorIdx = transcribed.length();
        }

        last = System.currentTimeMillis();
    }

    void keyUp(Character c) {
        if (!pressedKeys.containsKey(c)) {
            return;
        }
        
        long pressDelta = System.currentTimeMillis() - pressedKeys.remove(c);
        if (!avgKeyPress.containsKey(c)) {
            avgKeyPress.put(c, new DescriptiveStatistics());
        }
        
        avgPress.addValue(pressDelta);
        avgKeyPress.get(c).addValue(pressDelta);
    }

    double cps() {
        return 1000d / avgDelta.getMean();
    }

    double wpm() {
        return cps() * 60 / CHAR_PER_WORD;
    }

    double acc() {
        double totalErr = keyErrorFreq.entrySet().stream().mapToLong(entry -> entry.getValue().getSumFreq()).sum();
        return 1 - totalErr / transcribed.length();
    }
}
