package net.avdw.typing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

class TypingAnalyser {

    String presented;
    String transcribed;

    double cps;
    double wpm;
    double acc;

    final int CHAR_PER_WORD = 5;
    final DescriptiveStatistics avgDelta;
    final DescriptiveStatistics avgPress;
    final Map<Character, DescriptiveStatistics> avgKeyDelta;
    final Map<Character, DescriptiveStatistics> avgKeyPress;
    final Map<Character, Frequency> keyErrorFreq;
    final Map<Character, Long> pressedKeys;
    final List<Character> ignore;

    private long last;
    private int errorIdx;

    public TypingAnalyser(String presented) {
        this.presented = presented;
        transcribed = "";

        avgDelta = new DescriptiveStatistics();
        avgPress = new DescriptiveStatistics();
        avgKeyDelta = new HashMap();
        avgKeyPress = new HashMap();
        keyErrorFreq = new HashMap();
        pressedKeys = new HashMap();
        ignore = new ArrayList();

        errorIdx = -1;

    }

    public void ignore(Character... chars) {
        ignore.addAll(Arrays.asList(chars));
    }

    public void start() {
        last = System.currentTimeMillis();
    }

    public void keyDown(Character c) {
        if (ignore.contains(c)) {
            return;
        }
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
        
        cps = 1000d / avgDelta.getMean();
        wpm = cps * 60 / CHAR_PER_WORD;
        double totalErr = keyErrorFreq.entrySet().stream().mapToLong(entry -> entry.getValue().getSumFreq()).sum();
        acc = 1 - totalErr / transcribed.length();
    }

    public void keyUp(Character c) {
        if (ignore.contains(c)) {
            return;
        }
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
}
