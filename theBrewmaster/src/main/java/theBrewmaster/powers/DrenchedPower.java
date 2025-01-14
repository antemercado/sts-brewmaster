package theBrewmaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomDamageTypes;
import theBrewmaster.relics.EthanolFlaskRelic;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class DrenchedPower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = BrewmasterMod.makeID("DrenchedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public DrenchedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount){
        super.stackPower(stackAmount);
        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        int damage = this.amount;
        DamageType type = CustomDamageTypes.DRENCHED;

        if (info.type.equals(CustomDamageTypes.MATCH)){
            damage *= 2;
            type = CustomDamageTypes.MATCH;
        }

        //addToTop(new DamageAllEnemiesAction(null, damage, type, AttackEffect.FIRE));
        addToTop(new DamageAllEnemiesAction(AbstractDungeon.player,
             DamageInfo.createDamageMatrix(damage, true), type, AttackEffect.FIRE));
        
        if (!this.owner.hasPower(SearingChainPower.POWER_ID)){
            addToTop(new RemoveSpecificPowerAction(owner, info.owner, this));
        }
        return damageAmount;
    }

    public float atDamageReceive(float damage, DamageType damageType) {
        if (damageType.equals(DamageType.NORMAL) && AbstractDungeon.player.hasRelic(EthanolFlaskRelic.ID)){
            return damage * (1.0f + this.amount * 0.1f);
        }
        return damage;
    }

    // Update the description
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}