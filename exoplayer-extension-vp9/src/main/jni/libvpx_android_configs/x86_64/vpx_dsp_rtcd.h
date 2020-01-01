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

void vpx_comp_avg_pred_c(uint8_t *comp_pred, const uint8_t *pred, int width, int height, const uint8_t *ref, int ref_stride);
#define vpx_comp_avg_pred vpx_comp_avg_pred_c

void vpx_convolve8_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8 vpx_convolve8_c

void vpx_convolve8_avg_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_avg vpx_convolve8_avg_c

void vpx_convolve8_avg_horiz_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_avg_horiz vpx_convolve8_avg_horiz_c

void vpx_convolve8_avg_vert_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_avg_vert vpx_convolve8_avg_vert_c

void vpx_convolve8_horiz_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_horiz vpx_convolve8_horiz_c

void vpx_convolve8_vert_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve8_vert vpx_convolve8_vert_c

void vpx_convolve_avg_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve_avg vpx_convolve_avg_c

void vpx_convolve_copy_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_convolve_copy vpx_convolve_copy_c

void vpx_d117_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_16x16 vpx_d117_predictor_16x16_c

void vpx_d117_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_32x32 vpx_d117_predictor_32x32_c

void vpx_d117_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_4x4 vpx_d117_predictor_4x4_c

void vpx_d117_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d117_predictor_8x8 vpx_d117_predictor_8x8_c

void vpx_d135_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_16x16 vpx_d135_predictor_16x16_c

void vpx_d135_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_32x32 vpx_d135_predictor_32x32_c

void vpx_d135_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_4x4 vpx_d135_predictor_4x4_c

void vpx_d135_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d135_predictor_8x8 vpx_d135_predictor_8x8_c

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
#define vpx_d45_predictor_16x16 vpx_d45_predictor_16x16_c

void vpx_d45_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45_predictor_32x32 vpx_d45_predictor_32x32_c

void vpx_d45_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45_predictor_4x4 vpx_d45_predictor_4x4_c

void vpx_d45_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_d45_predictor_8x8 vpx_d45_predictor_8x8_c

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
#define vpx_dc_128_predictor_16x16 vpx_dc_128_predictor_16x16_c

void vpx_dc_128_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_128_predictor_32x32 vpx_dc_128_predictor_32x32_c

void vpx_dc_128_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_128_predictor_4x4 vpx_dc_128_predictor_4x4_c

void vpx_dc_128_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_128_predictor_8x8 vpx_dc_128_predictor_8x8_c

void vpx_dc_left_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_16x16 vpx_dc_left_predictor_16x16_c

void vpx_dc_left_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_32x32 vpx_dc_left_predictor_32x32_c

void vpx_dc_left_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_4x4 vpx_dc_left_predictor_4x4_c

void vpx_dc_left_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_left_predictor_8x8 vpx_dc_left_predictor_8x8_c

void vpx_dc_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_16x16 vpx_dc_predictor_16x16_c

void vpx_dc_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_32x32 vpx_dc_predictor_32x32_c

void vpx_dc_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_4x4 vpx_dc_predictor_4x4_c

void vpx_dc_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_predictor_8x8 vpx_dc_predictor_8x8_c

void vpx_dc_top_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_16x16 vpx_dc_top_predictor_16x16_c

void vpx_dc_top_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_32x32 vpx_dc_top_predictor_32x32_c

void vpx_dc_top_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_4x4 vpx_dc_top_predictor_4x4_c

void vpx_dc_top_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_dc_top_predictor_8x8 vpx_dc_top_predictor_8x8_c

void vpx_get16x16var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_get16x16var vpx_get16x16var_c

unsigned int vpx_get4x4sse_cs_c(const unsigned char *src_ptr, int src_stride, const unsigned char *ref_ptr, int ref_stride);
#define vpx_get4x4sse_cs vpx_get4x4sse_cs_c

void vpx_get8x8var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_get8x8var vpx_get8x8var_c

unsigned int vpx_get_mb_ss_c(const int16_t *);
#define vpx_get_mb_ss vpx_get_mb_ss_c

void vpx_h_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_16x16 vpx_h_predictor_16x16_c

void vpx_h_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_32x32 vpx_h_predictor_32x32_c

void vpx_h_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_4x4 vpx_h_predictor_4x4_c

void vpx_h_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_h_predictor_8x8 vpx_h_predictor_8x8_c

void vpx_he_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_he_predictor_4x4 vpx_he_predictor_4x4_c

void vpx_highbd_10_get16x16var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_highbd_10_get16x16var vpx_highbd_10_get16x16var_c

void vpx_highbd_10_get8x8var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_highbd_10_get8x8var vpx_highbd_10_get8x8var_c

unsigned int vpx_highbd_10_mse16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_mse16x16 vpx_highbd_10_mse16x16_c

unsigned int vpx_highbd_10_mse16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_mse16x8 vpx_highbd_10_mse16x8_c

