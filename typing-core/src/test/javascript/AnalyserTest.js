describe("An Analyser", function () {
    it("analyses typing accuracy", function () {
        var analyser = new Analyser("The quick brown fox jumped over the brown dog!");
        analyser.start();
        var transcribed = "The quick fbrown fox jumped over the jbrown dobdg!";
        for (var idx = 0; idx < transcribed.length; idx++) {
            analyser.keyDown(transcribed.charAt(idx));
        }

        expect(analyser.acc()).toBeCloseTo(.934, 0);
        expect(analyser.pressedKeys.isEmpty()).toBe(true);
    });

    it("analyses keypress duration", function () {
        var idx, analyser = new Analyser("The fiction that the magazine does publish");
        analyser.start();

        for (idx = 0; idx < analyser.presented.length; idx++) {
            analyser.keyDown(analyser.presented.charAt(idx));
            var start = new Date().getTime();
            while (new Date().getTime() < start + 30) {
            }
            analyser.keyUp(analyser.presented.charAt(idx));
        }

        expect(analyser.avgPress.getMean()).toBeCloseTo(30, 0);
        expect(analyser.avgKeyPress.get(' ').getMean()).toBeCloseTo(30, 0);
        expect(analyser.pressedKeys.isEmpty()).toBe(true);
    });

    it("tracks errors", function () {
        var idx, analyser = new Analyser("The quick brown fox jumped over the brown dog!");
        analyser.start();

        var transcribed = "The quick fbrown fox jumped over the jbrown dobdg!";
        for (idx = 0; idx < transcribed.length; idx++) {
            analyser.keyDown(transcribed.charAt(idx));
        }

        expect(analyser.keyErrorFreq.get('g').getSumFreq()).toBe(1);
        expect(analyser.pressedKeys.isEmpty()).toBe(true);
    });

    it("auto closes when stream is finished", function(){
        var analyser = new Analyser("a");
        analyser.start();
        analyser.keyDown('a');
        expect(analyser.keyDown).toThrow("Analyser complete");
    });
    
    it("ignores certain keys", function(){
        var analyser = new Analyser("The quick");
        analyser.ignore('T', 16, 17, 18); // backspace, shift, control, alt
        analyser.keyDown('T');
        expect(analyser.transcribed).toBe("");
    });
});