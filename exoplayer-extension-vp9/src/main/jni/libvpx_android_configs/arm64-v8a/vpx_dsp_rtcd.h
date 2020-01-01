// This file is generated. Do not edit.
#ifndef VPX_DSP_RTCD_H_
#define VPX_DSP_RTCD_H_

#ifdef RTCD_C
#define RTCD_EXTERN
#else
#define RTCD_EXTERN extern
#endif

/*
 * DSP
 */

#include "vpx/vpx_integer.h"
#include "vpx_dsp/vpx_dsp_common.h"
#include "vpx_dsp/vpx_filter.h"


#ifdef __cplusplus
extern "C" {
#endif

void vpx_convolve8_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve8_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8 vpx_convolve8_neon

void vpx_convolve8_avg_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve8_avg_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_avg vpx_convolve8_avg_neon

void vpx_convolve8_avg_horiz_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve8_avg_horiz_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_avg_horiz vpx_convolve8_avg_horiz_neon

void vpx_convolve8_avg_vert_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve8_avg_vert_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_avg_vert vpx_convolve8_avg_vert_neon

void vpx_convolve8_horiz_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve8_horiz_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_horiz vpx_convolve8_horiz_neon

void vpx_convolve8_vert_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve8_vert_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_vert vpx_convolve8_vert_neon

void vpx_convolve_avg_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve_avg_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve_avg vpx_convolve_avg_neon

void vpx_convolve_copy_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_convolve_copy_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve_copy vpx_convolve_copy_neon

void vpx_d117_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_16x16 vpx_d117_predictor_16x16_c

void vpx_d117_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_32x32 vpx_d117_predictor_32x32_c

void vpx_d117_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_4x4 vpx_d117_predictor_4x4_c

void vpx_d117_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_8x8 vpx_d117_predictor_8x8_c

void vpx_d135_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d135_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_16x16 vpx_d135_predictor_16x16_neon

void vpx_d135_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d135_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_32x32 vpx_d135_predictor_32x32_neon

void vpx_d135_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d135_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_4x4 vpx_d135_predictor_4x4_neon

void vpx_d135_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d135_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_8x8 vpx_d135_predictor_8x8_neon

void vpx_d153_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d153_predictor_16x16 vpx_d153_predictor_16x16_c

void vpx_d153_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d153_predictor_32x32 vpx_d153_predictor_32x32_c

void vpx_d153_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d153_predictor_4x4 vpx_d153_predictor_4x4_c

void vpx_d153_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d153_predictor_8x8 vpx_d153_predictor_8x8_c

void vpx_d207_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d207_predictor_16x16 vpx_d207_predictor_16x16_c

void vpx_d207_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d207_predictor_32x32 vpx_d207_predictor_32x32_c

void vpx_d207_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d207_predictor_4x4 vpx_d207_predictor_4x4_c

void vpx_d207_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d207_predictor_8x8 vpx_d207_predictor_8x8_c

void vpx_d45_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d45_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45_predictor_16x16 vpx_d45_predictor_16x16_neon

void vpx_d45_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d45_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45_predictor_32x32 vpx_d45_predictor_32x32_neon

void vpx_d45_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d45_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45_predictor_4x4 vpx_d45_predictor_4x4_neon

void vpx_d45_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_d45_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45_predictor_8x8 vpx_d45_predictor_8x8_neon

void vpx_d45e_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45e_predictor_4x4 vpx_d45e_predictor_4x4_c

void vpx_d63_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d63_predictor_16x16 vpx_d63_predictor_16x16_c

void vpx_d63_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d63_predictor_32x32 vpx_d63_predictor_32x32_c

void vpx_d63_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d63_predictor_4x4 vpx_d63_predictor_4x4_c

void vpx_d63_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d63_predictor_8x8 vpx_d63_predictor_8x8_c

void vpx_d63e_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d63e_predictor_4x4 vpx_d63e_predictor_4x4_c

void vpx_dc_128_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_128_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_128_predictor_16x16 vpx_dc_128_predictor_16x16_neon

void vpx_dc_128_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_128_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_128_predictor_32x32 vpx_dc_128_predictor_32x32_neon

void vpx_dc_128_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_128_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_128_predictor_4x4 vpx_dc_128_predictor_4x4_neon

void vpx_dc_128_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_128_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_128_predictor_8x8 vpx_dc_128_predictor_8x8_neon

void vpx_dc_left_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_left_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_16x16 vpx_dc_left_predictor_16x16_neon

void vpx_dc_left_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_left_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_32x32 vpx_dc_left_predictor_32x32_neon

void vpx_dc_left_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_left_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_4x4 vpx_dc_left_predictor_4x4_neon

void vpx_dc_left_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_left_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_8x8 vpx_dc_left_predictor_8x8_neon

void vpx_dc_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_16x16 vpx_dc_predictor_16x16_neon

void vpx_dc_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_32x32 vpx_dc_predictor_32x32_neon

void vpx_dc_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_4x4 vpx_dc_predictor_4x4_neon

void vpx_dc_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_8x8 vpx_dc_predictor_8x8_neon

void vpx_dc_top_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_top_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_16x16 vpx_dc_top_predictor_16x16_neon

void vpx_dc_top_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_top_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_32x32 vpx_dc_top_predictor_32x32_neon

void vpx_dc_top_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_top_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_4x4 vpx_dc_top_predictor_4x4_neon

void vpx_dc_top_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_dc_top_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_8x8 vpx_dc_top_predictor_8x8_neon

void vpx_h_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_h_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_16x16 vpx_h_predictor_16x16_neon

void vpx_h_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_h_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_32x32 vpx_h_predictor_32x32_neon

void vpx_h_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_h_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_4x4 vpx_h_predictor_4x4_neon

void vpx_h_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_h_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_8x8 vpx_h_predictor_8x8_neon

void vpx_he_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_he_predictor_4x4 vpx_he_predictor_4x4_c

void vpx_highbd_convolve8_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve8_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8 vpx_highbd_convolve8_neon

void vpx_highbd_convolve8_avg_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve8_avg_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_avg vpx_highbd_convolve8_avg_neon

void vpx_highbd_convolve8_avg_horiz_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve8_avg_horiz_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_avg_horiz vpx_highbd_convolve8_avg_horiz_neon

void vpx_highbd_convolve8_avg_vert_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve8_avg_vert_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_avg_vert vpx_highbd_convolve8_avg_vert_neon

void vpx_highbd_convolve8_horiz_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve8_horiz_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_horiz vpx_highbd_convolve8_horiz_neon

void vpx_highbd_convolve8_vert_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve8_vert_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_vert vpx_highbd_convolve8_vert_neon

void vpx_highbd_convolve_avg_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve_avg_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve_avg vpx_highbd_convolve_avg_neon

void vpx_highbd_convolve_copy_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
void vpx_highbd_convolve_copy_neon(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve_copy vpx_highbd_convolve_copy_neon

void vpx_highbd_d117_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_16x16 vpx_highbd_d117_predictor_16x16_c

void vpx_highbd_d117_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_32x32 vpx_highbd_d117_predictor_32x32_c

void vpx_highbd_d117_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_4x4 vpx_highbd_d117_predictor_4x4_c

void vpx_highbd_d117_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_8x8 vpx_highbd_d117_predictor_8x8_c

void vpx_highbd_d135_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d135_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_16x16 vpx_highbd_d135_predictor_16x16_neon

void vpx_highbd_d135_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d135_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_32x32 vpx_highbd_d135_predictor_32x32_neon

void vpx_highbd_d135_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d135_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_4x4 vpx_highbd_d135_predictor_4x4_neon

void vpx_highbd_d135_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d135_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_8x8 vpx_highbd_d135_predictor_8x8_neon

void vpx_highbd_d153_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d153_predictor_16x16 vpx_highbd_d153_predictor_16x16_c

void vpx_highbd_d153_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d153_predictor_32x32 vpx_highbd_d153_predictor_32x32_c

void vpx_highbd_d153_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d153_predictor_4x4 vpx_highbd_d153_predictor_4x4_c

void vpx_highbd_d153_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d153_predictor_8x8 vpx_highbd_d153_predictor_8x8_c

void vpx_highbd_d207_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d207_predictor_16x16 vpx_highbd_d207_predictor_16x16_c

void vpx_highbd_d207_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d207_predictor_32x32 vpx_highbd_d207_predictor_32x32_c

void vpx_highbd_d207_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d207_predictor_4x4 vpx_highbd_d207_predictor_4x4_c

void vpx_highbd_d207_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d207_predictor_8x8 vpx_highbd_d207_predictor_8x8_c

void vpx_highbd_d45_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d45_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d45_predictor_16x16 vpx_highbd_d45_predictor_16x16_neon

void vpx_highbd_d45_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d45_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d45_predictor_32x32 vpx_highbd_d45_predictor_32x32_neon

void vpx_highbd_d45_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d45_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d45_predictor_4x4 vpx_highbd_d45_predictor_4x4_neon

void vpx_highbd_d45_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_d45_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d45_predictor_8x8 vpx_highbd_d45_predictor_8x8_neon

void vpx_highbd_d63_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_16x16 vpx_highbd_d63_predictor_16x16_c

void vpx_highbd_d63_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_32x32 vpx_highbd_d63_predictor_32x32_c

void vpx_highbd_d63_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_4x4 vpx_highbd_d63_predictor_4x4_c

void vpx_highbd_d63_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_8x8 vpx_highbd_d63_predictor_8x8_c

void vpx_highbd_dc_128_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_128_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_16x16 vpx_highbd_dc_128_predictor_16x16_neon

void vpx_highbd_dc_128_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_128_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_32x32 vpx_highbd_dc_128_predictor_32x32_neon

void vpx_highbd_dc_128_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_128_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_4x4 vpx_highbd_dc_128_predictor_4x4_neon

void vpx_highbd_dc_128_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_128_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_8x8 vpx_highbd_dc_128_predictor_8x8_neon

void vpx_highbd_dc_left_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_left_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_16x16 vpx_highbd_dc_left_predictor_16x16_neon

void vpx_highbd_dc_left_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_left_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_32x32 vpx_highbd_dc_left_predictor_32x32_neon

void vpx_highbd_dc_left_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_left_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_4x4 vpx_highbd_dc_left_predictor_4x4_neon

void vpx_highbd_dc_left_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_left_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_8x8 vpx_highbd_dc_left_predictor_8x8_neon

void vpx_highbd_dc_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_16x16 vpx_highbd_dc_predictor_16x16_neon

void vpx_highbd_dc_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_32x32 vpx_highbd_dc_predictor_32x32_neon

void vpx_highbd_dc_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_4x4 vpx_highbd_dc_predictor_4x4_neon

void vpx_highbd_dc_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_8x8 vpx_highbd_dc_predictor_8x8_neon

void vpx_highbd_dc_top_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_top_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_16x16 vpx_highbd_dc_top_predictor_16x16_neon

void vpx_highbd_dc_top_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_top_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_32x32 vpx_highbd_dc_top_predictor_32x32_neon

void vpx_highbd_dc_top_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_top_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_4x4 vpx_highbd_dc_top_predictor_4x4_neon

void vpx_highbd_dc_top_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_dc_top_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_8x8 vpx_highbd_dc_top_predictor_8x8_neon

void vpx_highbd_h_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_h_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_16x16 vpx_highbd_h_predictor_16x16_neon

void vpx_highbd_h_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_h_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_32x32 vpx_highbd_h_predictor_32x32_neon

void vpx_highbd_h_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_h_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_4x4 vpx_highbd_h_predictor_4x4_neon

void vpx_highbd_h_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_h_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_8x8 vpx_highbd_h_predictor_8x8_neon

void vpx_highbd_idct16x16_10_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct16x16_10_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_10_add vpx_highbd_idct16x16_10_add_neon

void vpx_highbd_idct16x16_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct16x16_1_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_1_add vpx_highbd_idct16x16_1_add_neon

void vpx_highbd_idct16x16_256_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct16x16_256_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_256_add vpx_highbd_idct16x16_256_add_neon

void vpx_highbd_idct16x16_38_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct16x16_38_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_38_add vpx_highbd_idct16x16_38_add_neon

void vpx_highbd_idct32x32_1024_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct32x32_1024_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_1024_add vpx_highbd_idct32x32_1024_add_neon

void vpx_highbd_idct32x32_135_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct32x32_135_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_135_add vpx_highbd_idct32x32_135_add_neon

void vpx_highbd_idct32x32_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct32x32_1_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_1_add vpx_highbd_idct32x32_1_add_neon

void vpx_highbd_idct32x32_34_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct32x32_34_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_34_add vpx_highbd_idct32x32_34_add_neon

void vpx_highbd_idct4x4_16_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct4x4_16_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct4x4_16_add vpx_highbd_idct4x4_16_add_neon

void vpx_highbd_idct4x4_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct4x4_1_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct4x4_1_add vpx_highbd_idct4x4_1_add_neon

void vpx_highbd_idct8x8_12_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct8x8_12_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct8x8_12_add vpx_highbd_idct8x8_12_add_neon

void vpx_highbd_idct8x8_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct8x8_1_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct8x8_1_add vpx_highbd_idct8x8_1_add_neon

void vpx_highbd_idct8x8_64_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
void vpx_highbd_idct8x8_64_add_neon(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct8x8_64_add vpx_highbd_idct8x8_64_add_neon

void vpx_highbd_iwht4x4_16_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_iwht4x4_16_add vpx_highbd_iwht4x4_16_add_c

void vpx_highbd_iwht4x4_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_iwht4x4_1_add vpx_highbd_iwht4x4_1_add_c

void vpx_highbd_lpf_horizontal_16_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_horizontal_16_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_16 vpx_highbd_lpf_horizontal_16_neon

void vpx_highbd_lpf_horizontal_16_dual_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_horizontal_16_dual_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_16_dual vpx_highbd_lpf_horizontal_16_dual_neon

void vpx_highbd_lpf_horizontal_4_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_horizontal_4_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_4 vpx_highbd_lpf_horizontal_4_neon

void vpx_highbd_lpf_horizontal_4_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
void vpx_highbd_lpf_horizontal_4_dual_neon(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_horizontal_4_dual vpx_highbd_lpf_horizontal_4_dual_neon

void vpx_highbd_lpf_horizontal_8_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_horizontal_8_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_8 vpx_highbd_lpf_horizontal_8_neon

void vpx_highbd_lpf_horizontal_8_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
void vpx_highbd_lpf_horizontal_8_dual_neon(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_horizontal_8_dual vpx_highbd_lpf_horizontal_8_dual_neon

void vpx_highbd_lpf_vertical_16_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_vertical_16_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_16 vpx_highbd_lpf_vertical_16_neon

void vpx_highbd_lpf_vertical_16_dual_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_vertical_16_dual_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_16_dual vpx_highbd_lpf_vertical_16_dual_neon

void vpx_highbd_lpf_vertical_4_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_vertical_4_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_4 vpx_highbd_lpf_vertical_4_neon

void vpx_highbd_lpf_vertical_4_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
void vpx_highbd_lpf_vertical_4_dual_neon(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_vertical_4_dual vpx_highbd_lpf_vertical_4_dual_neon

void vpx_highbd_lpf_vertical_8_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
void vpx_highbd_lpf_vertical_8_neon(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_8 vpx_highbd_lpf_vertical_8_neon

void vpx_highbd_lpf_vertical_8_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
void vpx_highbd_lpf_vertical_8_dual_neon(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_vertical_8_dual vpx_highbd_lpf_vertical_8_dual_neon

void vpx_highbd_tm_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_tm_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_16x16 vpx_highbd_tm_predictor_16x16_neon

void vpx_highbd_tm_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_tm_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_32x32 vpx_highbd_tm_predictor_32x32_neon

void vpx_highbd_tm_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_tm_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_4x4 vpx_highbd_tm_predictor_4x4_neon

void vpx_highbd_tm_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_tm_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_8x8 vpx_highbd_tm_predictor_8x8_neon

void vpx_highbd_v_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_v_predictor_16x16_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_16x16 vpx_highbd_v_predictor_16x16_neon

void vpx_highbd_v_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_v_predictor_32x32_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_32x32 vpx_highbd_v_predictor_32x32_neon

void vpx_highbd_v_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_v_predictor_4x4_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_4x4 vpx_highbd_v_predictor_4x4_neon

void vpx_highbd_v_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
void vpx_highbd_v_predictor_8x8_neon(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_8x8 vpx_highbd_v_predictor_8x8_neon

void vpx_idct16x16_10_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct16x16_10_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_10_add vpx_idct16x16_10_add_neon

void vpx_idct16x16_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct16x16_1_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_1_add vpx_idct16x16_1_add_neon

void vpx_idct16x16_256_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct16x16_256_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_256_add vpx_idct16x16_256_add_neon

void vpx_idct16x16_38_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct16x16_38_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_38_add vpx_idct16x16_38_add_neon

void vpx_idct32x32_1024_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct32x32_1024_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_1024_add vpx_idct32x32_1024_add_neon

void vpx_idct32x32_135_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct32x32_135_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_135_add vpx_idct32x32_135_add_neon

void vpx_idct32x32_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct32x32_1_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_1_add vpx_idct32x32_1_add_neon

void vpx_idct32x32_34_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct32x32_34_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_34_add vpx_idct32x32_34_add_neon

void vpx_idct4x4_16_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct4x4_16_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct4x4_16_add vpx_idct4x4_16_add_neon

void vpx_idct4x4_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct4x4_1_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct4x4_1_add vpx_idct4x4_1_add_neon

void vpx_idct8x8_12_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct8x8_12_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct8x8_12_add vpx_idct8x8_12_add_neon

void vpx_idct8x8_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct8x8_1_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct8x8_1_add vpx_idct8x8_1_add_neon

void vpx_idct8x8_64_add_c(const tran_low_t *input, uint8_t *dest, int stride);
void vpx_idct8x8_64_add_neon(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct8x8_64_add vpx_idct8x8_64_add_neon

void vpx_iwht4x4_16_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_iwht4x4_16_add vpx_iwht4x4_16_add_c

void vpx_iwht4x4_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_iwht4x4_1_add vpx_iwht4x4_1_add_c

void vpx_lpf_horizontal_16_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_horizontal_16_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_16 vpx_lpf_horizontal_16_neon

void vpx_lpf_horizontal_16_dual_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_horizontal_16_dual_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_16_dual vpx_lpf_horizontal_16_dual_neon

void vpx_lpf_horizontal_4_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_horizontal_4_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_4 vpx_lpf_horizontal_4_neon

void vpx_lpf_horizontal_4_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
void vpx_lpf_horizontal_4_dual_neon(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_horizontal_4_dual vpx_lpf_horizontal_4_dual_neon

void vpx_lpf_horizontal_8_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_horizontal_8_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_8 vpx_lpf_horizontal_8_neon

void vpx_lpf_horizontal_8_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
void vpx_lpf_horizontal_8_dual_neon(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_horizontal_8_dual vpx_lpf_horizontal_8_dual_neon

void vpx_lpf_vertical_16_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_vertical_16_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_16 vpx_lpf_vertical_16_neon

void vpx_lpf_vertical_16_dual_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_vertical_16_dual_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_16_dual vpx_lpf_vertical_16_dual_neon

void vpx_lpf_vertical_4_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_vertical_4_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_4 vpx_lpf_vertical_4_neon

void vpx_lpf_vertical_4_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
void vpx_lpf_vertical_4_dual_neon(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_vertical_4_dual vpx_lpf_vertical_4_dual_neon

void vpx_lpf_vertical_8_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
void vpx_lpf_vertical_8_neon(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_8 vpx_lpf_vertical_8_neon

void vpx_lpf_vertical_8_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
void vpx_lpf_vertical_8_dual_neon(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_vertical_8_dual vpx_lpf_vertical_8_dual_neon

void vpx_scaled_2d_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
void vpx_scaled_2d_neon(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_scaled_2d vpx_scaled_2d_neon

void vpx_scaled_avg_2d_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_scaled_avg_2d vpx_scaled_avg_2d_c

void vpx_scaled_avg_horiz_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_scaled_avg_horiz vpx_scaled_avg_horiz_c

void vpx_scaled_avg_vert_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_scaled_avg_vert vpx_scaled_avg_vert_c

void vpx_scaled_horiz_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_scaled_horiz vpx_scaled_horiz_c

void vpx_scaled_vert_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_scaled_vert vpx_scaled_vert_c

void vpx_tm_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_tm_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_16x16 vpx_tm_predictor_16x16_neon

void vpx_tm_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_tm_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_32x32 vpx_tm_predictor_32x32_neon

void vpx_tm_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_tm_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_4x4 vpx_tm_predictor_4x4_neon

void vpx_tm_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_tm_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_8x8 vpx_tm_predictor_8x8_neon

void vpx_v_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_v_predictor_16x16_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_16x16 vpx_v_predictor_16x16_neon

void vpx_v_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_v_predictor_32x32_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_32x32 vpx_v_predictor_32x32_neon

void vpx_v_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_v_predictor_4x4_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_4x4 vpx_v_predictor_4x4_neon

void vpx_v_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
void vpx_v_predictor_8x8_neon(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_8x8 vpx_v_predictor_8x8_neon

void vpx_ve_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_ve_predictor_4x4 vpx_ve_predictor_4x4_c

void vpx_dsp_rtcd(void);

#include "vpx_config.h"

#ifdef RTCD_C
#include "vpx_ports/arm.h"
static void setup_rtcd_internal(void)
{
    int flags = arm_cpu_caps();

    (void)flags;

}
#endif

#ifdef __cplusplus
}  // extern "C"
#endif

#endif