unsigned int vpx_highbd_10_mse8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_mse8x16 vpx_highbd_10_mse8x16_c

unsigned int vpx_highbd_10_mse8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_mse8x8 vpx_highbd_10_mse8x8_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance16x16 vpx_highbd_10_sub_pixel_avg_variance16x16_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance16x32 vpx_highbd_10_sub_pixel_avg_variance16x32_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance16x8 vpx_highbd_10_sub_pixel_avg_variance16x8_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance32x16 vpx_highbd_10_sub_pixel_avg_variance32x16_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance32x32 vpx_highbd_10_sub_pixel_avg_variance32x32_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance32x64 vpx_highbd_10_sub_pixel_avg_variance32x64_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance4x4 vpx_highbd_10_sub_pixel_avg_variance4x4_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance4x8 vpx_highbd_10_sub_pixel_avg_variance4x8_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance64x32 vpx_highbd_10_sub_pixel_avg_variance64x32_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance64x64 vpx_highbd_10_sub_pixel_avg_variance64x64_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance8x16 vpx_highbd_10_sub_pixel_avg_variance8x16_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance8x4 vpx_highbd_10_sub_pixel_avg_variance8x4_c

uint32_t vpx_highbd_10_sub_pixel_avg_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_10_sub_pixel_avg_variance8x8 vpx_highbd_10_sub_pixel_avg_variance8x8_c

uint32_t vpx_highbd_10_sub_pixel_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance16x16 vpx_highbd_10_sub_pixel_variance16x16_c

uint32_t vpx_highbd_10_sub_pixel_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance16x32 vpx_highbd_10_sub_pixel_variance16x32_c

uint32_t vpx_highbd_10_sub_pixel_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance16x8 vpx_highbd_10_sub_pixel_variance16x8_c

uint32_t vpx_highbd_10_sub_pixel_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance32x16 vpx_highbd_10_sub_pixel_variance32x16_c

uint32_t vpx_highbd_10_sub_pixel_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance32x32 vpx_highbd_10_sub_pixel_variance32x32_c

uint32_t vpx_highbd_10_sub_pixel_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance32x64 vpx_highbd_10_sub_pixel_variance32x64_c

uint32_t vpx_highbd_10_sub_pixel_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance4x4 vpx_highbd_10_sub_pixel_variance4x4_c

uint32_t vpx_highbd_10_sub_pixel_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance4x8 vpx_highbd_10_sub_pixel_variance4x8_c

uint32_t vpx_highbd_10_sub_pixel_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance64x32 vpx_highbd_10_sub_pixel_variance64x32_c

uint32_t vpx_highbd_10_sub_pixel_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance64x64 vpx_highbd_10_sub_pixel_variance64x64_c

uint32_t vpx_highbd_10_sub_pixel_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance8x16 vpx_highbd_10_sub_pixel_variance8x16_c

uint32_t vpx_highbd_10_sub_pixel_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance8x4 vpx_highbd_10_sub_pixel_variance8x4_c

uint32_t vpx_highbd_10_sub_pixel_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_10_sub_pixel_variance8x8 vpx_highbd_10_sub_pixel_variance8x8_c

unsigned int vpx_highbd_10_variance16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance16x16 vpx_highbd_10_variance16x16_c

unsigned int vpx_highbd_10_variance16x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance16x32 vpx_highbd_10_variance16x32_c

unsigned int vpx_highbd_10_variance16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance16x8 vpx_highbd_10_variance16x8_c

unsigned int vpx_highbd_10_variance32x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance32x16 vpx_highbd_10_variance32x16_c

unsigned int vpx_highbd_10_variance32x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance32x32 vpx_highbd_10_variance32x32_c

unsigned int vpx_highbd_10_variance32x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance32x64 vpx_highbd_10_variance32x64_c

unsigned int vpx_highbd_10_variance4x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance4x4 vpx_highbd_10_variance4x4_c

unsigned int vpx_highbd_10_variance4x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance4x8 vpx_highbd_10_variance4x8_c

unsigned int vpx_highbd_10_variance64x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance64x32 vpx_highbd_10_variance64x32_c

unsigned int vpx_highbd_10_variance64x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance64x64 vpx_highbd_10_variance64x64_c

unsigned int vpx_highbd_10_variance8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance8x16 vpx_highbd_10_variance8x16_c

unsigned int vpx_highbd_10_variance8x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance8x4 vpx_highbd_10_variance8x4_c

unsigned int vpx_highbd_10_variance8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_10_variance8x8 vpx_highbd_10_variance8x8_c

void vpx_highbd_12_get16x16var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_highbd_12_get16x16var vpx_highbd_12_get16x16var_c

void vpx_highbd_12_get8x8var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_highbd_12_get8x8var vpx_highbd_12_get8x8var_c

unsigned int vpx_highbd_12_mse16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_mse16x16 vpx_highbd_12_mse16x16_c

