function HashMap() {
    'use strict';
}

HashMap.prototype = {
    constructor: HashMap,
    put: function (key, value) {
        'use strict';
        this[key] = value;
    },
    get: function (key) {
        'use strict';
        return this[key];
    },
    remove: function (key) {
        'use strict';
        var value = this[key];
        delete this[key];
        return value;
    },
    clear: function () {
        'use strict';
        var prop;
        for (prop in this) {
            if (Object.prototype.hasOwnProperty.call(this, prop)) {
                delete this[prop];
            }
        }
    },
    keySet: function () {
        'use strict';
        var prop,
            keyset = [];
        for (prop in this) {
            if (Object.prototype.hasOwnProperty.call(this, prop)) {
                keyset.push(prop);
            }
        }

        return keyset;
    },
    entrySet: function () {
        'use strict';
        var prop,
            entry,
            entryset = [];
        for (prop in this) {
            if (Object.prototype.hasOwnProperty.call(this, prop)) {
                entry = {
                    key: prop,
                    value: this[prop]
                };
                entryset.push(entry);
            }
        }

        return entryset;
    },
    containsKey: function (key) {
        'use strict';
        return Object.prototype.hasOwnProperty.call(this, key);
    },
    isEmpty: function () {
        'use strict';
        var prop;
        for (prop in this) {
            if (Object.prototype.hasOwnProperty.call(this, prop)) {
                return false;
            }
        }
        return true;
    }
};
