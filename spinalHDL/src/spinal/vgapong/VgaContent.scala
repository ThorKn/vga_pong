package vgapong

import spinal.core._
import spinal.lib._

case class VgaContent(config : VgaConfig) extends Component {

    val io = new Bundle{
        val x = in UInt(log2Up(config.h_total_pixels) bits)  // x position 
        val y = in UInt(log2Up(config.v_total_lines) bits)   // y position
        val activevideo = in Bool()   // Sync signals inside visible screen area?
        val r = out Bool()            // Red output 
        val g = out Bool()            // Green output 
        val b = out Bool()            // Blue output 
    }

    val clock_counters = ClockCounters()

    when(io.activevideo) {
        // Frame of 4 pixels width in blue
        when(io.x < 10 || io.x > 629 || 
             io.y < 10 || io.y > 469) {
            io.r := True
            io.g := True
            io.b := True
        } 
        // Scale lines for the clock in red
        .elsewhen(io.x >= 60 && io.x < 580 && 
                 (io.y === 60 || io.y === 61 || io.y === 90 || io.y === 120 ||
                  io.y === 150 || io.y === 151 || io.y === 180 || io.y === 210 ||
                  io.y === 240 || io.y === 241 || io.y === 270 || io.y === 300 ||
                  io.y === 330 || io.y === 331 || io.y === 360 || io.y === 390 ||
                  io.y === 420 || io.y === 421)) {
            io.r := True
            io.g := False
            io.b := False
        } 
        .elsewhen(io.x >= 420 && io.x < 520 &&
                  io.y <= 420 && io.y >= 420 - (clock_counters.io.seconds * 6)) {
            io.r := True
            io.g := True
            io.b := True
        }
        .elsewhen(io.x >= 270 && io.x < 370 &&
                  io.y <= 420 && io.y >= 420 - (clock_counters.io.minutes * 6)) {
            io.r := False
            io.g := True
            io.b := False
        }
        .elsewhen(io.x >= 120 && io.x < 220 &&
                  io.y <= 420 && io.y >= 420 - (clock_counters.io.hours * 30)) {
            io.r := False
            io.g := True
            io.b := False
        }
        .otherwise {
            io.r := False
            io.g := False
            io.b := False            
        }
    } .otherwise {
        io.r := False
        io.g := False
        io.b := False
    }

}
