package theBrewmaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.relics.LouseLiverRelic;
import theBrewmaster.relics.SpiritHelmetRelic;
import theBrewmaster.stances.IntoxicatedStance;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

//Gain 1 dex for the turn for each card played.

public class IntoxicationPower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = BrewmasterMod.makeID("Intoxication");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int INTOX_THRESHOLD = 100;
    public static final int INTOX_THRESHOLD_RELIC = 75;
    public static final int INTOX_DECAY_RATE = 5;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("intoxication84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("intoxication32.png"));

    public IntoxicationPower(final AbstractCreature owner, final AbstractCreature source, final int amount){
        this(owner,source,amount,false);
    }

    public IntoxicationPower(final AbstractCreature owner, final AbstractCreature source, final int amount, boolean isBeerStein) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        
        type = PowerType.BUFF;
        isTurnBased = false;
        
        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        // Temperance.
        if (this.owner.hasPower(TemperancePower.POWER_ID)){
            int temperanceAmount = this.owner.getPower(TemperancePower.POWER_ID).amount;
            addToBot(new ApplyPowerAction(this.owner, this.source, new StrengthPower(this.owner, temperanceAmount), temperanceAmount));
        }

        // Multiply gain if has relic
        if (AbstractDungeon.player.hasRelic(SpiritHelmetRelic.ID) && !isBeerStein)
            this.amount *= SpiritHelmetRelic.MULTIPLIER;

        // If have enough stacks when gaining power, enter stance
        if (this.amount >= INTOX_THRESHOLD || (this.amount >= INTOX_THRESHOLD_RELIC && AbstractDungeon.player.hasRelic(LouseLiverRelic.ID))){
            addToBot(new ChangeStanceAction(new IntoxicatedStance()));
        }

        updateDescription();
    }

    // Enter Intoxicated when you add enough stacks.
    // This is intended to pull you out of any Watcher stances you may have put youself into.
    public void stackPower(int stackAmount){
        if (AbstractDungeon.player.hasPower(NoIntoxicationPower.POWER_ID)){
            return;
        }
        super.stackPower(stackAmount);
        if (this.amount >= INTOX_THRESHOLD || (this.amount >= INTOX_THRESHOLD_RELIC && AbstractDungeon.player.hasRelic(LouseLiverRelic.ID))) {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new IntoxicatedStance()));
        }
        updateDescription();
    }
    
    // At the end of the turn, remove intoxication stacks
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        flash();
        int reduceAmount = this.amount/INTOX_DECAY_RATE;
        if (reduceAmount <= 0)
            reduceAmount = 1;
        reducePower(reduceAmount);

        updateDescription();
    }
    
    // Remove Intoxicated when losing enough stacks
    public void reducePower(int reduceAmount){
        // Add block if you have Fluid Motion active
        if (owner.hasPower(FluidMotionPower.POWER_ID)){
            addToBot(new GainBlockAction(owner, owner, reduceAmount));
        }
        super.reducePower(reduceAmount);
        if (this.amount < INTOX_THRESHOLD && AbstractDungeon.player.stance.ID.equals(IntoxicatedStance.STANCE_ID))
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        if (this.amount <= 0)
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    // If entering Neutral stance & has enough stacks, enter intoxication stance.
    // Useful if you end up entering any of the Watcher stances. When you leave, if you're intoxicated you'll re-enter Intoxicated.
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (newStance.ID.equals("Neutral") && this.amount >= INTOX_THRESHOLD)
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new IntoxicatedStance()));
    }

    // When starting turn, if have enough stacks and are in Neutral, enter intoxication stance.
    // This is not meant to pull you out of Watcher stances.
    public void atStartOfTurn() {
        if (AbstractDungeon.player.stance.ID.equals("Neutral") && this.amount >= INTOX_THRESHOLD)
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new IntoxicatedStance()));
    }

    // Update the description when intoxicated.
    @Override
    public void updateDescription() {
        if (this.amount < INTOX_THRESHOLD) {
            description = DESCRIPTIONS[0];
        } else if (this.amount >= INTOX_THRESHOLD) {
            description = DESCRIPTIONS[1];
        }
    }

}
