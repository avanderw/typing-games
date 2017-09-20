describe("A Frequency", function () {
    var freq = new Frequency();
    freq.addValue(1);
    freq.addValue(2);
    freq.addValue(2);
    freq.addValue(3);
    freq.addValue(3);
    freq.addValue(3);
    freq.addValue(2);
    freq.addValue(2);
    freq.addValue(1);

    it("calculates counts", function () {
        expect(freq.getCount(3)).toBe(3);
    });
    
    it("calculates unique counts", function () {
        expect(freq.getUniqueCount()).toBe(3);
    });
    
    it("calculates sum of the frequencies", function () {
        expect(freq.getSumFreq()).toBe(9);
    });
    
    it("will return a percentage for a bin", function () {
        expect(freq.getPct(3)).toBe(0.3333333333333333);
    });
    
    it("will get the mode of the set", function () {
        expect(freq.getMode()).toEqual(['2']);
    });
    
    it("will allow sampling", function () {
        var sampleFrequency = new Frequency();
        for (var i = 0; i < 1000; i++)
            sampleFrequency.addValue(freq.getSample());

        expect(sampleFrequency.getUniqueCount()).toBe(freq.getUniqueCount());
        expect(sampleFrequency.getMode()).toEqual(freq.getMode());

        expect(Math.abs(freq.getPct(3) - sampleFrequency.getPct(3)) < .05).toBe(true);
        expect(Math.abs(freq.getPct(2) - sampleFrequency.getPct(2)) < .05).toBe(true);
    });
});



