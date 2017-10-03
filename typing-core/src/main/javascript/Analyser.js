function Analyser(presented) {
    this.presented = presented;
    this.transcribed = "";
    this.avgDelta = new DescriptiveStatistics();
    this.avgPress = new DescriptiveStatistics();
    this.avgKeyDelta = new HashMap();
    this.avgKeyPress = new HashMap();
    this.keyErrorFreq = new HashMap();
    this.pressedKeys = new HashMap();
    this.errorIdx = -1;
    this.CHAR_PER_WORD = 5;
    this.ignoreList = [];
}

Analyser.prototype = {
    constructor: Analyser,
    start: function () {
        this.last = new Date().getTime();
    },
    ignore: function() {
        this.ignoreList = this.ignoreList.concat([].slice.call(arguments));
    },
    keyDown: function (c) {
        if (this.ignoreList.includes(c)) {
            return;
        }
        if (!this.pressedKeys.isEmpty()) {
            this.keyUp(this.pressedKeys.keySet().pop());
        }
        this.pressedKeys.put(c, new Date().getTime());
        if (this.transcribed.length === this.presented.length) {
            throw "Analyser complete";
        }

        var timeToLast = new Date().getTime() - this.last;
        if (this.presented.charAt(this.transcribed.length) === c) {
            this.transcribed += c; //http://www.keybr.com/practice

            if (!this.avgKeyDelta.containsKey(c)) {
                this.avgKeyDelta.put(c, new DescriptiveStatistics());
            }

            this.avgDelta.addValue(timeToLast);
            this.avgKeyDelta.get(c).addValue(timeToLast);
            if (this.transcribed.length === this.presented.length) {
                this.pressedKeys.clear();
            }
        } else if (this.errorIdx !== this.transcribed.length) {
            if (!this.keyErrorFreq.containsKey(this.presented.charAt(this.transcribed.length))) {
                this.keyErrorFreq.put(this.presented.charAt(this.transcribed.length), new Frequency());
            }

            this.keyErrorFreq.get(this.presented.charAt(this.transcribed.length)).addValue(c);
            this.errorIdx = this.transcribed.length;
        }

        this.last = new Date().getTime();
    },
    keyUp: function (c) {
        if (this.ignoreList.includes(c)) {
            return;
        }
        if (!this.pressedKeys.containsKey(c)) {
            return;
        }

        var pressDelta = new Date().getTime() - this.pressedKeys.remove(c);
        if (!this.avgKeyPress.containsKey(c)) {
            this.avgKeyPress.put(c, new DescriptiveStatistics());
        }

        this.avgPress.addValue(pressDelta);
        this.avgKeyPress.get(c).addValue(pressDelta);
    },
    cps: function () {
        return 1000 / this.avgDelta.getMean();
    },
    wpm: function () {
        return this.cps() * 60 / this.CHAR_PER_WORD;
    },
    acc: function () {
        var totalErr = this.keyErrorFreq.entrySet().map(function (entry) {
            return entry.value.getSumFreq();
        }).reduce(function (prevFreq, freq) {
            return prevFreq + freq;
        }, 0);
        console.log(totalErr);
        return 1 - (totalErr / this.transcribed.length);
    }
};
        