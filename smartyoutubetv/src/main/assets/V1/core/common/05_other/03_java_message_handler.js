/**
 * Message processing from the main Java app
 */

console.log("Scripts::Running script java_message_handler.js");

var JavaMessageHandler = {
    TAG: 'JavaMessageHandler',
    KEY_PRESS_MESSAGE: "key_press_message",
    KEYCODE_MEDIA_PLAY_PAUSE: "keycode_media_play_pause",
    KEYCODE_MEDIA_STOP: "keycode_media_stop",
    KEYCODE_MEDIA_FAST_FORWARD: "keycode_media_fast_forward",
    KEYCODE_MEDIA_REWIND: "keycode_media_rewind",
    ACTION_DOWN: "action_down",
    ACTION_UP: "action_up",

    handleMessage: function(messageId, messageData) {
        Log.d(this.TAG, "Message received: " + messageId + " " + messageData);

        switch (messageId) {
            case this.KEY_PRESS_MESSAGE:
                messageData && EventUtils.handleKeyPressMessage(JSON.parse(messageData));
                break;
        }
    }
};

// global obj
window.JavaMessageHandler = JavaMessageHandler;
