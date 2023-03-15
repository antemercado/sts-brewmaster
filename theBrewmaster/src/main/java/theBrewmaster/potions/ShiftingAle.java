package theBrewmaster.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import theBrewmaster.BrewmasterMod;

public class ShiftingAle extends AbstractPotion{

    public static final String POTION_ID = BrewmasterMod.makeID("ShiftingAle");

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final String NAME = potionStrings.NAME;

    private static final PotionRarity RARITY = PotionRarity.COMMON;
    private static final PotionSize SIZE = PotionSize.S;
    public static final PotionColor COLOR = PotionColor.NONE;
    public static final Color LIQUID_COLOR = CardHelper.getColor(230.0f, 210.0f, 15.0f); //RGB
    public static final Color HYBRID_COLOR = LIQUID_COLOR;
    public static final Color SPOTS_COLOR = CardHelper.getColor(200.0f, 130.0f, 30.0f); //RGB


    public ShiftingAle() {
        super(NAME, POTION_ID, RARITY, SIZE, COLOR);
        this.isThrown = false;
        this.labOutlineColor = BrewmasterMod.BREWMASTER_ORANGE;
    }

    public void initializeData(){
        this.potency = getPotency();
        if (this.potency == 1){
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[2];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public int getPotency(int potency) {
        return 1;
    }
    
    public void use(AbstractCreature target) {
        for (int i = 0; i < this.potency; i++){
            addToBot(new MakeTempCardInHandAction(BrewmasterMod.getMicrobrews().getRandomCard(true)));
        }
    }
    
    public AbstractPotion makeCopy() {
        return new ShiftingAle();
    }

    
}