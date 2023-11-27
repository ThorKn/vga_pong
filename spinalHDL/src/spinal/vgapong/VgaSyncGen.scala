package vgapong

import spinal.core._
import spinal.lib._

case class VgaSyncGen(config : VgaConfig) extends Component {

    val io = new Bundle{
        val h_sync = out Bool()             // horizontal sync output
        val v_sync = out Bool()             // vertical sync output
        val activevideo = out Bool()        // Sync signals inside visible screen area?
        val x = out UInt(log2Up(config.h_total_pixels) bits)  // x position 
        val y = out UInt(log2Up(config.v_total_lines) bits)   // y position
    }

    // Create counters for horizontal and vertical syncs in sizes fitting to the config
    val h_counter = Counter(0 to config.h_total_pixels-1)
    val v_counter = Counter(0 to config.v_total_lines-1)

    // Counters will overflow and reset automatically.
    // h_counter increments every clockcycle.
    // v_counter increments at every end of a line, when h_counter overflows).
    h_counter.increment()
    when(h_counter.willOverflow) {
        v_counter.increment()
    } 

    // Enable negative h_sync when h_counter is between front_porch and back_porch
    when(h_counter >= config.h_front_porch && h_counter <= config.h_front_porch + config.h_pulse) {
        io.h_sync := False
    } .otherwise {
        io.h_sync := True
    }

    // Enable negative v_sync when v_counter is between front_porch and back_porch
    when(v_counter >= config.v_front_porch && v_counter <= config.v_front_porch + config.v_pulse) {
        io.v_sync := False
    } .otherwise {
        io.v_sync := True
    }

    // Set the activevideo flag when in the visible screen area.
    // Calculate and set x and y outputs (pixel position) when in visible screen area. 
    when(h_counter >= config.h_hidden_pixels && v_counter >= config.v_hidden_lines) {
        io.activevideo := True
        io.x := h_counter - config.h_hidden_pixels
        io.y := v_counter - config.v_hidden_lines
    } .otherwise {
        io.activevideo := False
        io.x := 0
        io.y := 0
    }
}