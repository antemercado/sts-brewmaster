package theBrewmaster.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import basemod.interfaces.OnStartBattleSubscriber;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;

public class ShiftingAle extends AbstractPotion{

    public static final String POTION_ID = BrewmasterMod.makeID("ShiftingAle");

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final String NAME = potionStrings.NAME;

    private static final PotionRarity RARITY = PotionRarity.RARE;
    private static final PotionSize SIZE = PotionSize.S;
    public static final PotionColor COLOR = PotionColor.NONE;
    public static final Color LIQUID_COLOR = Color.CHARTREUSE; //RGB
    public static final Color HYBRID_COLOR = Color.RED; //RGB
    public static final Color SPOTS_COLOR = Color.CLEAR;
    public static final PotionEffect EFFECT = PotionEffect.OSCILLATE;

    public enum PotionMode
    {
        NONE, DEXTERITY, STRENGTH;
    }

    public PotionMode mode = PotionMode.NONE;

    public ShiftingAle() {
        super(NAME, POTION_ID, RARITY, SIZE, PotionEffect.OSCILLATE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        this.isThrown = false;
        this.labOutlineColor = BrewmasterMod.BREWMASTER_ORANGE;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null){
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
                changeEffect();
            }
        }
    }

    public void initializeData(){
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public int getPotency(int potency) {
        return 3;
    }
    
    public void use(AbstractCreature target) {
        AbstractPlayer abstractPlayer = AbstractDungeon.player;
        switch (this.mode){
            case DEXTERITY:
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, this.potency), this.potency));
                return;
            case STRENGTH:
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, this.potency), this.potency));
                return; 
        }
    }
    
    public AbstractPotion makeCopy() {
        ShiftingAle ret = new ShiftingAle();
        ret.mode = this.mode;
        return ret;
    }

    public void onBattleStart(AbstractRoom arg0) {
        this.flash();
        changeEffect();
    }

    private void changeEffect() {
        this.mode = PotionMode.values()[AbstractDungeon.potionRng.random(1) + 1];
        String[] powerTip = {"Keyword","Description"};
        switch (this.mode){
            case DEXTERITY:
                this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[2];
                powerTip[0] = TipHelper.capitalize(GameDictionary.DEXTERITY.NAMES[0]);
                powerTip[1] = GameDictionary.keywords.get(GameDictionary.DEXTERITY.NAMES[0]);
                this.hybridColor = LIQUID_COLOR;
                this.liquidColor = LIQUID_COLOR;
                break;
            case STRENGTH:
                this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[3];
                powerTip[0] = TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]);
                powerTip[1] = GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0]);
                this.hybridColor = HYBRID_COLOR;
                this.liquidColor = HYBRID_COLOR;
                break;
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(powerTip[0], powerTip[1]));
        this.tips.add(new PowerTip(TipHelper.capitalize(DESCRIPTIONS[4]), DESCRIPTIONS[5]));
    }

    
}