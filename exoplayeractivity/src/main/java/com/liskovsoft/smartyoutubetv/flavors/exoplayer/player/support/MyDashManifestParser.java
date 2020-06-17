package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.net.Uri;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.ProgramInformation;
import com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement;

import java.util.List;

public class MyDashManifestParser extends DashManifestParser {
    @Override
    protected DashManifest buildMediaPresentationDescription(
            long availabilityStartTime,
            long durationMs,
            long minBufferTimeMs,
            boolean dynamic,
            long minUpdateTimeMs,
            long timeShiftBufferDepthMs,
            long suggestedPresentationDelayMs,
            long publishTimeMs,
            ProgramInformation programInformation,
            UtcTimingElement utcTiming,
            Uri location,
            List<Period> periods) {
        return new DashManifest(
                availabilityStartTime,
                durationMs,
                minBufferTimeMs,
                false,
                minUpdateTimeMs,
                timeShiftBufferDepthMs,
                suggestedPresentationDelayMs,
                publishTimeMs,
                programInformation,
                utcTiming,
                location,
                periods);
    }
}
