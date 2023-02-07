package theBrewmaster.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.powers.IntoxicationPower;

public class FermentedTea extends AbstractPotion{

    public static final String POTION_ID = BrewmasterMod.makeID("FermentedTea");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final String NAME = potionStrings.NAME;

    private static final PotionRarity RARITY = PotionRarity.UNCOMMON;
    private static final PotionSize SIZE = PotionSize.BOTTLE;

    public static final PotionColor COLOR = PotionColor.NONE;
    public static final Color LIQUID_COLOR = CardHelper.getColor(153.0f, 51.0f, 15.0f); //RGB
    public static final Color SPOTS_COLOR = CardHelper.getColor(216.0f, 137.0f, 58.0f); //RGB
    public static final Color HYBRID_COLOR = CardHelper.getColor(255.0f, 206.0f, 158.0f); //RGB
    
    public FermentedTea() {
        super(NAME, POTION_ID, RARITY, SIZE, COLOR);
        this.isThrown = false;
    }
    
    public void initializeData(){
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public int getPotency(int potency) {
        return 50;
    }
    
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new IntoxicationPower(p, p, this.potency)));
    }
    
    public AbstractPotion makeCopy() {
        return new FermentedTea();
    }

    
}
