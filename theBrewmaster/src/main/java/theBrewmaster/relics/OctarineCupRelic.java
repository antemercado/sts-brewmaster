package theBrewmaster.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static theBrewmaster.BrewmasterMod.makeRelicOutlinePath;
import static theBrewmaster.BrewmasterMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.stances.IntoxicatedStance;
import theBrewmaster.util.TextureLoader;

public class OctarineCupRelic extends CustomRelic{

    
    public static final String ID = BrewmasterMod.makeID("OctarineCupRelic");
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("octarine_cup.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("octarine_cup.png"));
    
    public OctarineCupRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.stance.ID.equals(IntoxicatedStance.STANCE_ID)){
            flash();
            addToTop(new GainEnergyAction(1));
            addToTop(new DrawCardAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
