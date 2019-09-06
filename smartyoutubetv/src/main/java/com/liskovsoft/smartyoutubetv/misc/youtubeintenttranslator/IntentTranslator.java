package com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator;

import android.content.Intent;

interface IntentTranslator {
    Intent translate(Intent intent);
}
