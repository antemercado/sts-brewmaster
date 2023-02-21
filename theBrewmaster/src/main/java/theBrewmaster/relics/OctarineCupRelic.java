package theBrewmaster.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static theBrewmaster.BrewmasterMod.makeRelicOutlinePath;
import static theBrewmaster.BrewmasterMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.util.TextureLoader;

public class OctarineCupRelic extends CustomRelic implements OnReceivePowerRelic{

    
    public static final String ID = BrewmasterMod.makeID("OctarineCupRelic");
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    
    private boolean triggeredThisTurn = false;
    
    public OctarineCupRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature p) {
        if (!power.ID.equals(IntoxicationPower.POWER_ID)){
            return true;
        }
        if (((IntoxicationPower)power).isRelic){
            return true;
        }
        if (!triggeredThisTurn){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
            triggeredThisTurn = true;
        }
        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
