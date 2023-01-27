package theBrewmaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomDamageTypes;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AdmiralsBrewPower extends AbstractPower{
    public AbstractCreature source;

    public static final String POWER_ID = BrewmasterMod.makeID("AdmiralsBrewPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private AbstractPlayer p = AbstractDungeon.player;

    public AdmiralsBrewPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        
        this.amount = 0;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        
        if (info.type.equals(CustomDamageTypes.ADMIRALSBREW))
            return damageAmount;

        int damage = 0;
        
        if (damageAmount > 0) {
            flash();
            damage = damageAmount / 2;
        }
        
        this.amount += damage;
        updateDescription();
        
        return damage;
    }

    public void onVictory(){
        if (this.amount > this.p.currentHealth)
            this.amount = this.p.currentHealth - 1;
        
        p.damage(new DamageInfo(source, amount, CustomDamageTypes.ADMIRALSBREW));
    }

    // public void onInflictDamage(){
    //     activate();
    // }

    // public void atEndOfTurn(){
    //     activate();
    // }

    // private void activate() {
    //     if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()){
    //         if (this.amount > this.p.currentHealth)
    //             this.amount = this.p.currentHealth - 1;
    //         addToTop(new DamageAction(this.owner, new DamageInfo(source, amount, DamageType.HP_LOSS)));
    //     }
    // }

    // Update the description
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}