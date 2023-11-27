# vga_pong
Pong game on a VGA display. Written in spinalHDL for FPGA and ASIC. Works on a ULX3S FPGA board with a VGA female connector attached.

## Generate Verilog

```
cd spinalHDL
sbt run
```

Output is this Verilog file:
```
VgaPong.v
```

## Run the vga_pong on an ULX3S 85k FPGA board

ToDo: Explain the following notes further

- Copy ```VgaClock.v``` into the folder ```ulx3s```
- cd ulx3s
- Get oss-cad-suite and unpack (https://github.com/YosysHQ/oss-cad-suite-build/releases)
- Set paths in makefile
- make
- make prog
- Connect pins to VGA display / VGA connector cable
  
  | VGA    | FPGA ULX3S   |
  |--------|--------|
  | red    | GN[9]  |
  | green  | GN[10] |
  | blue   | GN[11] |
  | H Sync | GN[12] |
  | V Sync | GN[13] |
  | GND | GND |

 
## VGA clock screen photo

![VGA clock screen photo](screenshot.jpg)

## VGA clock drawing

This drawing might help to understand the pixel counter comparisons in the code.

![VGA clock drawing](drawing.jpg)

## Photo of the build on my desk

![VGA clock build](build.jpg)