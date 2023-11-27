TOPMOD 				:= VgaPong
PNAME  				:= VgaPong
CONSTRAINTS 		:= ulx3s
CHIP 				:= 85k
PACKAGE 			:= CABGA381
VSOURCES 			:= VgaPong.v
PROGPATH 			:= ~/oss-cad-suite/bin/fujprog
OSS_CAD_SUITE_PATH 	:= ~/oss-cad-suite/bin/

.PHONY: all

all: $(PNAME).bit

.PHONY: clean
clean:
	rm -rf $(PNAME).json $(PNAME).config $(PNAME).bit

$(PNAME).bit: $(PNAME).config
	$(OSS_CAD_SUITE_PATH)ecppack $(PNAME).config $(PNAME).bit

$(PNAME).config: $(PNAME).json
	$(OSS_CAD_SUITE_PATH)nextpnr-ecp5 \
		--$(CHIP) \
		--package $(PACKAGE) \
    	--json $(PNAME).json \
		--lpf $(CONSTRAINTS).lpf \
		--textcfg $(PNAME).config

$(PNAME).json: $(VSOURCES)
	yosys -q -l $(PNAME)_yosys.log -DSYNTH -p "synth_ecp5 -top $(TOPMOD) -json $(PNAME).json" $(VSOURCES)

prog: $(PNAME).bit
	sudo $(PROGPATH) $(PNAME).bit
