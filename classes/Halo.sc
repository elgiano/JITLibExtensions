
Halo : Library {
	classvar <lib;

		// shorter posting
	nodeType { ^Event }

	*initClass {
		lib = lib ?? { Halo.new };
	}

	*put { |...args|
		lib.put(*args);
	}

	*at { | ... keys| ^lib.at(*keys); }

	*postTree {
		this.lib.postTree
	}
}

+ Object {

	addHalo { |...args|
		Halo.put(this, *args);
	}

	getHalo { |... keys|
		if (keys.isNil) { ^Halo.at(this) };
		^Halo.at(this, *keys);
	}

	clearHalo { Halo.lib.put(this, nil) }

	adieu {
		this.clear;
		this.releaseDependants;
		this.clearHalo;
	}

	release {
		this.releaseDependants;
		this.clearHalo;
	}

	checkSpec {
		var specs = Halo.at(this, \spec);
		if (specs.isNil) {
			specs = ().parent_(Spec.specs);
			Halo.put(this, \spec, specs);
			this.addDependant({ |who, what|
				if (what == \clear) {
					this.releaseDependants;
					this.clearHalo;
				};
			});
		};
		^specs
	}

	// the ones for specs will be a common use case,
	// others could be done similarly:
	addSpec { |name, spec|
		this.checkSpec;
		if (name.notNil) {
			Halo.put(this, \spec, name, spec.asSpec);
		};
	}

	getSpec { |name|
		var specs = this.checkSpec;
		if (name.isNil) { ^specs };
		^specs.at(name);
	}


	addTag { |name, weight = 1|
		Halo.put(this, \tag, name, weight);
	}
		// returns tag weight
	getTag { |name|
		if (name.isNil) { ^Halo.at(this, \tag) };
		^Halo.at(this, \tag, name);
	}

	// categories also have weights
	addCat { |name, weight = 1|
		Halo.put(this, \cat, name, weight);
	}
		// returns cat weight
	getCat { |name|
		if (name.isNil) { ^Halo.at(this, \cat) };
		^Halo.at(this, \cat, name);
	}
}
