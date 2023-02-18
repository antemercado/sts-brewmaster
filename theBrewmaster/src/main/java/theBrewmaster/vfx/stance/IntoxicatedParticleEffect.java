package theBrewmaster.vfx.stance;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.helpers.VfxBuilder;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makeVFXPath;

public class IntoxicatedParticleEffect {

    private static final Texture BUBBLE = TextureLoader.getTexture(makeVFXPath("bubble.png"));
    private static final Texture BUBBLE_POP = TextureLoader.getTexture(makeVFXPath("bubble_pop.png"));

    private static AbstractPlayer p = AbstractDungeon.player;
    private static float spawnX = p.hb_w / 2.0f;

    public static AbstractGameEffect effect() {
        float x = p.hb.cX + (MathUtils.random(-1f,1f) * spawnX);
        float y = p.hb.y + (MathUtils.random(0f,1f) * p.hb_h);
        AbstractGameEffect ret = new VfxBuilder(BUBBLE, x, y, 2.0f)
            .setColor(BrewmasterMod.BREWMASTER_ORANGE)
            .useAdditiveBlending()
            .scale(1.0f, 0.25f, VfxBuilder.Interpolations.EXP10)
            .fadeOut(2.0f)
            .gravity(-2.0f)
            .build();
        ret.renderBehind = MathUtils.randomBoolean();
        return ret;
    }
    
}
