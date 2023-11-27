package vgapong

import spinal.core._
import spinal.lib._

case class VgaConfig(
    h_visible_pixels    : Int = 640,
    v_visible_lines     : Int = 480,
    h_front_porch       : Int = 16,
    h_pulse             : Int = 96,
    h_back_porch        : Int = 48,
    v_front_porch       : Int = 11,
    v_pulse             : Int = 2,
    v_back_porch        : Int = 31
) {
    def h_hidden_pixels = h_front_porch + h_pulse + h_back_porch;
    def v_hidden_lines  = v_front_porch + v_pulse + v_back_porch;
    def h_total_pixels  = h_hidden_pixels + h_visible_pixels;
    def v_total_lines   = v_hidden_lines + v_visible_lines;
}
