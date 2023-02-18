package theBrewmaster.vfx.stance;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityStanceChangeParticle;

import theBrewmaster.BrewmasterMod;

public class IntoxicatedParticleGenerator extends AbstractGameEffect{

    private float y;
    private float x;

    public IntoxicatedParticleGenerator(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        for (int i = 0; i < 10; i++){
            AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(BrewmasterMod.BREWMASTER_ORANGE, this.x, this.y));
        }
        this.isDone = true;
    }

    public void dispose() {
    }

    public void render(SpriteBatch sb) {
    }
    
}
