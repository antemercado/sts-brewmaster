package theBrewmaster.stances;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.vfx.stance.IntoxicatedParticleGenerator;
import theBrewmaster.vfx.stance.IntoxicatedParticleEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

public class IntoxicatedStance extends AbstractStance{
    public static final String STANCE_ID = BrewmasterMod.makeID("Intoxicated");
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public static final String NAME = stanceString.NAME;
    public static final String[] DESCRIPTIONS = stanceString.DESCRIPTION;

    public static final String SFX_ENTER_STANCE = "STANCE_ENTER_DIVINITY";
    public static final String SFX_STANCE_LOOP = "STANCE_LOOP_DIVINITY";
    
    public static long sfxId = -1L;

    public IntoxicatedStance() {
        ID = STANCE_ID;
        name = NAME;
        updateDescription();
    }

    public void onEnterStance() {
        if (sfxId != -1L){
            stopIdleSfx();
        }

        CardCrawlGame.sound.play(SFX_ENTER_STANCE);
        sfxId = CardCrawlGame.sound.playAndLoop(SFX_STANCE_LOOP);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(BrewmasterMod.BREWMASTER_ORANGE, true));
        AbstractDungeon.effectsQueue.add(new IntoxicatedParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
    }

    @Override
    public void onExitStance() {
        stopIdleSfx();
    }

    @Override
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS){
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0f){
                this.particleTimer = 0.1f;
                AbstractDungeon.effectsQueue.add(IntoxicatedParticleEffect.effect());
            }
        }

        // this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        // if (this.particleTimer2 < 0.0f){
        //     this.particleTimer2 = MathUtils.random(0.45f, 0.55f);
        //     AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        // }
    }

    @Override
    public void stopIdleSfx() {
        if (sfxId != -1L){
            CardCrawlGame.sound.stop(SFX_STANCE_LOOP, sfxId);
            sfxId = -1L;
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    
}