unsigned int vpx_highbd_12_mse16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_mse16x8 vpx_highbd_12_mse16x8_c

unsigned int vpx_highbd_12_mse8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_mse8x16 vpx_highbd_12_mse8x16_c

unsigned int vpx_highbd_12_mse8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_mse8x8 vpx_highbd_12_mse8x8_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance16x16 vpx_highbd_12_sub_pixel_avg_variance16x16_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance16x32 vpx_highbd_12_sub_pixel_avg_variance16x32_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance16x8 vpx_highbd_12_sub_pixel_avg_variance16x8_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance32x16 vpx_highbd_12_sub_pixel_avg_variance32x16_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance32x32 vpx_highbd_12_sub_pixel_avg_variance32x32_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance32x64 vpx_highbd_12_sub_pixel_avg_variance32x64_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance4x4 vpx_highbd_12_sub_pixel_avg_variance4x4_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance4x8 vpx_highbd_12_sub_pixel_avg_variance4x8_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance64x32 vpx_highbd_12_sub_pixel_avg_variance64x32_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance64x64 vpx_highbd_12_sub_pixel_avg_variance64x64_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance8x16 vpx_highbd_12_sub_pixel_avg_variance8x16_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance8x4 vpx_highbd_12_sub_pixel_avg_variance8x4_c

uint32_t vpx_highbd_12_sub_pixel_avg_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_12_sub_pixel_avg_variance8x8 vpx_highbd_12_sub_pixel_avg_variance8x8_c

uint32_t vpx_highbd_12_sub_pixel_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance16x16 vpx_highbd_12_sub_pixel_variance16x16_c

uint32_t vpx_highbd_12_sub_pixel_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance16x32 vpx_highbd_12_sub_pixel_variance16x32_c

uint32_t vpx_highbd_12_sub_pixel_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance16x8 vpx_highbd_12_sub_pixel_variance16x8_c

uint32_t vpx_highbd_12_sub_pixel_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance32x16 vpx_highbd_12_sub_pixel_variance32x16_c

uint32_t vpx_highbd_12_sub_pixel_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance32x32 vpx_highbd_12_sub_pixel_variance32x32_c

uint32_t vpx_highbd_12_sub_pixel_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance32x64 vpx_highbd_12_sub_pixel_variance32x64_c

uint32_t vpx_highbd_12_sub_pixel_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance4x4 vpx_highbd_12_sub_pixel_variance4x4_c

uint32_t vpx_highbd_12_sub_pixel_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance4x8 vpx_highbd_12_sub_pixel_variance4x8_c

uint32_t vpx_highbd_12_sub_pixel_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance64x32 vpx_highbd_12_sub_pixel_variance64x32_c

uint32_t vpx_highbd_12_sub_pixel_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance64x64 vpx_highbd_12_sub_pixel_variance64x64_c

uint32_t vpx_highbd_12_sub_pixel_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance8x16 vpx_highbd_12_sub_pixel_variance8x16_c

uint32_t vpx_highbd_12_sub_pixel_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance8x4 vpx_highbd_12_sub_pixel_variance8x4_c

uint32_t vpx_highbd_12_sub_pixel_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_12_sub_pixel_variance8x8 vpx_highbd_12_sub_pixel_variance8x8_c

unsigned int vpx_highbd_12_variance16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance16x16 vpx_highbd_12_variance16x16_c

unsigned int vpx_highbd_12_variance16x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance16x32 vpx_highbd_12_variance16x32_c

unsigned int vpx_highbd_12_variance16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance16x8 vpx_highbd_12_variance16x8_c

unsigned int vpx_highbd_12_variance32x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance32x16 vpx_highbd_12_variance32x16_c

unsigned int vpx_highbd_12_variance32x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance32x32 vpx_highbd_12_variance32x32_c

unsigned int vpx_highbd_12_variance32x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance32x64 vpx_highbd_12_variance32x64_c

unsigned int vpx_highbd_12_variance4x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance4x4 vpx_highbd_12_variance4x4_c

unsigned int vpx_highbd_12_variance4x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance4x8 vpx_highbd_12_variance4x8_c

unsigned int vpx_highbd_12_variance64x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance64x32 vpx_highbd_12_variance64x32_c

unsigned int vpx_highbd_12_variance64x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance64x64 vpx_highbd_12_variance64x64_c

unsigned int vpx_highbd_12_variance8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance8x16 vpx_highbd_12_variance8x16_c

unsigned int vpx_highbd_12_variance8x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance8x4 vpx_highbd_12_variance8x4_c

unsigned int vpx_highbd_12_variance8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_12_variance8x8 vpx_highbd_12_variance8x8_c

void vpx_highbd_8_get16x16var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_highbd_8_get16x16var vpx_highbd_8_get16x16var_c

void vpx_highbd_8_get8x8var_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse, int *sum);
#define vpx_highbd_8_get8x8var vpx_highbd_8_get8x8var_c

