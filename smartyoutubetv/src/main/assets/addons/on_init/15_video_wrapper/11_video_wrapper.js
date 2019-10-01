/**
 * Description:<br/>
 * Overrides volume property<br/>
 * Adds useful methods to video api<br/>
 * This methods have usage only in exoplayer context
 */

console.log("Scripts::Running script video_wrapper.js");

if (DeviceUtils.isExo()) {
    ElementWrapper.addHandler(new DummyVideoHandler());
}

