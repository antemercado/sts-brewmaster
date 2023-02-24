package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import basemod.helpers.CardModifierManager;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.actions.ApplyIntoxicationPower;
import theBrewmaster.cardmodifiers.BingeModifier;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.powers.IntoxicationPower;

import static theBrewmaster.BrewmasterMod.makeDefaultCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Binge extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = -1;
    
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final int MAGIC = 15;
    private static final int UPGRADE_PLUS_MAGIC = 6;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(Binge.class.getSimpleName());
    public static final String IMG = makeDefaultCardPath(Binge.class.getSimpleName(), TYPE);
    
    public Binge() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        
        for (int i = 0; i < this.energyOnUse; i++){
            addToBot(new ApplyIntoxicationPower(p, p, new IntoxicationPower(p, p, magicNumber)));
            addToBot(new GainBlockAction(p, p, block));
        }

        // AbstractCard tmp = new Binge();
        // if (this.upgraded)
        //     tmp.upgrade();

        // CardModifierManager.addModifier(tmp, new BingeModifier());

        // addToBot(new MakeTempCardInHandAction(tmp, 1, false));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}