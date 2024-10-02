package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.actions.GutFermentationAction;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.relics.LouseLiverRelic;
import theBrewmaster.stances.IntoxicatedStance;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GutFermentation extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(GutFermentation.class.getSimpleName());
    public static final String IMG = makeCardPath(GutFermentation.class.getSimpleName(), TYPE);

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public GutFermentation() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.isEthereal = true;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GutFermentationAction(this, upgraded));
    }

    public void applyPowers() {
        super.applyPowers();
        int intoxCheck = 0;
        int intoxThreshold = BrewmasterMod.intoxThreshAmount;
        if (AbstractDungeon.player.hasPower(IntoxicationPower.POWER_ID)){
            intoxCheck = AbstractDungeon.player.getPower(IntoxicationPower.POWER_ID).amount * 2;
        }
        if ((intoxCheck >= intoxThreshold || 
            (intoxCheck >= LouseLiverRelic.INTOX_THRESHOLD && AbstractDungeon.player.hasRelic(LouseLiverRelic.ID)))) {
                this.exhaust = true;
        }
    }

    // Glow when Intoxicated
    // public void triggerOnGlowCheck() {
    //     if (!upgraded){
    //         return;
    //     }
    //     this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    //     int intoxCheck = 0;
    //     if (AbstractDungeon.player.hasPower(IntoxicationPower.POWER_ID)){
    //         intoxCheck = AbstractDungeon.player.getPower(IntoxicationPower.POWER_ID).amount * 2;
    //     }
    //     if ((intoxCheck >= IntoxicationPower.INTOX_THRESHOLD || 
    //     (intoxCheck >= IntoxicationPower.INTOX_THRESHOLD_RELIC && AbstractDungeon.player.hasRelic(LouseLiverRelic.ID)))) {
    //         this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    //     }
    // }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}