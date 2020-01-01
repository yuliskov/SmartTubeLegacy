/* Copyright (c) 2011 The WebM project authors. All Rights Reserved. */
/*  */
/* Use of this source code is governed by a BSD-style license */
/* that can be found in the LICENSE file in the root of the source */
/* tree. An additional intellectual property rights grant can be found */
/* in the file PATENTS.  All contributing project authors may */
/* be found in the AUTHORS file in the root of the source tree. */
#include "vpx/vpx_codec.h"
static const char* const cfg = "--force-target=x86-android-gcc --disable-sse2 --disable-sse3 --disable-ssse3 --disable-sse4_1 --disable-avx --disable-avx2 --enable-pic --disable-examples --disable-docs --enable-realtime-only --disable-vp8 --disable-vp9-encoder --disable-webm-io --disable-libyuv --disable-runtime-cpu-detect --enable-external-build --enable-vp9-highbitdepth";
const char *vpx_codec_build_config(void) {return cfg;}
