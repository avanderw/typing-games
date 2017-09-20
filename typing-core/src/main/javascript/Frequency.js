function Frequency() {
    this.values = {};
}

Frequency.prototype = {
    constructor: Frequency,
    addValue: function (key) {
        if (key in this.values)
            this.values[key]++;
        else
            this.values[key] = 1;
    },
    getCount: function (key) {
        if (key in this.values)
            return this.values[key];
        else
            return 0;
    },
    getUniqueCount: function () {
        return Object.keys(this.values).length;
    },
    getSumFreq: function () {
        var result = 0;
        for (var key in this.values)
            result += this.values[key];

        return result;
    },
    getPct: function (key) {
        return this.getCount(key) / this.getSumFreq();
    },
    getMode: function () {
        var mostPopular = 0;
        for (var key in this.values)
            if (this.getCount(key) > mostPopular)
                mostPopular = this.getCount(key);

        var modeList = [];
        for (var key in this.values)
            if (this.getCount(key) === mostPopular)
                modeList.push(key);

        return modeList;
    },
   getSample: function () {
       var pct = 0;
       var rand = Math.random();
       for (var key in this.values) {
           pct += this.getPct(key);
           if (rand < pct)
               return key;
       }
    }
};

