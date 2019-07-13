function VolumeHandler() {
    this.TAG = 'VolumeHandler';

    this.onCreate = function(video) {
        this.overrideVolumeProp(video);
    };

    this.overrideVolumeProp = function(video) {
        Log.d(this.TAG, "Overriding video's volume property");
        Utils.overrideProp(video, 'volume', 0);
    };
}
