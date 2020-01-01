// This file is generated. Do not edit.
#ifndef VP9_RTCD_H_
#define VP9_RTCD_H_

#ifdef RTCD_C
#define RTCD_EXTERN
#else
#define RTCD_EXTERN extern
#endif

/*
 * VP9
 */

#include "vpx/vpx_integer.h"
#include "vp9/common/vp9_common.h"
#include "vp9/common/vp9_enums.h"
#include "vp9/common/vp9_filter.h"

struct macroblockd;

/* Encoder forward decls */
struct macroblock;
struct vp9_variance_vtable;
struct search_site_config;
struct mv;
union int_mv;
struct yv12_buffer_config;

#ifdef __cplusplus
extern "C" {
#endif

void vp9_highbd_iht16x16_256_add_c(const tran_low_t *input, uint16_t *dest, int stride, int tx_type, int bd);
#define vp9_highbd_iht16x16_256_add vp9_highbd_iht16x16_256_add_c

void vp9_highbd_iht4x4_16_add_c(const tran_low_t *input, uint16_t *dest, int stride, int tx_type, int bd);
#define vp9_highbd_iht4x4_16_add vp9_highbd_iht4x4_16_add_c

void vp9_highbd_iht8x8_64_add_c(const tran_low_t *input, uint16_t *dest, int stride, int tx_type, int bd);
#define vp9_highbd_iht8x8_64_add vp9_highbd_iht8x8_64_add_c

void vp9_iht16x16_256_add_c(const tran_low_t *input, uint8_t *dest, int stride, int tx_type);
#define vp9_iht16x16_256_add vp9_iht16x16_256_add_c

void vp9_iht4x4_16_add_c(const tran_low_t *input, uint8_t *dest, int stride, int tx_type);
#define vp9_iht4x4_16_add vp9_iht4x4_16_add_c

void vp9_iht8x8_64_add_c(const tran_low_t *input, uint8_t *dest, int stride, int tx_type);
#define vp9_iht8x8_64_add vp9_iht8x8_64_add_c

void vp9_rtcd(void);

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
