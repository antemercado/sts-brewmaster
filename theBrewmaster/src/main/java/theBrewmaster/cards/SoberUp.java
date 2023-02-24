package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.powers.IntoxicationPower;

import static theBrewmaster.BrewmasterMod.makeDefaultCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SoberUp extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 0;
    
    private static final int MAGIC = 25;
    private static final int UPGRADE_MAGIC = 20;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(SoberUp.class.getSimpleName());
    public static final String IMG = makeDefaultCardPath(SoberUp.class.getSimpleName(), TYPE);
    
    public SoberUp() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;

        this.exhaust = true;

    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(IntoxicationPower.POWER_ID)){
            AbstractPower intoxication = p.getPower(IntoxicationPower.POWER_ID);
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(intoxication.amount / this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(intoxication.amount / this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, intoxication));
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.baseMagicNumber = this.magicNumber = UPGRADE_MAGIC;
            initializeDescription();
        }
    }
}