unsigned int vpx_highbd_8_mse16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_mse16x16 vpx_highbd_8_mse16x16_c

unsigned int vpx_highbd_8_mse16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_mse16x8 vpx_highbd_8_mse16x8_c

unsigned int vpx_highbd_8_mse8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_mse8x16 vpx_highbd_8_mse8x16_c

unsigned int vpx_highbd_8_mse8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_mse8x8 vpx_highbd_8_mse8x8_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance16x16 vpx_highbd_8_sub_pixel_avg_variance16x16_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance16x32 vpx_highbd_8_sub_pixel_avg_variance16x32_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance16x8 vpx_highbd_8_sub_pixel_avg_variance16x8_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance32x16 vpx_highbd_8_sub_pixel_avg_variance32x16_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance32x32 vpx_highbd_8_sub_pixel_avg_variance32x32_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance32x64 vpx_highbd_8_sub_pixel_avg_variance32x64_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance4x4 vpx_highbd_8_sub_pixel_avg_variance4x4_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance4x8 vpx_highbd_8_sub_pixel_avg_variance4x8_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance64x32 vpx_highbd_8_sub_pixel_avg_variance64x32_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance64x64 vpx_highbd_8_sub_pixel_avg_variance64x64_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance8x16 vpx_highbd_8_sub_pixel_avg_variance8x16_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance8x4 vpx_highbd_8_sub_pixel_avg_variance8x4_c

uint32_t vpx_highbd_8_sub_pixel_avg_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_highbd_8_sub_pixel_avg_variance8x8 vpx_highbd_8_sub_pixel_avg_variance8x8_c

uint32_t vpx_highbd_8_sub_pixel_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance16x16 vpx_highbd_8_sub_pixel_variance16x16_c

uint32_t vpx_highbd_8_sub_pixel_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance16x32 vpx_highbd_8_sub_pixel_variance16x32_c

uint32_t vpx_highbd_8_sub_pixel_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance16x8 vpx_highbd_8_sub_pixel_variance16x8_c

uint32_t vpx_highbd_8_sub_pixel_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance32x16 vpx_highbd_8_sub_pixel_variance32x16_c

uint32_t vpx_highbd_8_sub_pixel_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance32x32 vpx_highbd_8_sub_pixel_variance32x32_c

uint32_t vpx_highbd_8_sub_pixel_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance32x64 vpx_highbd_8_sub_pixel_variance32x64_c

uint32_t vpx_highbd_8_sub_pixel_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance4x4 vpx_highbd_8_sub_pixel_variance4x4_c

uint32_t vpx_highbd_8_sub_pixel_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance4x8 vpx_highbd_8_sub_pixel_variance4x8_c

uint32_t vpx_highbd_8_sub_pixel_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance64x32 vpx_highbd_8_sub_pixel_variance64x32_c

uint32_t vpx_highbd_8_sub_pixel_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance64x64 vpx_highbd_8_sub_pixel_variance64x64_c

uint32_t vpx_highbd_8_sub_pixel_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance8x16 vpx_highbd_8_sub_pixel_variance8x16_c

uint32_t vpx_highbd_8_sub_pixel_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance8x4 vpx_highbd_8_sub_pixel_variance8x4_c

uint32_t vpx_highbd_8_sub_pixel_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_highbd_8_sub_pixel_variance8x8 vpx_highbd_8_sub_pixel_variance8x8_c

unsigned int vpx_highbd_8_variance16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance16x16 vpx_highbd_8_variance16x16_c

unsigned int vpx_highbd_8_variance16x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance16x32 vpx_highbd_8_variance16x32_c

unsigned int vpx_highbd_8_variance16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance16x8 vpx_highbd_8_variance16x8_c

unsigned int vpx_highbd_8_variance32x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance32x16 vpx_highbd_8_variance32x16_c

unsigned int vpx_highbd_8_variance32x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance32x32 vpx_highbd_8_variance32x32_c

unsigned int vpx_highbd_8_variance32x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance32x64 vpx_highbd_8_variance32x64_c

unsigned int vpx_highbd_8_variance4x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance4x4 vpx_highbd_8_variance4x4_c

unsigned int vpx_highbd_8_variance4x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance4x8 vpx_highbd_8_variance4x8_c

unsigned int vpx_highbd_8_variance64x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance64x32 vpx_highbd_8_variance64x32_c

unsigned int vpx_highbd_8_variance64x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance64x64 vpx_highbd_8_variance64x64_c

unsigned int vpx_highbd_8_variance8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance8x16 vpx_highbd_8_variance8x16_c

unsigned int vpx_highbd_8_variance8x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance8x4 vpx_highbd_8_variance8x4_c

unsigned int vpx_highbd_8_variance8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_highbd_8_variance8x8 vpx_highbd_8_variance8x8_c

