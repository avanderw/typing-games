function DescriptiveStatistics() {
    this.values = [];
}

DescriptiveStatistics.prototype = {
    constructor: DescriptiveStatistics,
    addValue: function (value) {
        this.values.push(value);
    },
    getMean: function () {
        return this.getSum() / this.getN();
    },
    getStandardDeviation: function () {
        var mean = this.getMean();
        var accum = 0.0;
        var dev = 0.0;
        var accum2 = 0.0;
        for (var i = 0; i < this.values.length; i++) {
            dev = this.values[i] - mean;
            accum += dev * dev;
            accum2 += dev;
        }

        var variance = (accum - (accum2 * accum2 / this.getN())) / (this.getN() - 1);
        return Math.sqrt(variance);
    },
    getSkewness: function () {
        var mean = this.getMean();
        var dev = 0.0;
        var accum = 0.0;
        var accum2 = 0.0;
        var accum3 = 0.0;
        for (var i = 0; i < this.values.length; i++) {
            dev = this.values[i] - mean;
            accum += dev * dev;
            accum2 += dev;
            accum3 += dev * dev * dev;
        }

        var variance = (accum - (accum2 * accum2 / this.getN())) / (this.getN() - 1);
        accum3 /= variance * Math.sqrt(variance);

        return (this.getN() / ((this.getN() - 1) * (this.getN() - 2))) * accum3;
    },
    getKurtosis: function () {
        var accum3 = 0.0;
        for (var i = 0; i < this.values.length; i++) {
            accum3 += Math.pow(this.values[i] - this.getMean(), 4.0);
        }
        accum3 /= Math.pow(this.getStandardDeviation(), 4.0);


        var coefficientOne = (this.getN() * (this.getN() + 1)) / ((this.getN() - 1) * (this.getN() - 2) * (this.getN() - 3));
        var termTwo = (3 * Math.pow(this.getN() - 1, 2.0)) / ((this.getN() - 2) * (this.getN() - 3));

        return (coefficientOne * accum3) - termTwo;
    },
    getMax: function () {
        return Math.max.apply(this, this.values);
    },
    getMin: function () {
        return Math.min.apply(this, this.values);
    },
    getN: function () {
        return this.values.length;
    },
    getSum: function () {
        return this.values.reduce(function (sum, value) {
            return sum + value;
        }, 0);
    }
};

