package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public enum ModSounds {
	FERMENTATION_BARREL_BUBBLE("fermentation_barrel_bubbles");

	private final String pathName;
	private final Supplier<SoundEvent> soundEventSupplier;
	private SoundEvent soundEvent;
	private boolean registered = false;

	ModSounds(String pathName) {
		this.pathName = pathName;
		this.soundEventSupplier = () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RussiansDelight.MOD_ID, this.pathName));
	}

	public static void registerAll() {
		for (ModSounds value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.soundEvent = Registry.register(
					BuiltInRegistries.SOUND_EVENT,
					new ResourceLocation(RussiansDelight.MOD_ID, this.pathName),
					this.soundEventSupplier.get()
			);
			this.registered = true;
		}
	}

	public SoundEvent get() {
		if (soundEvent == null) {
			throw new IllegalStateException("SoundEvent " + this.name() + " not registered yet!");
		}
		return soundEvent;
	}
}