void vpx_highbd_comp_avg_pred_c(uint16_t *comp_pred, const uint16_t *pred, int width, int height, const uint16_t *ref, int ref_stride);
#define vpx_highbd_comp_avg_pred vpx_highbd_comp_avg_pred_c

void vpx_highbd_convolve8_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8 vpx_highbd_convolve8_c

void vpx_highbd_convolve8_avg_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_avg vpx_highbd_convolve8_avg_c

void vpx_highbd_convolve8_avg_horiz_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_avg_horiz vpx_highbd_convolve8_avg_horiz_c

void vpx_highbd_convolve8_avg_vert_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_avg_vert vpx_highbd_convolve8_avg_vert_c

void vpx_highbd_convolve8_horiz_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_horiz vpx_highbd_convolve8_horiz_c

void vpx_highbd_convolve8_vert_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve8_vert vpx_highbd_convolve8_vert_c

void vpx_highbd_convolve_avg_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve_avg vpx_highbd_convolve_avg_c

void vpx_highbd_convolve_copy_c(const uint16_t *src, ptrdiff_t src_stride, uint16_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h, int bd);
#define vpx_highbd_convolve_copy vpx_highbd_convolve_copy_c

void vpx_highbd_d117_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_16x16 vpx_highbd_d117_predictor_16x16_c

void vpx_highbd_d117_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_32x32 vpx_highbd_d117_predictor_32x32_c

void vpx_highbd_d117_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_4x4 vpx_highbd_d117_predictor_4x4_c

void vpx_highbd_d117_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d117_predictor_8x8 vpx_highbd_d117_predictor_8x8_c

void vpx_highbd_d135_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_16x16 vpx_highbd_d135_predictor_16x16_c

void vpx_highbd_d135_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_32x32 vpx_highbd_d135_predictor_32x32_c

void vpx_highbd_d135_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_4x4 vpx_highbd_d135_predictor_4x4_c

void vpx_highbd_d135_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d135_predictor_8x8 vpx_highbd_d135_predictor_8x8_c

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
#define vpx_highbd_d45_predictor_16x16 vpx_highbd_d45_predictor_16x16_c

void vpx_highbd_d45_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d45_predictor_32x32 vpx_highbd_d45_predictor_32x32_c

void vpx_highbd_d45_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d45_predictor_4x4 vpx_highbd_d45_predictor_4x4_c

void vpx_highbd_d45_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d45_predictor_8x8 vpx_highbd_d45_predictor_8x8_c

void vpx_highbd_d63_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_16x16 vpx_highbd_d63_predictor_16x16_c

void vpx_highbd_d63_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_32x32 vpx_highbd_d63_predictor_32x32_c

void vpx_highbd_d63_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_4x4 vpx_highbd_d63_predictor_4x4_c

void vpx_highbd_d63_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_d63_predictor_8x8 vpx_highbd_d63_predictor_8x8_c

void vpx_highbd_dc_128_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_16x16 vpx_highbd_dc_128_predictor_16x16_c

void vpx_highbd_dc_128_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_32x32 vpx_highbd_dc_128_predictor_32x32_c

void vpx_highbd_dc_128_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_4x4 vpx_highbd_dc_128_predictor_4x4_c

void vpx_highbd_dc_128_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_128_predictor_8x8 vpx_highbd_dc_128_predictor_8x8_c

void vpx_highbd_dc_left_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_16x16 vpx_highbd_dc_left_predictor_16x16_c

void vpx_highbd_dc_left_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_32x32 vpx_highbd_dc_left_predictor_32x32_c

void vpx_highbd_dc_left_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_4x4 vpx_highbd_dc_left_predictor_4x4_c

void vpx_highbd_dc_left_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_left_predictor_8x8 vpx_highbd_dc_left_predictor_8x8_c

void vpx_highbd_dc_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_16x16 vpx_highbd_dc_predictor_16x16_c

void vpx_highbd_dc_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_32x32 vpx_highbd_dc_predictor_32x32_c

void vpx_highbd_dc_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_4x4 vpx_highbd_dc_predictor_4x4_c

void vpx_highbd_dc_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_predictor_8x8 vpx_highbd_dc_predictor_8x8_c

void vpx_highbd_dc_top_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_16x16 vpx_highbd_dc_top_predictor_16x16_c

void vpx_highbd_dc_top_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_32x32 vpx_highbd_dc_top_predictor_32x32_c

void vpx_highbd_dc_top_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_4x4 vpx_highbd_dc_top_predictor_4x4_c

void vpx_highbd_dc_top_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_dc_top_predictor_8x8 vpx_highbd_dc_top_predictor_8x8_c

void vpx_highbd_h_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_16x16 vpx_highbd_h_predictor_16x16_c

void vpx_highbd_h_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_32x32 vpx_highbd_h_predictor_32x32_c

void vpx_highbd_h_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_4x4 vpx_highbd_h_predictor_4x4_c

void vpx_highbd_h_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_h_predictor_8x8 vpx_highbd_h_predictor_8x8_c

