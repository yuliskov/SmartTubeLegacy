package com.liskovsoft.smartyoutubetv.misc;

import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Translates stick's {@link MotionEvent} to {@link KeyEvent}
 */
public class AnalogStickTranslator {
    private static final float THRESHOLD = 0.1f;
    private static final float THRESHOLD2 = -0.1f;
    private final Callback mCallback;
    private float mX;
    private float mY;

    public interface Callback {
        void onAnalogStickKeyEvent(KeyEvent event);
    }

    public AnalogStickTranslator(Callback callback) {
        mCallback = callback;
    }

    public void handle(MotionEvent event) {
        if (!isAnalogStick(event) || mCallback == null) {
            return;
        }

        float oldX = mX;
        float oldY = mY;
        int key = KeyEvent.KEYCODE_UNKNOWN;

        calculate(event);

        if (mX > THRESHOLD && mX > oldX && oldX != 0) {
            // moving right
            key = KeyEvent.KEYCODE_DPAD_RIGHT;
        } else if (mX < THRESHOLD2 && mX < oldX && oldX != 0) {
            // moving left
            key = KeyEvent.KEYCODE_DPAD_LEFT;
        } else if (mY > THRESHOLD && mY > oldY && oldY != 0) {
            // moving up
            key = KeyEvent.KEYCODE_DPAD_UP;
        } else if (mY < THRESHOLD2 && mY < oldY && oldY != 0) {
            // moving down
            key = KeyEvent.KEYCODE_DPAD_DOWN;
        }

        if (key != KeyEvent.KEYCODE_UNKNOWN) {
            mCallback.onAnalogStickKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, key));
            mCallback.onAnalogStickKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, key));
        }
    }

    private void calculate(MotionEvent event) {
        // Process all historical movement samples in the batch
        final int historySize = event.getHistorySize();

        // Process the movements starting from the
        // earliest historical position in the batch
        for (int i = 0; i < historySize; i++) {
            // Process the event at historical position i
            processJoystickInput(event, i);
        }

        // Process the current movement sample in the batch (position -1)
        processJoystickInput(event, -1);
    }

    private void processJoystickInput(MotionEvent event, int historyPos) {
        InputDevice device = InputDevice.getDevice(event.getDeviceId());

        // Calculate the horizontal distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat axis, or the right control stick.
        mX = getCenteredAxis(event, device,
                MotionEvent.AXIS_X, historyPos);
        if (mX == 0) {
            mX = getCenteredAxis(event, device,
                    MotionEvent.AXIS_HAT_X, historyPos);
        }

        if (mX == 0) {
            mX = getCenteredAxis(event, device,
                    MotionEvent.AXIS_Z, historyPos);
        }

        // Calculate the vertical distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat switch, or the right control stick.
        mY = getCenteredAxis(event, device,
                MotionEvent.AXIS_Y, historyPos);
        if (mY == 0) {
            mY = getCenteredAxis(event, device,
                    MotionEvent.AXIS_HAT_Y, historyPos);
        }

        if (mY == 0){
            mY = getCenteredAxis(event, device,
                    MotionEvent.AXIS_RZ, historyPos);
        }

    }

    private static float getCenteredAxis(MotionEvent event, InputDevice device, int axis, int historyPos) {
        InputDevice.MotionRange range = null;

        if (device != null) {
            range = device.getMotionRange(axis, event.getSource());
        }

        float flat = 0;

        if (range != null) {
            // A joystick at rest does not always report an absolute position of
            // (0,0). Use the getFlat() method to determine the range of values
            // bounding the joystick axis center.
            flat = range.getFlat();
        }

        final float value =
                historyPos < 0 ? event.getAxisValue(axis):
                        event.getHistoricalAxisValue(axis, historyPos);

        // Ignore axis values that are within the 'flat' region of the
        // joystick axis center.
        if (Math.abs(value) > flat) {
            return value;
        }

        return 0;
    }

    private boolean isAnalogStick(MotionEvent event) {
        return (event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE;
    }
}
