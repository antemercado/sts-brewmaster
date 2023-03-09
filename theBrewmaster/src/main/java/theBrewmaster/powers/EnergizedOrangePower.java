package theBrewmaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnergizedOrangePower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = BrewmasterMod.makeID("EnergizedOrangePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("energized_orange_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("energized_orange_32.png"));

    public EnergizedOrangePower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        if (this.amount > 999){
            this.amount = 999;
        }

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount){
        super.stackPower(stackAmount);
        if (this.amount > 999){
            this.amount = 999;
        }
        updateDescription();
    }

    @Override
    public void onEnergyRecharge() {
        flash();
        AbstractDungeon.player.gainEnergy(this.amount);
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    // Update the description
    @Override
    public void updateDescription() {
        if (this.amount == 1){
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

}