void vpx_highbd_idct16x16_10_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_10_add vpx_highbd_idct16x16_10_add_c

void vpx_highbd_idct16x16_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_1_add vpx_highbd_idct16x16_1_add_c

void vpx_highbd_idct16x16_256_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_256_add vpx_highbd_idct16x16_256_add_c

void vpx_highbd_idct16x16_38_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct16x16_38_add vpx_highbd_idct16x16_38_add_c

void vpx_highbd_idct32x32_1024_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_1024_add vpx_highbd_idct32x32_1024_add_c

void vpx_highbd_idct32x32_135_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_135_add vpx_highbd_idct32x32_135_add_c

void vpx_highbd_idct32x32_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_1_add vpx_highbd_idct32x32_1_add_c

void vpx_highbd_idct32x32_34_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct32x32_34_add vpx_highbd_idct32x32_34_add_c

void vpx_highbd_idct4x4_16_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct4x4_16_add vpx_highbd_idct4x4_16_add_c

void vpx_highbd_idct4x4_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct4x4_1_add vpx_highbd_idct4x4_1_add_c

void vpx_highbd_idct8x8_12_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct8x8_12_add vpx_highbd_idct8x8_12_add_c

void vpx_highbd_idct8x8_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct8x8_1_add vpx_highbd_idct8x8_1_add_c

void vpx_highbd_idct8x8_64_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_idct8x8_64_add vpx_highbd_idct8x8_64_add_c

void vpx_highbd_iwht4x4_16_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_iwht4x4_16_add vpx_highbd_iwht4x4_16_add_c

void vpx_highbd_iwht4x4_1_add_c(const tran_low_t *input, uint16_t *dest, int stride, int bd);
#define vpx_highbd_iwht4x4_1_add vpx_highbd_iwht4x4_1_add_c

void vpx_highbd_lpf_horizontal_16_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_16 vpx_highbd_lpf_horizontal_16_c

void vpx_highbd_lpf_horizontal_16_dual_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_16_dual vpx_highbd_lpf_horizontal_16_dual_c

void vpx_highbd_lpf_horizontal_4_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_4 vpx_highbd_lpf_horizontal_4_c

void vpx_highbd_lpf_horizontal_4_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_horizontal_4_dual vpx_highbd_lpf_horizontal_4_dual_c

void vpx_highbd_lpf_horizontal_8_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_horizontal_8 vpx_highbd_lpf_horizontal_8_c

void vpx_highbd_lpf_horizontal_8_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_horizontal_8_dual vpx_highbd_lpf_horizontal_8_dual_c

void vpx_highbd_lpf_vertical_16_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_16 vpx_highbd_lpf_vertical_16_c

void vpx_highbd_lpf_vertical_16_dual_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_16_dual vpx_highbd_lpf_vertical_16_dual_c

void vpx_highbd_lpf_vertical_4_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_4 vpx_highbd_lpf_vertical_4_c

void vpx_highbd_lpf_vertical_4_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_vertical_4_dual vpx_highbd_lpf_vertical_4_dual_c

void vpx_highbd_lpf_vertical_8_c(uint16_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh, int bd);
#define vpx_highbd_lpf_vertical_8 vpx_highbd_lpf_vertical_8_c

void vpx_highbd_lpf_vertical_8_dual_c(uint16_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1, int bd);
#define vpx_highbd_lpf_vertical_8_dual vpx_highbd_lpf_vertical_8_dual_c

void vpx_highbd_tm_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_16x16 vpx_highbd_tm_predictor_16x16_c

void vpx_highbd_tm_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_32x32 vpx_highbd_tm_predictor_32x32_c

void vpx_highbd_tm_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_4x4 vpx_highbd_tm_predictor_4x4_c

void vpx_highbd_tm_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_tm_predictor_8x8 vpx_highbd_tm_predictor_8x8_c

void vpx_highbd_v_predictor_16x16_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_16x16 vpx_highbd_v_predictor_16x16_c

void vpx_highbd_v_predictor_32x32_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_32x32 vpx_highbd_v_predictor_32x32_c

void vpx_highbd_v_predictor_4x4_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_4x4 vpx_highbd_v_predictor_4x4_c

void vpx_highbd_v_predictor_8x8_c(uint16_t *dst, ptrdiff_t stride, const uint16_t *above, const uint16_t *left, int bd);
#define vpx_highbd_v_predictor_8x8 vpx_highbd_v_predictor_8x8_c

void vpx_idct16x16_10_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_10_add vpx_idct16x16_10_add_c

void vpx_idct16x16_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_1_add vpx_idct16x16_1_add_c

void vpx_idct16x16_256_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_256_add vpx_idct16x16_256_add_c

void vpx_idct16x16_38_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct16x16_38_add vpx_idct16x16_38_add_c

void vpx_idct32x32_1024_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_1024_add vpx_idct32x32_1024_add_c

void vpx_idct32x32_135_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_135_add vpx_idct32x32_135_add_c

