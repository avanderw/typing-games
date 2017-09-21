describe("An Analyser", function(){
    it("calculates typing accuracy", function(){
        var analyser = new Analyser("The quick brown fox jumped over the brown dog!");
        analyser.start();
        var transcribed = "The quick fbrown fox jumped over the jbrown dobdg!";
        for (var idx = 0; idx < transcribed.length(); idx++) {
            analyser.keyDown(transcribed.charAt(idx));
        }

        assertEquals("Error rate is 93%.", .934, analyser.acc(), 0.001);
        assertTrue(analyser.pressedKeys.isEmpty());
    });
});