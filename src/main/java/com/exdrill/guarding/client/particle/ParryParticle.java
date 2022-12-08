package com.exdrill.guarding.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ParryParticle extends SimpleAnimatedParticle {

    protected ParryParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider, 0.0F);
        this.lifetime = 5;
        this.quadSize = 1.0F;
        float f = this.random.nextFloat() * 0.4F + 0.6F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.setSpriteFromAge(spriteProvider);
    }

    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    public ParticleRenderType getType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        public Particle createParticle(SimpleParticleType parameters, ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ParryParticle(clientLevel, x, y, z, spriteSet);
        }
    }
}
