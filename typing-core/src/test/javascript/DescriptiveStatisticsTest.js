describe("A DescriptiveStatistic", function () {
    var stats = new DescriptiveStatistics();
    stats.addValue(5);
    stats.addValue(20);
    stats.addValue(40);
    stats.addValue(80);
    stats.addValue(10);

    var stats2 = new DescriptiveStatistics();
    stats2.addValue(1234);
    stats2.addValue(324);
    stats2.addValue(532);
    stats2.addValue(8643);
    stats2.addValue(1450);

    it("calculates means", function () {
        expect(stats.getMean()).toBe(31);
        expect(stats2.getMean()).toBe(2436.6);
    });

    it("calculates standard deviations", function () {
        expect(stats.getStandardDeviation()).toBe(30.495901363953813);
        expect(stats2.getStandardDeviation()).toBe(3501.0572403204146);
    });

    it("calculates skewness", function () {
        expect(stats.getSkewness()).toBe(1.325314709813405);
        expect(stats2.getSkewness()).toBe(2.1363489491807903);
    });

    it("calculates kurtosis", function () {
        expect(stats.getKurtosis()).toBe(1.303763440860216);
        expect(stats2.getKurtosis()).toBe(4.644949276828559);
    });

    it("calculates the maximum", function () {
        expect(stats.getMax()).toBe(80);
        expect(stats2.getMax()).toBe(8643);
    });

    it("calculates the minimum", function () {
        expect(stats.getMin()).toBe(5);
        expect(stats2.getMin()).toBe(324);
    });

    it("keeps track of the sample count", function () {
        expect(stats.getN()).toBe(5);
        expect(stats2.getN()).toBe(5);
    });

    it("calculates the sum", function () {
        expect(stats.getSum()).toBe(155);
        expect(stats2.getSum()).toBe(12183);
    });
});



