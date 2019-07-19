package com.liskovsoft.smartyoutubetv.misc;

import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Translates stick's {@link MotionEvent} to {@link KeyEvent}
 */
public class AnalogStickTranslator {
    private final Callback mCallback;
    private float mX;
    private float mY;

    public interface Callback {
        void onStickKeyEvent(KeyEvent event);
    }

    public AnalogStickTranslator(Callback callback) {
        mCallback = callback;
    }

    public void handle(MotionEvent event) {
        if (!isAnalogStick(event)) {
            return;
        }

        calculate(event);
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
        InputDevice mInputDevice = event.getDevice();

        // Calculate the horizontal distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat axis, or the right control stick.
        mX = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_X, historyPos);
        if (mX == 0) {
            mX = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_HAT_X, historyPos);
        }

        if (mX == 0) {
            mX = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_Z, historyPos);
        }

        // Calculate the vertical distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat switch, or the right control stick.
        mY = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Y, historyPos);
        if (mY == 0) {
            mY = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_HAT_Y, historyPos);
        }

        if (mY == 0){
            mY = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_RZ, historyPos);
        }

    }

    private static float getCenteredAxis(MotionEvent event, InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range = device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value =
                    historyPos < 0 ? event.getAxisValue(axis):
                            event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }

    private boolean isAnalogStick(MotionEvent event) {
        return (event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE;
    }
}