void vpx_idct32x32_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_1_add vpx_idct32x32_1_add_c

void vpx_idct32x32_34_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct32x32_34_add vpx_idct32x32_34_add_c

void vpx_idct4x4_16_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct4x4_16_add vpx_idct4x4_16_add_c

void vpx_idct4x4_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct4x4_1_add vpx_idct4x4_1_add_c

void vpx_idct8x8_12_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct8x8_12_add vpx_idct8x8_12_add_c

void vpx_idct8x8_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct8x8_1_add vpx_idct8x8_1_add_c

void vpx_idct8x8_64_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_idct8x8_64_add vpx_idct8x8_64_add_c

void vpx_iwht4x4_16_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_iwht4x4_16_add vpx_iwht4x4_16_add_c

void vpx_iwht4x4_1_add_c(const tran_low_t *input, uint8_t *dest, int stride);
#define vpx_iwht4x4_1_add vpx_iwht4x4_1_add_c

void vpx_lpf_horizontal_16_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_16 vpx_lpf_horizontal_16_c

void vpx_lpf_horizontal_16_dual_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_16_dual vpx_lpf_horizontal_16_dual_c

void vpx_lpf_horizontal_4_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_4 vpx_lpf_horizontal_4_c

void vpx_lpf_horizontal_4_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_horizontal_4_dual vpx_lpf_horizontal_4_dual_c

void vpx_lpf_horizontal_8_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_horizontal_8 vpx_lpf_horizontal_8_c

void vpx_lpf_horizontal_8_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_horizontal_8_dual vpx_lpf_horizontal_8_dual_c

void vpx_lpf_vertical_16_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_16 vpx_lpf_vertical_16_c

void vpx_lpf_vertical_16_dual_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_16_dual vpx_lpf_vertical_16_dual_c

void vpx_lpf_vertical_4_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_4 vpx_lpf_vertical_4_c

void vpx_lpf_vertical_4_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_vertical_4_dual vpx_lpf_vertical_4_dual_c

void vpx_lpf_vertical_8_c(uint8_t *s, int pitch, const uint8_t *blimit, const uint8_t *limit, const uint8_t *thresh);
#define vpx_lpf_vertical_8 vpx_lpf_vertical_8_c

void vpx_lpf_vertical_8_dual_c(uint8_t *s, int pitch, const uint8_t *blimit0, const uint8_t *limit0, const uint8_t *thresh0, const uint8_t *blimit1, const uint8_t *limit1, const uint8_t *thresh1);
#define vpx_lpf_vertical_8_dual vpx_lpf_vertical_8_dual_c

void vpx_mbpost_proc_across_ip_c(unsigned char *src, int pitch, int rows, int cols,int flimit);
#define vpx_mbpost_proc_across_ip vpx_mbpost_proc_across_ip_c

void vpx_mbpost_proc_down_c(unsigned char *dst, int pitch, int rows, int cols,int flimit);
#define vpx_mbpost_proc_down vpx_mbpost_proc_down_c

unsigned int vpx_mse16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_mse16x16 vpx_mse16x16_c

unsigned int vpx_mse16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_mse16x8 vpx_mse16x8_c

unsigned int vpx_mse8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_mse8x16 vpx_mse8x16_c

unsigned int vpx_mse8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_mse8x8 vpx_mse8x8_c

void vpx_plane_add_noise_c(uint8_t *start, const int8_t *noise, int blackclamp, int whiteclamp, int width, int height, int pitch);
#define vpx_plane_add_noise vpx_plane_add_noise_c

void vpx_post_proc_down_and_across_mb_row_c(unsigned char *src, unsigned char *dst, int src_pitch, int dst_pitch, int cols, unsigned char *flimits, int size);
#define vpx_post_proc_down_and_across_mb_row vpx_post_proc_down_and_across_mb_row_c

void vpx_scaled_2d_c(const uint8_t *src, ptrdiff_t src_stride, uint8_t *dst, ptrdiff_t dst_stride, const InterpKernel *filter, int x0_q4, int x_step_q4, int y0_q4, int y_step_q4, int w, int h);
#define vpx_scaled_2d vpx_scaled_2d_c

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

uint32_t vpx_sub_pixel_avg_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance16x16 vpx_sub_pixel_avg_variance16x16_c

uint32_t vpx_sub_pixel_avg_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance16x32 vpx_sub_pixel_avg_variance16x32_c

uint32_t vpx_sub_pixel_avg_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance16x8 vpx_sub_pixel_avg_variance16x8_c

uint32_t vpx_sub_pixel_avg_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance32x16 vpx_sub_pixel_avg_variance32x16_c

uint32_t vpx_sub_pixel_avg_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance32x32 vpx_sub_pixel_avg_variance32x32_c

uint32_t vpx_sub_pixel_avg_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance32x64 vpx_sub_pixel_avg_variance32x64_c

uint32_t vpx_sub_pixel_avg_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance4x4 vpx_sub_pixel_avg_variance4x4_c

