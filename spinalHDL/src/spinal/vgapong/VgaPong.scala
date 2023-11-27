package vgapong

import spinal.core._
import spinal.core.sim._
import spinal.lib._

case class VgaPong() extends Component{

    val io = new Bundle{
        val h_sync = out Bool()                 // horizontal sync output
        val v_sync = out Bool()                 // vertical sync output
        val r = out Bool()                      // color red output
        val g = out Bool()                      // color green output
        val b = out Bool()                      // color blue output
    }

    val vga_config = VgaConfig()

    val vga_sync_gen = VgaSyncGen(vga_config)
    io.h_sync := vga_sync_gen.io.h_sync
    io.v_sync := vga_sync_gen.io.v_sync

    val vga_content = VgaContent(vga_config)
    vga_content.io.x := vga_sync_gen.io.x
    vga_content.io.y := vga_sync_gen.io.y
    vga_content.io.activevideo := vga_sync_gen.io.activevideo
    io.r := vga_content.io.r
    io.g := vga_content.io.g
    io.b := vga_content.io.b
}

object VgaPongMain{
    def main(args: Array[String]) {
        SpinalConfig(
            mode = Verilog,
            defaultClockDomainFrequency = FixedFrequency(25 MHz)
        ).generate(new VgaPong)

        // val spinalConfig = SpinalConfig(defaultClockDomainFrequency = FixedFrequency(25 MHz))

        // SimConfig
        //     .withConfig(spinalConfig)
        //     .withWave
        //     .compile(new VgaPong)
        //     .doSim{ vgapong =>
        //         vgapong.clockDomain.forkStimulus(2)
        //         var idx = 0
        //         while(idx < 3000000){
        //             vgapong.clockDomain.waitSampling()
        //             idx += 1
        //         }
        //     }
    }
}
