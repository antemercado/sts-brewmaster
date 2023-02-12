package theBrewmaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomDamageTypes;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VasoimmolationPower extends AbstractPower{
    public AbstractCreature source;

    
    public static final String POWER_ID = BrewmasterMod.makeID("VasoimmolationPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    
    public boolean upgraded;
    private AbstractPlayer p;
    private int NUMERATOR = 20;


    private DamageType damageType;

    public VasoimmolationPower(final AbstractPlayer p, final int amount, boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.p = p;
        this.owner = p;
        this.amount = amount;
        this.upgraded = upgraded;
        this.damageType = CustomDamageTypes.VASOIMMOLATION;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!p.hasPower(IntoxicationPower.POWER_ID))
            return;
        flash();
        float damage = p.getPower(IntoxicationPower.POWER_ID).amount * this.amount * NUMERATOR / 100;
        if (!upgraded)
            addToBot(new DamageAction(this.p, new DamageInfo(p, (int)damage, damageType), AttackEffect.FIRE));
        addToBot(new DamageAllEnemiesAction(this.p, (int)damage, damageType, AttackEffect.FIRE));
    }

    // Update the description
    @Override
    public void updateDescription() {
        if (!upgraded)
        description = DESCRIPTIONS[0] + (amount * NUMERATOR) + DESCRIPTIONS[2];
        if (upgraded)
            description = DESCRIPTIONS[1] + (amount * NUMERATOR) + DESCRIPTIONS[2];
    }

}