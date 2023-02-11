package theBrewmaster.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.BaseMod;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.powers.DrenchedPower;

public class InertIncendiary extends AbstractPotion{

    public static final String POTION_ID = BrewmasterMod.makeID("InertIncendiary");

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final String NAME = potionStrings.NAME;

    private static final PotionRarity RARITY = PotionRarity.COMMON;
    private static final PotionSize SIZE = PotionSize.SPHERE;
    public static final PotionColor COLOR = PotionColor.NONE;
    public static final Color LIQUID_COLOR = CardHelper.getColor(100.0f, 160.0f, 70.0f); //RGB
    public static final Color HYBRID_COLOR = LIQUID_COLOR; //RGB
    public static final Color SPOTS_COLOR = CardHelper.getColor(120.0f, 140.0f, 48.0f);


    public InertIncendiary() {
        super(NAME, POTION_ID, RARITY, SIZE, COLOR);
        this.isThrown = true;
        this.targetRequired = true;
        this.labOutlineColor = BrewmasterMod.BREWMASTER_ORANGE;
    }

    public void initializeData(){
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip
            (BaseMod.getKeywordTitle("thebrewmaster:drenched"),BaseMod.getKeywordDescription("thebrewmaster:drenched")));
    }

    public int getPotency(int potency) {
        return 15;
    }
    
    public void use(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new DrenchedPower(target, AbstractDungeon.player, potency), potency));
    }
    
    public AbstractPotion makeCopy() {
        return new InertIncendiary();
    }

    
}