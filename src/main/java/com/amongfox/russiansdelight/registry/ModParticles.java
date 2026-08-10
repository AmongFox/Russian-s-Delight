package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.client.particle.ModSimpleParticleType;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public enum ModParticles {
	FERMENTATION_BARREL_BUBBLE("fermentation_barrel_bubble", () -> new ModSimpleParticleType(false));

	private final String pathName;
	private final Supplier<SimpleParticleType> typeSupplier;
	private SimpleParticleType type;
	private boolean registered = false;

	ModParticles(String pathName, Supplier<SimpleParticleType> typeSupplier) {
		this.pathName = pathName;
		this.typeSupplier = typeSupplier;
	}

	public static void registerAll() {
		for (ModParticles value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.type = Registry.register(
					BuiltInRegistries.PARTICLE_TYPE,
					ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, this.pathName),
					this.typeSupplier.get()
			);
			this.registered = true;
		}
	}

	public SimpleParticleType get() {
		if (type == null) {
			throw new IllegalStateException("ParticleType " + this.name() + " not registered yet!");
		}
		return type;
	}
}
