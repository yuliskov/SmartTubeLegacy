package com.liskovsoft.smartyoutubetv.misc;

import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.liskovsoft.smartyoutubetv.misc.AnalogStickTranslator.Callback;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AnalogStickTranslatorTest implements Callback {
    private final float[][] LEFT = {{-0.001f, 0.0013f}, {-0.19f, 0.025f}, {-0.42f, 0.07f}, {-0.6f, 0.07f}, {-0.74f, 0.08f}, {-0.8f, 0.09f}, {-1.0f, 0.08f}};
    private final float[][] RIGHT = {{0.001f, -0.0013f}, {0.19f, -0.025f}, {0.42f, -0.07f}, {0.6f, -0.07f}, {0.74f, -0.08f}, {0.8f, -0.09f}, {1.0f, -0.08f}};
    private final float[][] UP = {{-0.0039f, 0.0195f}, {-0.01f, 0.103f}, {-0.019f, 0.39f}, {-0.025f, 0.64f}, {-0.025f, 0.83f}, {-0.025f, 0.88f}, {-0.03f, 1.0f}};
    private final float[][] DOWN = {{0.0039f, -0.0195f}, {0.01f, -0.103f}, {0.019f, -0.39f}, {0.025f, -0.64f}, {0.025f, -0.83f}, {0.025f, -0.88f}, {0.03f, -1.0f}};
    private final List<MotionEvent> mLeftAsc;
    private final List<MotionEvent> mLeftDesc;
    private final List<MotionEvent> mRightAsc;
    private final List<MotionEvent> mRightDesc;
    private final List<MotionEvent> mUpAsc;
    private final List<MotionEvent> mUpDesc;
    private final List<MotionEvent> mDownAsc;
    private final List<MotionEvent> mDownDesc;
    private AnalogStickTranslator mTranslator;
    private KeyEvent mLastKeyEvent;

    public AnalogStickTranslatorTest() {
        mLeftAsc = new ArrayList<>();
        mLeftDesc = new ArrayList<>();
        mRightAsc = new ArrayList<>();
        mRightDesc = new ArrayList<>();
        mUpAsc = new ArrayList<>();
        mUpDesc = new ArrayList<>();
        mDownAsc = new ArrayList<>();
        mDownDesc = new ArrayList<>();

        for (float[] event : LEFT) {
            mLeftAsc.add(initMotionEvent(event));
        }

        for (int i = (LEFT.length - 1); i >= 0; i--) { // reverse
            mLeftDesc.add(initMotionEvent(LEFT[i]));
        }

        for (float[] event : RIGHT) {
            mRightAsc.add(initMotionEvent(event));
        }

        for (int i = (RIGHT.length - 1); i >= 0; i--) { // reverse
            mRightDesc.add(initMotionEvent(RIGHT[i]));
        }

        for (float[] event : UP) {
            mUpAsc.add(initMotionEvent(event));
        }

        for (int i = (UP.length - 1); i >= 0; i--) { // reverse
            mUpDesc.add(initMotionEvent(UP[i]));
        }

        for (float[] event : DOWN) {
            mDownAsc.add(initMotionEvent(event));
        }

        for (int i = (DOWN.length - 1); i >= 0; i--) { // reverse
            mDownDesc.add(initMotionEvent(DOWN[i]));
        }
    }

    @Before
    public void setUp() {
        mTranslator = new AnalogStickTranslator(this);
    }

    @After
    public void tearDown() {
        mLastKeyEvent = null;
        mTranslator = null;
    }

    @Test
    public void moveStickLeftDescendant() {
        for (MotionEvent me : mLeftDesc) {
           mTranslator.handle(me);
        }

        assertNull("Event should NOT be produced when moving in descendant direction", mLastKeyEvent);
    }

    @Test
    public void moveStickLeftAscendant() {
        for (MotionEvent me : mLeftAsc) {
            mTranslator.handle(me);
        }

        assertTrue("Event is not null", mLastKeyEvent != null);
        assertTrue("Event should be produced when moving in ascendant direction", mLastKeyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT);
    }

    @Test
    public void moveStickRightDescendant() {
        for (MotionEvent me : mRightDesc) {
            mTranslator.handle(me);
        }

        assertNull("Event should NOT be produced when moving in descendant direction", mLastKeyEvent);
    }

    @Test
    public void moveStickRightAscendant() {
        for (MotionEvent me : mRightAsc) {
            mTranslator.handle(me);
        }

        assertTrue("Event is not null", mLastKeyEvent != null);
        assertTrue("Event should be produced when moving in ascendant direction", mLastKeyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT);
    }

    @Test
    public void moveStickUpDescendant() {
        for (MotionEvent me : mUpDesc) {
            mTranslator.handle(me);
        }

        assertNull("Event should NOT be produced when moving in descendant direction", mLastKeyEvent);
    }

    @Test
    public void moveStickUpAscendant() {
        for (MotionEvent me : mUpAsc) {
            mTranslator.handle(me);
        }

        assertTrue("Event is not null", mLastKeyEvent != null);
        assertTrue("Event should be produced when moving in ascendant direction", mLastKeyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP);
    }

    @Test
    public void moveStickDownDescendant() {
        for (MotionEvent me : mDownDesc) {
            mTranslator.handle(me);
        }

        assertNull("Event should NOT be produced when moving in descendant direction", mLastKeyEvent);
    }

    @Test
    public void moveStickDownAscendant() {
        for (MotionEvent me : mDownAsc) {
            mTranslator.handle(me);
        }

        assertTrue("Event is not null", mLastKeyEvent != null);
        assertTrue("Event should be produced when moving in ascendant direction", mLastKeyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN);
    }

    private MotionEvent initMotionEvent(float[] event) {
        MotionEvent newEvent = MotionEvent.obtain(0, System.currentTimeMillis(), MotionEvent.ACTION_MOVE, event[0], event[1], 0);
        newEvent.setSource(InputDevice.SOURCE_JOYSTICK);
        return newEvent;
    }

    @Override
    public void onAnalogStickKeyEvent(KeyEvent event) {
        mLastKeyEvent = event;
    }
}