## Pre-requisites ##
###1. node.js installed: http://nodejs.org/###

## Installation ##
Install the packages required by Grunt executing the following command in the root of your application:
`npm install`

## Running Grunt ##
To build the project, run the following command in the root of your application:
`grunt`


##Move to AngularJS 1.3.15##

You have to do the following:

- In angular.js, line 12403 you have to add the following piece of code:

`
if(!args){
	args = [];
}
`

Make sure it is placed immediately after the comment message "IE doesn't have apply for some native functions"

- Don't use loops "for in". IE8 doesn't work correctly with them. Use a regular for loop. 


- Add the polyfill for functions

Add the following code before including AngularJS in the page.
`
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(searchElement) {
        if (this.length === 0) {
            return -1;
        }
        var n = 0;
        if (arguments.length > 1) {
            n = Number(arguments[1]);
            if (isNaN(n)) {
                n = 0;
            } else if (n !== 0 && n !== Infinity && n !== -Infinity) {
                n = (n > 0 || -1) * Math.floor(Math.abs(n));
            }
        }
        if (n >= this.length) {
            return -1;
        }
        var k = n >= 0 ? n : Math.max(this.length - Math.abs(n), 0);
        while (k < this.length) {
            if (k in this && this[k] === searchElement) {
                return k;
            }
            ++k;
        }
        return -1;
    };
}

if (!Array.prototype.filter) {
    Array.prototype.filter = function(fun/*, thisArg*/) {
	
		console.log(fun);
	
        if (this === undefined || this === null) {
            throw new TypeError();
        }

        var t = Object(this);
        var len = t.length >>> 0;
    
        var res = [];
        var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
        for (var i = 0; i < len; i++) {
            if (i in t) {
                var val = t[i];
                if (fun.call(thisArg, val, i, t)) {
                    res.push(val);
                }
            }
        }
        return res;
    };
}

if (!Array.isArray) {
    Array.isArray = function(arg) {
        return Object.prototype.toString.call(arg) === '[object Array]';
    };
}

if (!Array.prototype.every) {
    Array.prototype.every = function(callbackfn, thisArg) {
        'use strict';
        var T, k;
        if (this == null) {
            throw new TypeError('this is null or not defined');
        }
        var O = Object(this);
        var len = O.length >>> 0;
        if (typeof callbackfn !== 'function') {
            throw new TypeError();
        }
        if (arguments.length > 1) {
            T = thisArg;
        }
        k = 0;
        while (k < len) {

            var kValue;

            if (k in O) {
                kValue = O[k];
                var testResult = callbackfn.call(T, kValue, k, O);
                if (!testResult) {
                    return false;
                }
            }
            k++;
        }
        return true;
    };
}

if (!Object.create) {
    Object.create = (function() {
        var Object = function() {};
        return function (prototype) {
            if (arguments.length > 1) {
                throw new Error('Second argument not supported');
            }
            if (typeof prototype != 'object') {
                throw new TypeError('Argument must be an object');
            }
            Object.prototype = prototype;
            var result = new Object();
            Object.prototype = null;
            return result;
        };
    })();
}

if (!Array.prototype.forEach) {
    Array.prototype.forEach = function(fun /*, thisArg */) {
        if (this === void 0 || this === null)
            throw new TypeError();

        var t = Object(this);
        var len = t.length >>> 0;
        if (typeof fun !== "function")
            throw new TypeError();

        var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
        for (var i = 0; i < len; ++i) {
            if (i in t)
                fun.call(thisArg, t[i], i, t);
        }
    };
}

if (!String.prototype.trim) {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/gm, '');
    };
}

(function() {
    //$http uses onload instead of onreadystatechange. Need shimming as IE8 doesn't have onload.
    if (new XMLHttpRequest().onload === undefined) {
        var orig = XMLHttpRequest.prototype.send;
        XMLHttpRequest.prototype.send = function() {
            var self = this;
            if (!this.onreadystatechange && this.onload) {
                this.onreadystatechange = function() {
                    if (self.readyState === 4) {
                        self.onload();
                    }
                };
            }
            orig.apply(self, arguments);
        };
    }
})();

if (!Date.now) {
    Date.now = function() {
        return new Date().getTime();
    };
}

if (!Function.prototype.bind) {
    Function.prototype.bind = function(oThis) {
        if (typeof this !== "function") {
            throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
        }
        var aArgs = Array.prototype.slice.call(arguments, 1),
            fToBind = this,
            fNOP = function() {
            },
            fBound = function() {
                return fToBind.apply(this instanceof fNOP && oThis
                        ? this
                        : oThis,
                    aArgs.concat(Array.prototype.slice.call(arguments)));
            };

        fNOP.prototype = this.prototype;
        fBound.prototype = new fNOP();

        return fBound;
    };
}

if (!Object.keys) {
    Object.keys = function(object) {
        var keys = [];
        for (var o in object) {
            if (object.hasOwnProperty(o)) {
                keys.push(o);
            }
        }
        return keys;
    };
}

if (!Object.getPrototypeOf) {
    Object.getPrototypeOf = function(object) {
        return object.__proto__ || object.constructor.prototype;
    };
}
`