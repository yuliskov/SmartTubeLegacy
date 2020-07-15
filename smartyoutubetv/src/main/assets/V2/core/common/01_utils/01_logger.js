console.log("Scripts::Running script logger.js");

var Log = {
    d: function(tag, msg) {
        tag = tag ? tag : 'UNTAGGED';
        console.log(tag + ': ' + msg);
    },

    e: function(tag, msg) {
        tag = tag ? tag : 'UNTAGGED';
        console.error(tag + ': ' + msg);
    },

    w: function(tag, msg) {
        tag = tag ? tag : 'UNTAGGED';
        console.warn(tag + ': ' + msg);
    }
};
