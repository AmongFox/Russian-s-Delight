package com.amongfox.russiansdelight.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class FermentationBubbleParticle extends TextureSheetParticle {
	protected FermentationBubbleParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(level, x, y, z);
		this.gravity = -0.1F;
		this.friction = 0.9F;
		this.setSize(0.02F, 0.02F);
		this.quadSize *= 0.2F + this.random.nextFloat() * 0.6F;
		this.xd = xSpeed * 0.2D + (this.random.nextDouble() * 2.0D - 1.0D) * 0.02D;
		this.yd = ySpeed * 0.2D + (this.random.nextDouble() * 2.0D - 1.0D) * 0.02D;
		this.zd = zSpeed * 0.2D + (this.random.nextDouble() * 2.0D - 1.0D) * 0.02D;
		this.lifetime = (int)(24.0D / (this.random.nextDouble() * 0.6D + 0.4D));
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			FermentationBubbleParticle particle = new FermentationBubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.pickSprite(this.sprites);
			particle.setColor(0.435F, 0.827F, 0.478F);
			return particle;
		}
	}
}