uint32_t vpx_sub_pixel_avg_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance4x8 vpx_sub_pixel_avg_variance4x8_c

uint32_t vpx_sub_pixel_avg_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance64x32 vpx_sub_pixel_avg_variance64x32_c

uint32_t vpx_sub_pixel_avg_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance64x64 vpx_sub_pixel_avg_variance64x64_c

uint32_t vpx_sub_pixel_avg_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance8x16 vpx_sub_pixel_avg_variance8x16_c

uint32_t vpx_sub_pixel_avg_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance8x4 vpx_sub_pixel_avg_variance8x4_c

uint32_t vpx_sub_pixel_avg_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse, const uint8_t *second_pred);
#define vpx_sub_pixel_avg_variance8x8 vpx_sub_pixel_avg_variance8x8_c

uint32_t vpx_sub_pixel_variance16x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance16x16 vpx_sub_pixel_variance16x16_c

uint32_t vpx_sub_pixel_variance16x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance16x32 vpx_sub_pixel_variance16x32_c

uint32_t vpx_sub_pixel_variance16x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance16x8 vpx_sub_pixel_variance16x8_c

uint32_t vpx_sub_pixel_variance32x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance32x16 vpx_sub_pixel_variance32x16_c

uint32_t vpx_sub_pixel_variance32x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance32x32 vpx_sub_pixel_variance32x32_c

uint32_t vpx_sub_pixel_variance32x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance32x64 vpx_sub_pixel_variance32x64_c

uint32_t vpx_sub_pixel_variance4x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance4x4 vpx_sub_pixel_variance4x4_c

uint32_t vpx_sub_pixel_variance4x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance4x8 vpx_sub_pixel_variance4x8_c

uint32_t vpx_sub_pixel_variance64x32_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance64x32 vpx_sub_pixel_variance64x32_c

uint32_t vpx_sub_pixel_variance64x64_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance64x64 vpx_sub_pixel_variance64x64_c

uint32_t vpx_sub_pixel_variance8x16_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance8x16 vpx_sub_pixel_variance8x16_c

uint32_t vpx_sub_pixel_variance8x4_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance8x4 vpx_sub_pixel_variance8x4_c

uint32_t vpx_sub_pixel_variance8x8_c(const uint8_t *src_ptr, int src_stride, int x_offset, int y_offset, const uint8_t *ref_ptr, int ref_stride, uint32_t *sse);
#define vpx_sub_pixel_variance8x8 vpx_sub_pixel_variance8x8_c

void vpx_tm_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_16x16 vpx_tm_predictor_16x16_c

void vpx_tm_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_32x32 vpx_tm_predictor_32x32_c

void vpx_tm_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_4x4 vpx_tm_predictor_4x4_c

void vpx_tm_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_tm_predictor_8x8 vpx_tm_predictor_8x8_c

void vpx_v_predictor_16x16_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_16x16 vpx_v_predictor_16x16_c

void vpx_v_predictor_32x32_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_32x32 vpx_v_predictor_32x32_c

void vpx_v_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_4x4 vpx_v_predictor_4x4_c

void vpx_v_predictor_8x8_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_v_predictor_8x8 vpx_v_predictor_8x8_c

unsigned int vpx_variance16x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance16x16 vpx_variance16x16_c

unsigned int vpx_variance16x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance16x32 vpx_variance16x32_c

unsigned int vpx_variance16x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance16x8 vpx_variance16x8_c

unsigned int vpx_variance32x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance32x16 vpx_variance32x16_c

unsigned int vpx_variance32x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance32x32 vpx_variance32x32_c

unsigned int vpx_variance32x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance32x64 vpx_variance32x64_c

unsigned int vpx_variance4x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance4x4 vpx_variance4x4_c

unsigned int vpx_variance4x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance4x8 vpx_variance4x8_c

unsigned int vpx_variance64x32_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance64x32 vpx_variance64x32_c

unsigned int vpx_variance64x64_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance64x64 vpx_variance64x64_c

unsigned int vpx_variance8x16_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance8x16 vpx_variance8x16_c

unsigned int vpx_variance8x4_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance8x4 vpx_variance8x4_c

unsigned int vpx_variance8x8_c(const uint8_t *src_ptr, int src_stride, const uint8_t *ref_ptr, int ref_stride, unsigned int *sse);
#define vpx_variance8x8 vpx_variance8x8_c

void vpx_ve_predictor_4x4_c(uint8_t *dst, ptrdiff_t stride, const uint8_t *above, const uint8_t *left);
#define vpx_ve_predictor_4x4 vpx_ve_predictor_4x4_c

void vpx_dsp_rtcd(void);

#ifdef RTCD_C
#include "vpx_ports/x86.h"
static void setup_rtcd_internal(void)
{
    int flags = x86_simd_caps();

    (void)flags;

}
#endif

#ifdef __cplusplus
}  // extern "C"
#endif

#endif
