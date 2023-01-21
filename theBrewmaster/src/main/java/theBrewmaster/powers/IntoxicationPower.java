package theBrewmaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import theBrewmaster.DefaultMod;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.DefaultMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

//Gain 1 dex for the turn for each card played.

public class IntoxicationPower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Intoxication");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("intoxication84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("intoxication32.png"));

    public IntoxicationPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

        updateDescription();
    }

    // At the end of the turn, remove half of intoxication stacks
    @Override
    public void atEndOfTurn(final boolean isPlayer) {

        flash();
        reducePower(this.amount/4);

    }

    // When over 100 stacks, enter Intoxicated
    @Override
    public void stackPower(int stackAmount){
        super.stackPower(stackAmount);
        if (this.amount >= 100) {
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction("Divinity"));
        }
    }

    // Update the description when intoxicated.
    @Override
    public void updateDescription() {
        if (amount < 100) {
            description = DESCRIPTIONS[0];
        } else if (amount >= 100) {
            description = DESCRIPTIONS[1];
        }
    }

}
